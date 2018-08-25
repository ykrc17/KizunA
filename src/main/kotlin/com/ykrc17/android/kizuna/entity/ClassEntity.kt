package com.ykrc17.android.kizuna.entity

import com.squareup.javapoet.ClassName

open class ClassEntity(val packageName: String, val simpleName: String) {

    fun toPoetClassName(): ClassName? {
        return ClassName.get(packageName, simpleName)
    }

}