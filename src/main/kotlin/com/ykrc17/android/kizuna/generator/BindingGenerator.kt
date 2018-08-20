package com.ykrc17.android.kizuna.generator

import com.squareup.javapoet.*
import com.ykrc17.android.kizuna.xml.LayoutElementModel
import java.io.File
import javax.lang.model.element.Modifier


fun generateBinding(layoutElements: List<LayoutElementModel>, packageName: String, originFileName: String, targetFile: File) {
    val javaFile = JavaFile.builder(packageName, generateClass(originFileName, layoutElements)).build()

    javaFile.writeTo(targetFile)
}

fun generateClass(originFileName: String, layoutElements: List<LayoutElementModel>): TypeSpec? {
    val clazz = TypeSpec.classBuilder(generateBindingClassName(originFileName))
            .addModifiers(Modifier.PUBLIC)

    val constructor = MethodSpec.constructorBuilder()
            .addModifiers(Modifier.PUBLIC)
            .addParameter(ClassName.get("android.view", "View"), "view")

    layoutElements.forEach {
        clazz.addField(it.getPoetClassName(), it.viewId, Modifier.PUBLIC)
        constructor.addStatement("\$L = (\$T) view.findViewById(R.id.\$L)", it.viewId, it.getPoetClassName(), it.viewId)
    }

    clazz.addMethod(generateLayoutResMethod(originFileName))

    clazz.addMethod(constructor.build())

    return clazz.build()
}

fun generateBindingClassName(originFileName: String): String {
    val result = StringBuilder()
    var nextIsUpper = true

    originFileName.forEach {
        if (it == '_') {
            nextIsUpper = true
            return@forEach
        } else {
            result.append(if (nextIsUpper) it.toUpperCase() else it)
            nextIsUpper = false
        }
    }

    result.append("Binding")
    return result.toString()
}

fun generateLayoutResMethod(originFileName: String): MethodSpec? {
    return MethodSpec.methodBuilder("getLayoutRes")
            .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
            .returns(TypeName.INT)
            .addStatement("return R.layout.$originFileName")
            .build()
}