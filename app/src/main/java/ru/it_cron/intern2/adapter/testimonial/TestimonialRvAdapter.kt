package ru.it_cron.intern2.adapter.testimonial

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.it_cron.intern2.R
import ru.it_cron.intern2.databinding.CardTestimonialBinding
import ru.it_cron.intern2.dto.Testimonial
import ru.it_cron.intern2.extension.loadAvatar

class TestimonialRvAdapter : ListAdapter<Testimonial.Data, TestimonialRvVH>(TestimonialCB()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestimonialRvVH {
        val binding =
            CardTestimonialBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TestimonialRvVH(binding)
    }

    override fun onBindViewHolder(holder: TestimonialRvVH, position: Int) {
        holder.bind(getItem(position))
    }
}

class TestimonialRvVH(
    private val binding: CardTestimonialBinding
) : RecyclerView.ViewHolder(binding.root) {
    private val defaultMaxLine = 5

    fun bind(testimonial: Testimonial.Data) {
        with(binding) {
            ivAvatar.loadAvatar(testimonial.icon)
            tvName.text = testimonial.customerName
            tvPosition.text = testimonial.company
            tvContent.apply {
                text = testimonial.text
                maxLines = defaultMaxLine
            }

            readMore.apply {
                visibility = when {
                    tvContent.textSize < testimonial.text.length -> View.VISIBLE
                    else -> View.INVISIBLE
                }
                text = when (tvContent.maxLines) {
                    defaultMaxLine -> context.getString(R.string.read_all)
                    else -> context.getString(R.string.hide)
                }
                setOnClickListener {
                    when (tvContent.maxLines == defaultMaxLine) {
                        true -> {
                            tvContent.maxLines = Integer.MAX_VALUE
                            readMore.text = context.getString(R.string.hide)
                        }

                        else -> {
                            tvContent.maxLines = defaultMaxLine
                            readMore.text = context.getString(R.string.read_all)
                        }
                    }
                }
            }
        }
    }

}

class TestimonialCB : DiffUtil.ItemCallback<Testimonial.Data>() {
    override fun areItemsTheSame(oldItem: Testimonial.Data, newItem: Testimonial.Data): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Testimonial.Data, newItem: Testimonial.Data): Boolean {
        return oldItem == newItem
    }
}