package com.ykrc17.android.kizuna.xml

import com.ykrc17.android.kizuna.entity.LayoutElementEntity
import java.io.File
import javax.xml.stream.XMLStreamReader

class LayoutXmlReader(file: File) : AbstractXmlReader(file) {

    private var packageName: String? = null
    private val pairList = arrayListOf<LayoutElementEntity>()

    fun getPackage(fallback: () -> String): String {
        packageName?.also { return it }
        return fallback()
    }

    fun getElements(): List<LayoutElementEntity> {
        return pairList
    }

    override fun onVisitElement(reader: XMLStreamReader) {
        if (packageName == null) {
            reader.getAttributeValue("http://schemas.android.com/tools", "package")
                    ?.also {
                        packageName = it
                    }
        }
        reader.getAttributeValue("http://schemas.android.com/apk/res/android", "id")
                ?.also {
                    pairList.add(LayoutElementEntity(reader.localName, it))
                }
    }
}