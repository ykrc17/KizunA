package com.ykrc17.android.kizuna.core.parser

import java.io.File
import java.io.FileReader
import javax.xml.stream.XMLInputFactory
import javax.xml.stream.events.StartElement

abstract class AbstractXmlReader(file: File) {
    private val reader = XMLInputFactory.newInstance().createXMLEventReader(FileReader(file))
    protected var isVisitFinish = false

    protected abstract fun onVisitElement(element: StartElement)

    fun visitElements() {
        reader.apply {
            while (hasNext() && !isVisitFinish) {
                val event = nextEvent()
                if (event.isStartElement) {
                    onVisitElement(event.asStartElement())
                }
            }
        }
        reader.close()
    }
}