package com.ykrc17.android.kizuna.xml

import java.io.File
import javax.xml.stream.XMLStreamReader

class ManifestXmlReader(file: File) : AbstractXmlReader(file) {
    var packageName = ""
        private set

    override fun onVisitElement(reader: XMLStreamReader) {
        reader.getAttributeValue(null, "package")?.also {
            packageName = it
            isVisitFinish = true
        }
    }
}