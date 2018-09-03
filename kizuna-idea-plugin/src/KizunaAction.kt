import com.intellij.lang.Language
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.fileTypes.StdFileTypes
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.refactoring.RefactoringActionHandler
import com.intellij.refactoring.actions.BaseRefactoringAction
import com.ykrc17.android.kizuna.kizuna
import java.io.File

class KizunaAction : BaseRefactoringAction() {

    override fun isAvailableForLanguage(language: Language): Boolean {
        return language.isKindOf(StdFileTypes.XML.language)
    }

    override fun getHandler(context: DataContext): RefactoringActionHandler? {
        return object : RefactoringActionHandler {
            override fun invoke(project: Project, editor: Editor, psiFile: PsiFile, context: DataContext) {
                invoke(project, psiFile, context)
            }

            override fun invoke(project: Project, psiElements: Array<out PsiElement>, context: DataContext) {
                if (isEnabledOnElements(psiElements)) {
                    invoke(project, psiElements[0] as PsiFile, context)
                }
            }

            private fun invoke(project: Project, psiFile: PsiFile, context: DataContext) {
                kizuna(File(psiFile.virtualFile.path), null, null)
            }
        }
    }

    override fun isAvailableInEditorOnly() = false

    override fun isEnabledOnElements(psiElements: Array<out PsiElement>) = psiElements.isNotEmpty() && psiElements[0] is PsiFile
}
