package com.ykrc17.android.kizuna.config

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.project.Project

@State(name = "KizunaConfig", storages = [Storage(file = "kizuna_config.xml")])
open class ConfigStorage private constructor() : PersistentStateComponent<Config> {

    var config = Config()

    override fun getState(): Config {
        return config
    }

    override fun loadState(config: Config?) {
        this.config = config ?: Config()
    }

    companion object Singleton {
        fun getInstance(project: Project): ConfigStorage {
            return ServiceManager.getService(project, ConfigStorage::class.java) ?: ConfigStorage()
        }
    }
}