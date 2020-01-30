package com.ykrc17.android.kizuna.core.parser

import com.ykrc17.android.kizuna.core.entity.LayoutElementEntity
import java.io.File
import javax.xml.namespace.QName
import javax.xml.stream.events.Attribute
import javax.xml.stream.events.StartElement

class LayoutXmlReader(file: File) : AbstractXmlReader(file) {

    private var packageName: String? = null
    private var className: String? = null
    private val layoutElements = arrayListOf<LayoutElementEntity>()

    fun getPackage(): String? {
        return packageName
    }

    fun getClassName(): String? {
        return className
    }

    fun getElements(): List<LayoutElementEntity> {
        return layoutElements
    }

    override fun onVisitElement(element: StartElement) {
        if (packageName == null) {
            element.getAttributeByName(QName("http://schemas.android.com/tools", "context"))
                    ?.also {
                        val index = it.value.lastIndexOf('.')
                        packageName = it.value.substring(0, index)
                        className = it.value.substring(index + 1)
                    }
            element.getAttributeByName(QName("http://schemas.android.com/tools", "package"))
                    ?.also {
                        packageName = it.value
                    }
        }
        // 如果有id
        element.getAttributeByName(QName("http://schemas.android.com/apk/res/android", "id"))
                ?.also {
                    layoutElements.add(parseLayoutElement(element, it))
                }
    }

    private fun parseLayoutElement(element: StartElement, it: Attribute): LayoutElementEntity {
        var clazz = element.name.localPart
        if (clazz == "include") {
            clazz = "View"
        }

        var packageName = ""
        val dotIndex = clazz.lastIndexOf('.')
        if (dotIndex < 0) {
            packageName = when (clazz) {
                "View", "ViewStub", "SurfaceView", "TextureView" -> "android.view"
                else -> "android.widget"
            }
        } else {
            packageName = clazz.substring(0, dotIndex)
            clazz = clazz.substring(dotIndex + 1)
        }
        return LayoutElementEntity(packageName, clazz, it.value);
    }
}