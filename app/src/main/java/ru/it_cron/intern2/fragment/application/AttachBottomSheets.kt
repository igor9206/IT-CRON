package ru.it_cron.intern2.fragment.application

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toFile
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import ru.it_cron.intern2.databinding.BottomSheetsAttachBinding
import ru.it_cron.intern2.extension.toFile
import ru.it_cron.intern2.extension.toFileSizeInMB
import ru.it_cron.intern2.model.AttachmentModel
import ru.it_cron.intern2.viewmodel.ApplicationViewModel
import java.io.File

class AttachBottomSheets : BottomSheetDialogFragment() {

    private val applicationViewModel: ApplicationViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = BottomSheetsAttachBinding.inflate(inflater, container, false)

        with(binding) {
            photo.setOnClickListener {
                ImagePicker.Builder(requireActivity())
                    .cameraOnly()
                    .crop()
                    .createIntent {
                        attachmentActivityResult.launch(it)
                    }
            }

            file.setOnClickListener {
                attachmentActivityResult.launch(Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                    addCategory(Intent.CATEGORY_OPENABLE)
                    putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
                    type = "*/*"
                })
            }

            cancel.setOnClickListener { dismiss() }
        }

        return binding.root
    }

    private val attachmentActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri = result.data?.data ?: return@registerForActivityResult
                when (uri.scheme) {
                    "content" -> fromUriContentSchema(uri)
                    "file" -> fromUriFileSchema(uri)
                }
            }
            dismiss()
        }

    private fun fromUriContentSchema(uri: Uri) {
        requireContext().contentResolver
            .query(uri, null, null, null, null)
            ?.use { cursor ->
                val nameFile = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME).let { index ->
                    cursor.moveToFirst()
                    cursor.getString(index)
                }
                val file = uri.toFile(requireContext(), nameFile)

                setAttachment(
                    file,
                    uri,
                    file.name,
                    requireContext().contentResolver.getType(uri) ?: "unknown",
                    file.length().toFileSizeInMB()
                )
            }
    }

    private fun fromUriFileSchema(uri: Uri) {
        val file = uri.toFile()
        val ext = MimeTypeMap.getFileExtensionFromUrl(uri.toString())
        setAttachment(
            file,
            uri,
            file.name,
            MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext) ?: "unknown",
            file.length().toFileSizeInMB()
        )
    }

    private fun setAttachment(file: File, uri: Uri, name: String, ext: String, size: Double) {
        applicationViewModel.setAttachment(
            AttachmentModel(0, file, uri, name, ext, size)
        )
    }

    companion object {
        const val TAG = "AttachBottomSheet"
    }

}