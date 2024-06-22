package ru.it_cron.intern2.adapter.case

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.it_cron.intern2.R
import ru.it_cron.intern2.databinding.CaseFilterCategoryBinding
import ru.it_cron.intern2.databinding.CaseFilterNameBinding
import ru.it_cron.intern2.model.CaseFilterCategory
import ru.it_cron.intern2.model.CaseFilterName
import ru.it_cron.intern2.model.Filter

interface OnCaseFilterClickListener {
    fun select(caseFilterName: CaseFilterName)
}

class CaseFilterAdapter(
    private val onCaseFilterClickListener: OnCaseFilterClickListener
) : ListAdapter<Filter, RecyclerView.ViewHolder>(CaseFilterCallBack()) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is CaseFilterCategory -> R.layout.case_filter_category
            is CaseFilterName -> R.layout.case_filter_name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.case_filter_category -> {
                val binding = CaseFilterCategoryBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                CaseFilterCategoryVH(binding)
            }

            R.layout.case_filter_name -> {
                val binding = CaseFilterNameBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                CaseFilterNameVH(binding, onCaseFilterClickListener)
            }

            else -> error("Unknown view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is CaseFilterCategory -> (holder as CaseFilterCategoryVH).bind(item)
            is CaseFilterName -> (holder as CaseFilterNameVH).bind(item)
        }
    }
}

class CaseFilterCategoryVH(
    private val binding: CaseFilterCategoryBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(caseFilterCategory: CaseFilterCategory) {
        binding.nameCategory.text = caseFilterCategory.name
    }
}

class CaseFilterNameVH(
    private val binding: CaseFilterNameBinding,
    private val onCaseFilterClickListener: OnCaseFilterClickListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(caseFilterName: CaseFilterName) {

        with(binding) {
            nameFilter.text = caseFilterName.name
            nameFilter.setTextColor(
                if (caseFilterName.selected) Color.WHITE else Color.BLACK
            )

            binding.cardFilter.isChecked = caseFilterName.selected
            binding.checkIcon.isChecked = caseFilterName.selected

            binding.cardFilter.setOnClickListener {
                onCaseFilterClickListener.select(caseFilterName)
            }
        }

    }
}

class CaseFilterCallBack : DiffUtil.ItemCallback<Filter>() {
    override fun areItemsTheSame(oldItem: Filter, newItem: Filter): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Filter, newItem: Filter): Boolean {
        return oldItem == newItem
    }
}