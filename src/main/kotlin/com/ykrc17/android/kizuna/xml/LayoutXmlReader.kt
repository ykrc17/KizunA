package com.ykrc17.android.kizuna.xml

import org.dom4j.Element
import java.io.File

class LayoutXmlReader(file: File) : AbstractXmlReader(file) {
    private val pairList = arrayListOf<LayoutElementModel>()

    fun readElements(): List<LayoutElementModel> {
        readElementsRecursively(rootElement)
        return pairList
    }

    private fun readElementsRecursively(element: Element) {
        val attr = element.attributes().find { it.qualifiedName == "android:id" }
        attr?.apply {
            pairList.add(LayoutElementModel(element.name, text))
        }
        element.elements().forEach(::readElementsRecursively)
    }
}