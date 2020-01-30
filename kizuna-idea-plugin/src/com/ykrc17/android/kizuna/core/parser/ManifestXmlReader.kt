package com.ykrc17.android.kizuna.core.parser

import java.io.File
import javax.xml.namespace.QName
import javax.xml.stream.events.StartElement

class ManifestXmlReader(file: File) : AbstractXmlReader(file) {
    var packageName = ""
        private set

    override fun onVisitElement(element: StartElement) {
        element.getAttributeByName(QName(null, "package"))?.also {
            packageName = it.value
            isVisitFinish = true
        }
    }
}