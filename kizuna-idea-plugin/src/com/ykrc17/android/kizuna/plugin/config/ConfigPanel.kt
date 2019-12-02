package com.ykrc17.android.kizuna.plugin.config

import com.intellij.ui.EditorTextField
import com.intellij.ui.IdeBorderFactory
import com.ykrc17.android.kizuna.plugin.config.Config
import java.awt.Component
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import javax.swing.JLabel
import javax.swing.JPanel

class ConfigPanel(config: Config) : JPanel() {
    var currentY = 0
    private val srcRelativePath = EditorTextField(config.srcRelativePath)
    private val viewHolderClassName = EditorTextField(config.viewHolderClassName)

    init {
        border = IdeBorderFactory.createTitledBorder("Kizuna")
        layout = GridBagLayout()
        add(JLabel("Source directory:"), srcRelativePath)
        add(JLabel("ViewHolder class:"), viewHolderClassName)
        addFiller()
    }

    private fun add(compLeft: Component, compRight: Component) {
        val c = GridBagConstraints()
        c.insets = Insets(0, 0, 0, 4)
        c.gridx = 0
        c.gridy = currentY
        add(compLeft, c)

        c.insets = Insets(0, 4, 0, 0)
        c.fill = GridBagConstraints.HORIZONTAL
        c.weightx = 1.0
        c.gridx = 1
        c.gridy = currentY++
        add(compRight, c)
    }

    private fun addFiller() {
        val c = GridBagConstraints()
        c.fill = GridBagConstraints.VERTICAL
        c.weighty = 1.0
        c.gridy = currentY++
        add(JPanel(), c)
    }

    fun getConfig(): Config {
        return Config(srcRelativePath.text, viewHolderClassName.text)
    }
}