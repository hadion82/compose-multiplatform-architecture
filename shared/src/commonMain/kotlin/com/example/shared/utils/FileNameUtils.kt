package com.example.shared.utils

import kotlin.math.max

object FileNameUtils {
    fun getExtension(fileName: String): String? {
        val index = indexOfExtension(fileName)
        check(index > 0)
        return fileName.substring(index + 1);
    }

    private fun indexOfExtension(fileName: String): Int {
        val extensionPos: Int = fileName.lastIndexOf('.')
        val lastSeparator: Int = indexOfLastSeparator(fileName)
        return if (lastSeparator > extensionPos) -1 else extensionPos
    }

    private fun indexOfLastSeparator(fileName: String): Int {
        val lastUnixPos: Int = fileName.lastIndexOf('/')
        val lastWindowsPos: Int = fileName.lastIndexOf('\\')
        return max(lastUnixPos.toDouble(), lastWindowsPos.toDouble()).toInt()
    }
}