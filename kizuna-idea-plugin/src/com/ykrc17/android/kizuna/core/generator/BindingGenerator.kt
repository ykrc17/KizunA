package com.ykrc17.android.kizuna.core.generator

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.TypeSpec
import javax.lang.model.element.Modifier

class BindingGenerator : BaseGenerator() {
    override fun getFileSuffix(): String {
        return "Binding"
    }

    override fun onCreateClass(type: TypeSpec.Builder) {
        type.addField(ClassName.get("android.view", "View"), "itemView", Modifier.PUBLIC)
    }

    override fun onCreateMethod(type: TypeSpec.Builder) {
        type.apply {
            constructor {
                addParameter(ClassName.get("android.view", "View"), "itemView")
                addStatement("this.itemView = itemView")
                addStatement("bind(itemView)")
            }
        }
    }
}