package com.ykrc17.android.kizuna.xml

import org.dom4j.Element
import org.dom4j.io.SAXReader
import java.io.File

abstract class AbstractXmlReader(file: File) {
    val rootElement: Element

    init {
        val reader = SAXReader()
        val document = reader.read(file)
        rootElement = document.rootElement
    }
}