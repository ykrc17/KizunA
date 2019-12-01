package com.ykrc17.android.kizuna.generator

import com.ykrc17.android.kizuna.entity.ClassEntity
import com.ykrc17.android.kizuna.entity.LayoutElementEntity
import com.ykrc17.android.kizuna.xml.LayoutXmlReader
import java.io.File

class Arguments {
    val outputPackageName: String
    val outputClassName: String?
    val layoutElements: List<LayoutElementEntity>
    val layoutResId: String
    val rClass: ClassEntity

    constructor(layoutXmlFile: File, rPackageName: String = "") {
        val layoutReader = LayoutXmlReader(layoutXmlFile)
        layoutReader.visitElements()

        val packageName = layoutReader.getPackage()
        outputPackageName = when {
            packageName.isNullOrEmpty() -> rPackageName
            packageName!!.startsWith(".") -> rPackageName + packageName
            else -> packageName
        }
        outputClassName = layoutReader.getClassName()
        layoutElements = layoutReader.getElements()
        layoutResId = layoutXmlFile.nameWithoutExtension

        rClass = ClassEntity(rPackageName, "R")
    }
}
