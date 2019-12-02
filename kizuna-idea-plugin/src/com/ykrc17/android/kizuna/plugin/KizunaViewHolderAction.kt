package com.ykrc17.android.kizuna.plugin

import com.ykrc17.android.kizuna.plugin.config.Config
import com.ykrc17.android.kizuna.generator.BaseGenerator
import com.ykrc17.android.kizuna.generator.ViewHolderGenerator
import com.ykrc17.android.kizuna.plugin.BaseKizunaAction

class KizunaViewHolderAction : BaseKizunaAction() {

    override fun getGenerator(config: Config): BaseGenerator {
        return ViewHolderGenerator(config.viewHolderClassName)
    }
}