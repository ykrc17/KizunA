package com.ykrc17.android.kizuna

import com.ykrc17.android.kizuna.generator.generateBinding
import com.ykrc17.android.kizuna.xml.LayoutXmlReader
import com.ykrc17.android.kizuna.xml.ManifestXmlReader
import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.HelpFormatter
import org.apache.commons.cli.Options
import java.io.File

fun main(args: Array<String>) {
    val options = Options()
    options.addOption("d", "src-dir", true, "source directory")
    options.addOption("p", "package", true, "package of the generated java class")
    options.addOption("h", "help", false, "help")
    val line = DefaultParser().parse(options, args)
    if (line.argList.isEmpty() || line.hasOption("h") || line.hasOption("help")) {
        HelpFormatter().printHelp("java -jar kizuna.jar [options] [layoutXml]", options)
        return
    }

    val file = File(line.argList[0])
    var rPackageName = ""
    findManifest(file.parentFile)?.also {
        rPackageName = ManifestXmlReader(it).readPackageName()
    }
    val layoutElements = LayoutXmlReader(file).readElements()

    val targetDir = File("")
    generateBinding(layoutElements, "", rPackageName, file.nameWithoutExtension, targetDir)
}

fun findManifest(dir: File): File? {
    val children = dir.listFiles()
    children.find { it.name == "AndroidManifest.xml" }?.also { return it }
    children.find { it.name == "build.gradle" }?.also { return null }
    return findManifest(dir.parentFile)
}