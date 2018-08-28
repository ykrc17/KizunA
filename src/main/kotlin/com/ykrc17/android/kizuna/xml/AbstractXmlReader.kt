package com.ykrc17.android.kizuna.xml

import java.io.File
import java.io.FileReader
import javax.xml.stream.XMLInputFactory
import javax.xml.stream.XMLStreamConstants
import javax.xml.stream.XMLStreamReader

abstract class AbstractXmlReader(file: File) {
    private val reader: XMLStreamReader
    protected var isVisitFinish = false

    init {
        reader = XMLInputFactory.newInstance().createXMLStreamReader(FileReader(file))
    }

    protected abstract fun onVisitElement(reader: XMLStreamReader)

    public fun visitElements() {
        reader.apply {
            while (hasNext() && !isVisitFinish) {
                if (next() == XMLStreamConstants.START_ELEMENT) {
                    onVisitElement(reader)
                }
            }
        }
        reader.close()
    }
}