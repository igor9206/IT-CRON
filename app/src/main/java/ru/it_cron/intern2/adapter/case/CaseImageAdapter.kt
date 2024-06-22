package ru.it_cron.intern2.adapter.case

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.it_cron.intern2.databinding.CaseCardImgItemBinding
import ru.it_cron.intern2.extension.loadImage

interface OnCaseImageClickListener {
    fun open()
}

class CaseImageAdapter(
    private val onCaseImageClickListener: OnCaseImageClickListener
) : ListAdapter<String, CaseImageVH>(CaseImageCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CaseImageVH {
        val binding =
            CaseCardImgItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CaseImageVH(binding, onCaseImageClickListener)
    }

    override fun onBindViewHolder(holder: CaseImageVH, position: Int) {
        val img = getItem(position)
        holder.bind(img)
    }
}

class CaseImageVH(
    private val binding: CaseCardImgItemBinding,
    private val onCaseImageClickListener: OnCaseImageClickListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(img: String) {
        with(binding) {
            ivImageCase.loadImage(img)

            cardImg.setOnClickListener {
                onCaseImageClickListener.open()
            }
        }
    }

}

class CaseImageCallBack : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}