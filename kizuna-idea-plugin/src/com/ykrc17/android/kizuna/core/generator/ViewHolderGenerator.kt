package com.ykrc17.android.kizuna.core.generator

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.TypeSpec

class ViewHolderGenerator : BaseGenerator {
    val viewHolderClassName: String

    constructor(viewHolderClassName: String) : super() {
        if (viewHolderClassName.isEmpty()) {
            this.viewHolderClassName = "android.support.v7.widget.RecyclerView.ViewHolder"
        } else {
            this.viewHolderClassName = viewHolderClassName
        }
    }

    override fun getFileSuffix(): String {
        return "ViewHolder"
    }

    override fun onCreateClass(type: TypeSpec.Builder) {
        type.superclass(ClassName.bestGuess(viewHolderClassName))
    }

    override fun onCreateMethod(type: TypeSpec.Builder) {
        type.apply {
            constructor {
                addParameter(ClassName.get("android.view", "View"), "itemView")
                addStatement("super(itemView)")
                addStatement("bind(itemView)")
            }
        }
    }
}