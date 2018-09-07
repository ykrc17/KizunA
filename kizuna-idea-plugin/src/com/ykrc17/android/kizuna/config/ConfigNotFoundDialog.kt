package com.ykrc17.android.kizuna.config

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.EditorTextField
import com.intellij.util.ui.JBUI
import java.awt.BorderLayout
import java.awt.Component
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel

class ConfigNotFoundDialog(private val project: Project) : DialogWrapper(project) {
    private val pathEditField = EditorTextField()

    init {
        init()
    }

    override fun createCenterPanel(): JComponent? = JPanel(BorderLayout())

    override fun createNorthPanel(): JComponent? {
        return JPanel(GridBagLayout()).run {
            grid(JLabel("请先设置src相对于build.gradle的路径，通常是\"src/main/java\"")) {
                gridwidth = 2
                weightx = 1.0
            }
            grid(JLabel("srcRelativePath")) {
                gridy = 1
            }
            grid(pathEditField) {
                gridy = 1
                gridx = 1
                weightx = 1.0
            }

            this
        }
    }

    override fun doOKAction() {
        ConfigStorage.getInstance(project).config.srcRelativePath = pathEditField.text
        super.doOKAction()
    }

    private inline fun JPanel.grid(comp: Component, cbConsumer: GridBagConstraints.() -> Unit) {
        val cb = GridBagConstraints()
        cb.anchor = GridBagConstraints.WEST
        cb.fill = GridBagConstraints.HORIZONTAL
        cb.insets = JBUI.insets(4, 8)
        cbConsumer(cb)
        add(comp, cb)
    }
}