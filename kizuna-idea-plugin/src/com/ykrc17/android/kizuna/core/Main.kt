package com.ykrc17.android.kizuna.core

import com.ykrc17.android.kizuna.core.generator.Arguments
import com.ykrc17.android.kizuna.core.generator.BaseGenerator
import com.ykrc17.android.kizuna.core.parser.ManifestXmlReader
import java.io.File

fun kizuna(layoutXmlFile: File, srcRelativePath: String, generator: BaseGenerator, callback: (File) -> Unit) {
    // read AndroidManifest.xml
    val projectStructure = ProjectStructure(layoutXmlFile.parentFile)
    var rPackageName = ""
    projectStructure.manifest?.also {
        val reader = ManifestXmlReader(it)
        reader.visitElements()
        rPackageName = reader.packageName
    }
    val srcDir = projectStructure.readSrcDir(srcRelativePath)

    val bindingArgs = Arguments(layoutXmlFile, rPackageName)
    generator.generate(bindingArgs, srcDir, callback)
}

internal class ProjectStructure {
    var manifest: File? = null
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
            return
        }
        if (dir.parentFile == null) return
        findFiles(dir.parentFile)
    }

    fun readSrcDir(relativePath: String): File {
        return File(buildFile!!.parentFile, relativePath)
    }
}

internal fun parseOutputClassName(layoutFileName: String, fileSuffix: String): String {
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

    result.append(fileSuffix)
    return result.toString()
}