package com.ykrc17.android.kizuna

import com.ykrc17.android.kizuna.generator.Arguments
import com.ykrc17.android.kizuna.generator.BindingGenerator
import com.ykrc17.android.kizuna.gradle.PropertiesReader
import com.ykrc17.android.kizuna.xml.LayoutXmlReader
import com.ykrc17.android.kizuna.xml.ManifestXmlReader
import java.io.File

fun kizuna(layoutXmlFile: File, inSrcDir: File?, inTargetPackage: String?) {
    val projectStructure = ProjectStructure(layoutXmlFile.parentFile)

    val layoutReader = LayoutXmlReader(layoutXmlFile)
    layoutReader.visitElements()
    val layoutElements = layoutReader.getElements()
    val layoutResId = layoutXmlFile.nameWithoutExtension
    val packageName = inTargetPackage ?: layoutReader.getPackage()

    var rPackageName = ""
    projectStructure.manifest?.also {
        val reader = ManifestXmlReader(it)
        reader.visitElements()
        rPackageName = reader.packageName
    }

    var srcDir = inSrcDir ?: projectStructure.readSrcDir()

    val bindingArgs = Arguments(toBindingClassName(layoutResId),
            layoutElements,
            layoutResId,
            rPackageName)
    BindingGenerator().generate(bindingArgs, packageName, srcDir)
}

internal class ProjectStructure {
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

    fun readSrcDir(): File {
        if (properties != null && buildFile != null && properties!!.exists() && buildFile!!.exists()) {
            PropertiesReader(properties!!).read("srcDir")?.also {
                return File(buildFile!!.parentFile, it)
            }
        }
        return buildFile!!.parentFile
    }
}

internal fun toBindingClassName(layoutFileName: String): String {
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