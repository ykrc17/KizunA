package com.ykrc17.android.kizuna.config

class Config(var srcRelativePath: String = "") {

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other is Config) {
            return srcRelativePath == other.srcRelativePath
        }
        return false
    }

    override fun hashCode(): Int {
        return srcRelativePath.hashCode()
    }
}