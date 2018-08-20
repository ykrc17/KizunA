package com.ykrc17.android.kizuna.xml

import com.squareup.javapoet.ClassName

class LayoutElementModel(clazz: String, id: String) {
    val packageName: String
    val simpleName: String
    val viewId: String

    init {
        val dotIndex = clazz.lastIndexOf('.')
        if (dotIndex < 0) {
            packageName = "android.view"
            simpleName = clazz
        } else {
            packageName = clazz.substring(0, dotIndex)
            simpleName = clazz.substring(dotIndex + 1)
        }
        viewId = id.replace("@+id/", "").replace("@id/", "")
    }

    fun getPoetClassName(): ClassName? {
        return ClassName.get(packageName, simpleName)
    }

    override fun toString(): String {
        return "$packageName.$simpleName : R.id.$viewId"
    }
}
