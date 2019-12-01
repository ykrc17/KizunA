package com.ykrc17.android.kizuna.generator

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.TypeSpec

class ViewHolderGenerator : BaseGenerator() {
    override fun getFileSuffix(): String {
        return "ViewHolder"
    }

    override fun onCreateClass(type: TypeSpec.Builder) {
        type.superclass(ClassName.get("android.support.v7.widget", "RecyclerView", "ViewHolder"))
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