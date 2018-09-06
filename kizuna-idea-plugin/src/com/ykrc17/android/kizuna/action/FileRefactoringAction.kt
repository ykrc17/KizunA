package com.ykrc17.android.kizuna.action

import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.refactoring.RefactoringActionHandler
import com.intellij.refactoring.actions.BaseRefactoringAction

abstract class FileRefactoringAction : BaseRefactoringAction(), RefactoringActionHandler {

    override fun getHandler(context: DataContext): RefactoringActionHandler? {
        return this
    }

    override fun invoke(project: Project, editor: Editor, psiFile: PsiFile, context: DataContext) {
        invoke(project, psiFile, context)
    }

    override fun invoke(project: Project, psiElements: Array<out PsiElement>, context: DataContext) {
        if (isEnabledOnElements(psiElements)) {
            invoke(project, psiElements[0] as PsiFile, context)
        }
    }

    override fun isEnabledOnElements(psiElements: Array<out PsiElement>): Boolean {
        return psiElements.isNotEmpty() && psiElements[0] is PsiFile
    }

    override fun isAvailableInEditorOnly() = false

    protected abstract fun invoke(project: Project, psiFile: PsiFile, context: DataContext)
}