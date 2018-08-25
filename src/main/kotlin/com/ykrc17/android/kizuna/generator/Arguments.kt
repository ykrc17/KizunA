package com.ykrc17.android.kizuna.generator

import com.ykrc17.android.kizuna.entity.ClassEntity
import com.ykrc17.android.kizuna.entity.LayoutElementEntity

class Arguments {
    val bindingClassName: String
    val layoutElements: List<LayoutElementEntity>
    val layoutResId: String
    val rClass: ClassEntity

    constructor(bindingClassName: String, layoutElements: List<LayoutElementEntity>, layoutResId: String)
            : this(bindingClassName, layoutElements, layoutResId, "")

    constructor(bindingClassName: String, layoutElements: List<LayoutElementEntity>, layoutResId: String, rPackageName: String) {
        this.bindingClassName = bindingClassName
        this.layoutElements = layoutElements
        this.layoutResId = layoutResId
        this.rClass = ClassEntity(rPackageName, "R")
    }
}
