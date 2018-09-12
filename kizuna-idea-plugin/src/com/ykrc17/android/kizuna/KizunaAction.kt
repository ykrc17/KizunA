package com.ykrc17.android.kizuna

import com.intellij.lang.Language
import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.fileEditor.OpenFileDescriptor
import com.intellij.openapi.fileTypes.StdFileTypes
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.psi.PsiFile
import com.ykrc17.android.kizuna.action.FileRefactoringAction
import com.ykrc17.android.kizuna.config.ConfigNotFoundDialog
import com.ykrc17.android.kizuna.config.ConfigStorage
import java.io.File

class KizunaAction : FileRefactoringAction() {

    override fun isAvailableForLanguage(language: Language): Boolean {
        return language.isKindOf(StdFileTypes.XML.language)
    }

    override fun invoke(project: Project, psiFile: PsiFile, context: DataContext) {
        val configStorage = ConfigStorage.getInstance(project)

        if (configStorage.config.srcRelativePath.isEmpty()) {
            if (!ConfigNotFoundDialog(project).showAndGet()) {
                // 如果不ok
                return
            }

            if (configStorage.config.srcRelativePath.isEmpty()) {
                Notification("kizunaSrcPathError", "KizunA", "生成终止, src相对路径为空", NotificationType.WARNING).notify(project)
                return
            }
        }
        kizuna(File(psiFile.virtualFile.path), null, configStorage.config.srcRelativePath, null) { outputFile ->
            LocalFileSystem.getInstance().refreshAndFindFileByIoFile(outputFile)?.also {
                OpenFileDescriptor(project, it).navigate(true)
            }
        }
    }
}
