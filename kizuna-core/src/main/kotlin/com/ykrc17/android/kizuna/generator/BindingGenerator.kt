package com.ykrc17.android.kizuna.generator

import com.squareup.javapoet.*
import java.io.File
import javax.lang.model.element.Modifier

class BindingGenerator {
    fun generate(args: Arguments, packageName: String, srcDir: File) {
        val javaFile = JavaFile.builder(packageName, bindClass(args)).build()
        javaFile.writeTo(srcDir)
    }

    private fun bindClass(args: Arguments): TypeSpec? {
        return TypeSpec.classBuilder(args.bindingClassName).apply {
            addModifiers(Modifier.PUBLIC)
            args.layoutElements.forEach {
                addField(it.viewClass.toPoetClassName(), it.viewId, Modifier.PUBLIC)
            }

            public("bind") {
                addParameter(ClassName.get("android.view", "View"), "view")
                args.layoutElements.forEach {
                    addStatement("${it.viewId} = (\$T) view.findViewById(\$T.id.${it.viewId})", it.viewClass.toPoetClassName(), args.rClass.toPoetClassName())
                }
            }

            // 构造函数
            constructor { }
            constructor {
                addParameter(ClassName.get("android.view", "View"), "view")
                addStatement("bind(view)")
            }

            public("getLayoutRes") {
                returns(TypeName.INT)
                addStatement("return \$T.layout.${args.layoutResId}", args.rClass.toPoetClassName())
            }
        }.build()
    }

    private fun TypeSpec.Builder.constructor(block: MethodSpec.Builder.() -> Unit) {
        val builder = MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC)
        block(builder)
        addMethod(builder.build())
    }

    private fun TypeSpec.Builder.public(name: String, block: MethodSpec.Builder.() -> Unit) {
        val builder = MethodSpec.methodBuilder(name).addModifiers(Modifier.PUBLIC)
        block(builder)
        addMethod(builder.build())
    }
}