package com.ykrc17.android.kizuna.plugin.config

import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import javax.swing.JComponent

class ConfigController(project: Project) : Configurable {
    private val storage = ConfigStorage.getInstance(project)
    private val panel: ConfigPanel by lazy { ConfigPanel(storage.config) }

    override fun getDisplayName() = "KizunA"

    override fun isModified(): Boolean {
        return storage.config != panel.getConfig()
    }

    override fun apply() {
        storage.config = panel.getConfig()
    }

    override fun createComponent(): JComponent? {
        return panel
    }
}