package com.ykrc17.android.kizuna.plugin

import com.ykrc17.android.kizuna.plugin.config.Config
import com.ykrc17.android.kizuna.core.generator.BaseGenerator
import com.ykrc17.android.kizuna.core.generator.ViewHolderGenerator

class KizunaViewHolderAction : BaseKizunaAction() {

    override fun getGenerator(config: Config): BaseGenerator {
        return ViewHolderGenerator(config.viewHolderClassName)
    }
}