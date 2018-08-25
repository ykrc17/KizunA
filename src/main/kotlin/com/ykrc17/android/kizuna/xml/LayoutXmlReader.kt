package com.ykrc17.android.kizuna.xml

import com.ykrc17.android.kizuna.entity.LayoutElementEntity
import org.dom4j.Element
import java.io.File

class LayoutXmlReader(file: File) : AbstractXmlReader(file) {
    private val pairList = arrayListOf<LayoutElementEntity>()

    fun readPackage(fallback: () -> String): String {
        rootElement.attributes().find { it.qualifiedName == "tools:package" }?.also { return it.text }
        return fallback()
    }

    fun readElements(): List<LayoutElementEntity> {
        readElementsRecursively(rootElement)
        return pairList
    }

    private fun readElementsRecursively(element: Element) {
        val attr = element.attributes().find { it.qualifiedName == "android:id" }
        attr?.apply {
            pairList.add(LayoutElementEntity(element.name, text))
        }
        element.elements().forEach(::readElementsRecursively)
    }
}