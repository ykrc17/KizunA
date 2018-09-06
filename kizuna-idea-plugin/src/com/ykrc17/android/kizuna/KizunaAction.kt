package com.ykrc17.android.kizuna

import com.intellij.lang.Language
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.fileTypes.StdFileTypes
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import com.ykrc17.android.kizuna.action.FileRefactoringAction
import com.ykrc17.android.kizuna.config.ConfigStorage
import java.io.File

class KizunaAction : FileRefactoringAction() {

    override fun isAvailableForLanguage(language: Language): Boolean {
        return language.isKindOf(StdFileTypes.XML.language)
    }

    override fun invoke(project: Project, psiFile: PsiFile, context: DataContext) {
        val configStorage = ConfigStorage.getInstance(project)

        var srcDir: File? = null
        configStorage.config.srcDirPath?.also {
            srcDir = File(it)
        }
        kizuna(File(psiFile.virtualFile.path), srcDir, null)
    }
}
