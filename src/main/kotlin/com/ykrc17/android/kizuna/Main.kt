package com.ykrc17.android.kizuna

import com.ykrc17.android.kizuna.generator.Arguments
import com.ykrc17.android.kizuna.generator.BindingGenerator
import com.ykrc17.android.kizuna.xml.LayoutXmlReader
import com.ykrc17.android.kizuna.xml.ManifestXmlReader
import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.HelpFormatter
import org.apache.commons.cli.Options
import java.io.File

fun main(args: Array<String>) {
    val options = Options()
    options.addOption("d", "src-dir", true, "source directory")
    options.addOption("p", "package", true, "generated java class's package name")
    options.addOption("h", "help", false, "help")
    val line = DefaultParser().parse(options, args)
    if (line.argList.isEmpty() || line.hasOption("h")) {
        HelpFormatter().printHelp("java -jar kizuna.jar [options] [layoutXml]", options)
        return
    }

    val layoutXmlFile = File(line.argList[0])
    var rPackageName = ""
    findManifest(layoutXmlFile.parentFile)?.also {
        rPackageName = ManifestXmlReader(it).readPackageName()
    }
    val layoutElements = LayoutXmlReader(layoutXmlFile).readElements()
    val layoutResId = layoutXmlFile.nameWithoutExtension

    val bindingArgs = Arguments(getBindingClassName(layoutResId),
            layoutElements,
            layoutResId,
            rPackageName)
    BindingGenerator().generate(bindingArgs, line.getOptionValue("p", ""), File(line.getOptionValue("d", "")))
}

fun findManifest(dir: File): File? {
    val children = dir.listFiles()
    children.find { it.name == "AndroidManifest.xml" }?.also { return it }
    children.find { it.name == "build.gradle" }?.also { return null }
    return findManifest(dir.parentFile)
}

fun getBindingClassName(layoutFileName: String): String {
    val result = StringBuilder()
    var nextIsUpper = true

    layoutFileName.forEach {
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