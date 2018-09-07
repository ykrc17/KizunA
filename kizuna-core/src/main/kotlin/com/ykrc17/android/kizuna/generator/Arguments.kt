package com.ykrc17.android.kizuna.generator

import com.ykrc17.android.kizuna.entity.ClassEntity
import com.ykrc17.android.kizuna.entity.LayoutElementEntity

class Arguments {
    val outputPackageName: String
    val outputClassName: String
    val layoutElements: List<LayoutElementEntity>
    val layoutResId: String
    val rClass: ClassEntity

    constructor(outputPackageName: String, bindingClassName: String, layoutElements: List<LayoutElementEntity>, layoutResId: String, rPackageName: String = "") {
        if (outputPackageName.startsWith(".")) {
            this.outputPackageName = rPackageName + outputPackageName
        } else {
            this.outputPackageName = outputPackageName
        }
        this.outputClassName = bindingClassName
        this.layoutElements = layoutElements
        this.layoutResId = layoutResId
        this.rClass = ClassEntity(rPackageName, "R")
    }
}
