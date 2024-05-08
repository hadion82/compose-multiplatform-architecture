package com.example.shared.platform.media

import android.content.ContentValues
import android.os.Build
import android.os.Environment.*
import android.os.FileUtils
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import androidx.annotation.RequiresApi
import com.example.shared.platform.startup.androidContext
import io.github.aakira.napier.Napier
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

actual interface Downloader {
    @Throws(IOException::class, FileNotFoundException::class)
    actual fun copyToFile(sourcePath: String, dir: String, fileName: String)
}

actual object MediaUtils : Downloader {

    private fun getMimeType(fileName: String): String? =
        MimeTypeMap.getSingleton().getMimeTypeFromExtension(
            MimeTypeMap.getFileExtensionFromUrl(fileName)
        )

    override fun copyToFile(sourcePath: String, dir: String, fileName: String) {
        (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) Q else P)
            .copyToFile(sourcePath, dir, fileName)
    }

    object Q : Downloader {
        @RequiresApi(Build.VERSION_CODES.Q)
        override fun copyToFile(
            sourcePath: String,
            dir: String,
            fileName: String
        ) {
            Napier.d("source path -> $sourcePath")
            val mimeType = getMimeType(fileName)
            val values = getValues(dir, fileName, mimeType ?: "image/*")
            val collection = MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL)
            Napier.d("outputFile path -> $collection")
            with(androidContext.contentResolver) {
                insert(collection, values)?.let {
                    openFileDescriptor(it, "w", null)?.use { fd ->
                        FileUtils.copy(FileInputStream(File(sourcePath)), FileOutputStream(fd.fileDescriptor))
                        Napier.d("copy complete")
                    }
                    values.clear()
                    values.put(MediaStore.MediaColumns.IS_PENDING, 0)
                    update(it, values, null, null)
                }
            }
        }

        @RequiresApi(Build.VERSION_CODES.Q)
        private fun getValues(dir: String, fileName: String, mimeType: String) =
            ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
                put(
                    MediaStore.MediaColumns.RELATIVE_PATH,
                    DIRECTORY_DOWNLOADS + dir
                )
                put(MediaStore.MediaColumns.IS_PENDING, 1)
            }
    }

    object P : Downloader {
        override fun copyToFile(
            sourcePath: String,
            dir: String,
            fileName: String
        ) {
            Napier.d("source path -> $sourcePath")
            val outputFile = File(externalDownloadsDir(dir), fileName)
            Napier.d("outputFile path -> ${outputFile.absoluteFile}")
            FileOutputStream(outputFile).use { fos ->
                FileInputStream(File(sourcePath)).use { fis ->
                    fis.copyTo(fos)
                    Napier.d("copy complete")
                }
            }
        }

        private fun externalDownloadsDir(dir: String) =
            File(getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS), dir)
                .apply { if (exists().not()) mkdir() }
    }
}