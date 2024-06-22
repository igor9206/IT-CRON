package ru.it_cron.intern2.extension

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

fun Uri.toFile(context: Context, fileName: String): File {
    val inputStream = context.contentResolver.openInputStream(this)
    val tempFile = File(context.cacheDir, fileName)

    val outputStream = FileOutputStream(tempFile)
    inputStream?.use { input ->
        outputStream.use { output ->
            input.copyTo(output)
        }
    }
    return tempFile
}