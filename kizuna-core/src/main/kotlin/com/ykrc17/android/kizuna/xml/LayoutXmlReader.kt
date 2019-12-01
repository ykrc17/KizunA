package com.ykrc17.android.kizuna.xml

import com.ykrc17.android.kizuna.entity.LayoutElementEntity
import java.io.File
import javax.xml.namespace.QName
import javax.xml.stream.events.StartElement

class LayoutXmlReader(file: File) : AbstractXmlReader(file) {

    private var packageName: String? = null
    private var className: String? = null
    private val pairList = arrayListOf<LayoutElementEntity>()

    fun getPackage(): String? {
        return packageName
    }

    fun getClassName(): String? {
        return className
    }

    fun getElements(): List<LayoutElementEntity> {
        return pairList
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
                    val viewClassName = element.name.localPart
                    // 不支持include
                    if (viewClassName != "include") {
                        pairList.add(LayoutElementEntity(viewClassName, it.value))
                    }
                }
    }
}