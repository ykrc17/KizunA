package com.ykrc17.android.kizuna

import com.ykrc17.android.kizuna.generator.BaseGenerator
import com.ykrc17.android.kizuna.generator.ViewHolderGenerator

class KizunaViewHolderAction : BaseKizunaAction() {

    val generator = ViewHolderGenerator()

    override fun getGenerator(): BaseGenerator {
        return generator
    }
}