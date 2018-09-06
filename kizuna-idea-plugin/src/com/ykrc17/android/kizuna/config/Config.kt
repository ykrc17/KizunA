package com.ykrc17.android.kizuna.config

class Config(var srcDirPath: String = "") {

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other is Config) {
            return srcDirPath == other.srcDirPath
        }
        return false
    }

    override fun hashCode(): Int {
        return srcDirPath.hashCode()
    }
}