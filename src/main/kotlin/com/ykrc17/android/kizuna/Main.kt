package com.ykrc17.android.kizuna

import com.ykrc17.android.kizuna.generator.generateBinding
import com.ykrc17.android.kizuna.xml.LayoutXmlReader
import java.io.File

fun main(args: Array<String>) {
    if (args.isNotEmpty()) {
        val file = File(args[0])
        val layoutElements = LayoutXmlReader(file).readElements()

        val targetDir = File("")
        generateBinding(layoutElements, "", getTargetSimpleName(file), targetDir)
    }
}

fun getTargetSimpleName(originFile: File): String {
    val result = StringBuilder()
    var nextIsUpper = true

    originFile.nameWithoutExtension.forEach {
        if (it == '_') {
            nextIsUpper = true
            return@forEach
        } else {
            result.append(if (nextIsUpper) it.toUpperCase() else it)
            nextIsUpper = false
        }
    }

    result.append("Binding")
    return result.toString()
}