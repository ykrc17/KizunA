package com.ykrc17.android.kizuna.xml

class LayoutElementModel(clazz: String, id: String) {
    val viewClass: String
    val viewId: String

    init {
        viewClass = if (clazz.indexOf('.') < 0) {
            "android.view.$clazz"
        } else {
            clazz
        }
        viewId = id.replace("@+id/", "").replace("@id/", "")
    }

    override fun toString(): String {
        return "$viewClass : R.id.$viewId"
    }
}
