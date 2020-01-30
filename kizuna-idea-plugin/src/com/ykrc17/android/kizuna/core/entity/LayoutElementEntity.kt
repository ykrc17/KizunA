package com.ykrc17.android.kizuna.core.entity

class LayoutElementEntity {
    val viewClass: ClassEntity
    val viewId: String

    /**
     * 用constructor不是因为我菜，是为了可读性
     * TODO 创建converter
     */
    constructor(packageName: String, clazz: String, id: String) {
        viewClass = ClassEntity(packageName, clazz)
        viewId = id.replace("@+id/", "").replace("@id/", "")
    }
}
