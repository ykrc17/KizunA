package com.ykrc17.android.kizuna.gradle

import java.io.File

class PropertiesReader {
    fun read(propertyName: String): String? {
        file.readLines().forEach {
            val list = it.split("=")
            if (list.size >= 2) {
                val pName = list[0].trim()
                if (pName == propertyName) {
                    return list[1].trim()
                }
            }
        }
        return null
    }

    val file: File

    constructor(file: File) {
        this.file = file
    }
}