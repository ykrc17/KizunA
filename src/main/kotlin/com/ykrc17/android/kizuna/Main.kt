package com.ykrc17.android.kizuna

import com.ykrc17.android.kizuna.generator.Arguments
import com.ykrc17.android.kizuna.generator.BindingGenerator
import com.ykrc17.android.kizuna.gradle.PropertiesReader
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
    val projectStructure = ProjectStructure(layoutXmlFile.parentFile)

    val layoutReader = LayoutXmlReader(layoutXmlFile)
    val layoutElements = layoutReader.readElements()
    val layoutResId = layoutXmlFile.nameWithoutExtension
    val packageName = layoutReader.readPackage { line.getOptionValue("p", "") }

    var rPackageName = ""
    projectStructure.manifest?.also {
        rPackageName = ManifestXmlReader(it).readPackageName()
    }

    var srcDir = projectStructure.readSrcDir {
        File(line.getOptionValue("d", ""))
    }

    val bindingArgs = Arguments(toBindingClassName(layoutResId),
            layoutElements,
            layoutResId,
            rPackageName)
    BindingGenerator().generate(bindingArgs, packageName, srcDir)
}

class ProjectStructure {
    var manifest: File? = null
    var properties: File? = null
    var buildFile: File? = null

    constructor(dir: File) {
        findFiles(dir)
    }

    private fun findFiles(dir: File) {
        val children = mutableMapOf<String, File>()
        dir.listFiles().forEach {
            children[it.name] = it
        }
        if (manifest == null) {
            manifest = children["AndroidManifest.xml"]
        }
        if (children.contains("build.gradle")) {
            buildFile = children["build.gradle"]
            properties = children["kizuna.properties"]
            return
        }
        if (dir.parentFile == null) return
        findFiles(dir.parentFile)
    }

    fun readSrcDir(fallback: () -> File): File {
        if (properties != null && buildFile != null && properties!!.exists() && buildFile!!.exists()) {
            PropertiesReader(properties!!).read("srcDir")?.also {
                return File(buildFile!!.parentFile, it)
            }
        }
        return fallback()
    }
}

fun toBindingClassName(layoutFileName: String): String {
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