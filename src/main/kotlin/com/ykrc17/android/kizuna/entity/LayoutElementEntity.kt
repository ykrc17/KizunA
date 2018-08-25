package com.ykrc17.android.kizuna.entity

class LayoutElementEntity(clazz: String, id: String) {
    val viewClass: ClassEntity
    val viewId: String

    init {
        val dotIndex = clazz.lastIndexOf('.')
        // 原生控件
        viewClass = if (dotIndex < 0) {
            val packageName = when (clazz) {
                "View", "ViewStub" ->
                    "android.view"
                else ->
                    "android.widget"
            }
            ClassEntity(packageName, clazz)
        } else {
            val packageName = clazz.substring(0, dotIndex)
            val simpleName = clazz.substring(dotIndex + 1)
            ClassEntity(packageName, simpleName)
        }
        viewId = id.replace("@+id/", "").replace("@id/", "")
    }
}
