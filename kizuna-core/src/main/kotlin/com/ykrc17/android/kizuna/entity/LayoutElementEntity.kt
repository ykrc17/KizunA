package com.ykrc17.android.kizuna.entity

@Suppress("ConvertSecondaryConstructorToPrimary")
class LayoutElementEntity {
    val viewClass: ClassEntity
    val viewId: String

    /**
     * 用constructor不是因为我菜，是为了可读性
     * TODO 创建converter
     */
    constructor(clazz: String, id: String) {
        val dotIndex = clazz.lastIndexOf('.')
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
