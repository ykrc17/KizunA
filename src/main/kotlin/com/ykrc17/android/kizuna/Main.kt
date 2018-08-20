package com.ykrc17.android.kizuna

import com.ykrc17.android.kizuna.generator.generateBinding
import com.ykrc17.android.kizuna.xml.LayoutXmlReader
import java.io.File

fun main(args: Array<String>) {
    if (args.isNotEmpty()) {
        val file = File(args[0])
        val layoutElements = LayoutXmlReader(file).readElements()

        val targetDir = File("")
        generateBinding(layoutElements, "", file.nameWithoutExtension, targetDir)
    }
}