package com.ykrc17.android.kizuna.xml

import java.io.File

class ManifestXmlReader(file: File) : AbstractXmlReader(file) {
    fun readPackageName(): String {
        return rootElement.attributeValue("package")
    }
}