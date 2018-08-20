package com.ykrc17.android.kizuna.generator

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeSpec
import com.ykrc17.android.kizuna.xml.LayoutElementModel
import java.io.File
import javax.lang.model.element.Modifier


fun generateBinding(layoutElements: List<LayoutElementModel>, packageName: String, simpleName: String, targetFile: File) {
    val javaFile = JavaFile.builder(packageName, generateClass(simpleName, layoutElements)).build()

    javaFile.writeTo(targetFile)
}

fun generateClass(simpleName: String, layoutElements: List<LayoutElementModel>): TypeSpec? {
    val clazz = TypeSpec.classBuilder(simpleName).addModifiers(Modifier.PUBLIC)

    val constructor = MethodSpec.constructorBuilder()
            .addModifiers(Modifier.PUBLIC)
            .addParameter(ClassName.get("android.view", "View"), "view")

    layoutElements.forEach {
        clazz.addField(it.getPoetClassName(), it.viewId, Modifier.PUBLIC)
        constructor.addStatement("\$L = (\$T) view.findViewById(R.id.\$L)", it.viewId, it.getPoetClassName(), it.viewId)
    }

    clazz.addMethod(constructor.build())

    return clazz.build()
}