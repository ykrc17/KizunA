package com.ykrc17.android.kizuna

import com.ykrc17.android.kizuna.generator.generateBinding
import com.ykrc17.android.kizuna.xml.LayoutXmlReader
import com.ykrc17.android.kizuna.xml.ManifestXmlReader
import java.io.File

fun main(args: Array<String>) {
    if (args.isNotEmpty()) {
        val file = File(args[0])
        var rPackageName = ""
        findManifest(file.parentFile)?.also {
            rPackageName = ManifestXmlReader(it).readPackageName()
        }
        val layoutElements = LayoutXmlReader(file).readElements()

        val targetDir = File("")
        generateBinding(layoutElements, "", rPackageName, file.nameWithoutExtension, targetDir)
    }
}

fun findManifest(dir: File): File? {
    val children = dir.listFiles()
    children.find { it.name == "AndroidManifest.xml" }?.also { return it }
    children.find { it.name == "build.gradle" }?.also { return null }
    return findManifest(dir.parentFile)
}