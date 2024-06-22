package ru.it_cron.intern2.adapter.application

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import ru.it_cron.intern2.databinding.CardAttachmentBinding
import ru.it_cron.intern2.extension.loadBackground
import ru.it_cron.intern2.model.AttachmentModel

interface AttachmentClickListener {
    fun removeAttachment(attachmentModel: AttachmentModel)
}

class AttachmentAdapter(
    private val attachments: List<AttachmentModel>,
    private val attachmentClickListener: AttachmentClickListener
) : RecyclerView.Adapter<AttachmentVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttachmentVH {
        val binding =
            CardAttachmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AttachmentVH(binding, attachmentClickListener)
    }

    override fun getItemCount(): Int {
        return attachments.size
    }

    override fun onBindViewHolder(holder: AttachmentVH, position: Int) {
        holder.bind(attachments[position])
    }
}

class AttachmentVH(
    private val binding: CardAttachmentBinding,
    private val attachmentClickListener: AttachmentClickListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(attachmentModel: AttachmentModel) {
        val ext = MimeTypeMap.getSingleton().getExtensionFromMimeType(attachmentModel.mimeType)
            ?: "unknown"
        with(binding) {
            groupInfo.isVisible = !ext.contains("jpg")
            cardAttachment.loadBackground(
                if (ext.contains("jpg")) attachmentModel.uri.toString() else null
            )

            nameAttachment.text = attachmentModel.name
            extensionAttachment.text = ext.uppercase()
            extensionAttachment.setBackgroundColor(getColor(ext))
            sizeAttachment.text = attachmentModel.size.toString()

            buttonRemove.setOnClickListener {
                attachmentClickListener.removeAttachment(attachmentModel)
            }
        }
    }

    private fun getColor(ext: String): Int {
        return when (true) {
            ext.contains("pdf") -> Color.parseColor("#FB6542")
            ext.contains("zip") -> Color.YELLOW
            ext.contains("txt") -> Color.GRAY
            ext.contains("doc") -> Color.BLUE
            ext.contains("xls") -> Color.GREEN
            else -> Color.RED
        }
    }
}