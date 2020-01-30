package com.ykrc17.android.kizuna.plugin

import com.ykrc17.android.kizuna.plugin.config.Config
import com.ykrc17.android.kizuna.core.generator.BaseGenerator
import com.ykrc17.android.kizuna.core.generator.BindingGenerator

class KizunaBindingAction : BaseKizunaAction() {

    val generator = BindingGenerator()

    override fun getGenerator(config: Config): BaseGenerator {
        return generator
    }
}