package com.ykrc17.android.kizuna.plugin

import com.intellij.lang.Language
import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.fileEditor.OpenFileDescriptor
import com.intellij.openapi.fileTypes.StdFileTypes
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.psi.PsiFile
import com.ykrc17.android.kizuna.plugin.action.FileRefactoringAction
import com.ykrc17.android.kizuna.plugin.config.Config
import com.ykrc17.android.kizuna.plugin.config.ConfigNotFoundDialog
import com.ykrc17.android.kizuna.plugin.config.ConfigStorage
import com.ykrc17.android.kizuna.core.generator.BaseGenerator
import com.ykrc17.android.kizuna.core.kizuna
import java.io.File

abstract class BaseKizunaAction : FileRefactoringAction() {

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

        // 保存当前更改
        FileDocumentManager.getInstance().saveAllDocuments()

        kizuna(File(psiFile.virtualFile.path), configStorage.config.srcRelativePath, getGenerator(configStorage.config)) { outputFile ->
            LocalFileSystem.getInstance().refreshAndFindFileByIoFile(outputFile)?.also {
                OpenFileDescriptor(project, it).navigate(true)
            }
        }
    }

    abstract fun getGenerator(config: Config): BaseGenerator
}
