package ru.it_cron.intern2.adapter.case

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.it_cron.intern2.databinding.CaseItemBinding
import ru.it_cron.intern2.dto.Case
import ru.it_cron.intern2.extension.loadImage

interface OnCaseClickListener {
    fun openCase(case: Case.Data)
}

class CaseAdapter(
    private val onCaseClickListener: OnCaseClickListener
) : ListAdapter<Case.Data, CaseVH>(CaseCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CaseVH {
        val binding = CaseItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CaseVH(binding, onCaseClickListener)
    }

    override fun onBindViewHolder(holder: CaseVH, position: Int) {
        val case = getItem(position)
        holder.bind(case)
    }
}

class CaseVH(
    private val binding: CaseItemBinding,
    private val onCaseClickListener: OnCaseClickListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(case: Case.Data) {
        with(binding) {
            tvTitle.text = case.title
            ivImage.loadImage(case.image)

            cardCase.setOnClickListener {
                onCaseClickListener.openCase(case)
            }
        }
    }
}

class CaseCallBack : DiffUtil.ItemCallback<Case.Data>() {
    override fun areItemsTheSame(oldItem: Case.Data, newItem: Case.Data): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Case.Data, newItem: Case.Data): Boolean {
        return oldItem == newItem
    }
}