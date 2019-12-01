package com.ykrc17.android.kizuna

import com.ykrc17.android.kizuna.generator.BaseGenerator
import com.ykrc17.android.kizuna.generator.BindingGenerator

class KizunaBindingAction : BaseKizunaAction() {

    val generator = BindingGenerator()

    override fun getGenerator(): BaseGenerator {
        return generator
    }
}