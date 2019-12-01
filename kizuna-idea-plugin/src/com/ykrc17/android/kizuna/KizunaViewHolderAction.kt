package com.ykrc17.android.kizuna

import com.ykrc17.android.kizuna.config.Config
import com.ykrc17.android.kizuna.generator.BaseGenerator
import com.ykrc17.android.kizuna.generator.ViewHolderGenerator

class KizunaViewHolderAction : BaseKizunaAction() {

    override fun getGenerator(config: Config): BaseGenerator {
        return ViewHolderGenerator(config.viewHolderClassName)
    }
}