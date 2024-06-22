package ru.it_cron.intern2.model

import android.net.Uri
import java.io.File

data class AttachmentModel(
    val id: Int,
    val file: File,
    val uri: Uri,
    val name: String,
    val mimeType: String,
    val size: Double
)