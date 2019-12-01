package com.ykrc17.android.kizuna.generator

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.TypeSpec

class BindingGenerator : BaseGenerator() {
    override fun getFileSuffix(): String {
        return "Binding"
    }

    override fun onCreateClass(type: TypeSpec.Builder) {
    }

    override fun onCreateMethod(type: TypeSpec.Builder) {
        type.apply {
            constructor { }
            public("inflate") {
                val T_View = ClassName.get("android.view", "View")
                val T_LayoutInflater = ClassName.get("android.view", "LayoutInflater")
                addParameter(ClassName.get("android.content", "Context"), "context")
                addParameter(ClassName.get("android.view", "ViewGroup"), "parent")
                returns(T_View)

                addStatement("\$T view = \$T.from(context).inflate(getLayoutRes(), parent, false)", T_View, T_LayoutInflater)
                addStatement("bind(view)")
                addStatement("return view")
            }
        }
    }
}