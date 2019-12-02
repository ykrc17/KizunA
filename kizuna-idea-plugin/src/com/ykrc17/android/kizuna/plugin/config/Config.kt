package com.ykrc17.android.kizuna.plugin.config

class Config(var srcRelativePath: String = "", var viewHolderClassName: String = "") {

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other is Config) {
            return srcRelativePath == other.srcRelativePath && viewHolderClassName == other.viewHolderClassName
        }
        return false
    }

    override fun hashCode(): Int {
        return srcRelativePath.hashCode() + viewHolderClassName.hashCode()
    }
}