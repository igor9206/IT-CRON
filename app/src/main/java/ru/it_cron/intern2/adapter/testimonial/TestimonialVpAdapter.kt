package ru.it_cron.intern2.adapter.testimonial

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.it_cron.intern2.databinding.FeedbackItemBinding
import ru.it_cron.intern2.dto.Testimonial
import ru.it_cron.intern2.extension.loadImage

class TestimonialVpAdapter(
    private val testimonials: List<Testimonial.Data>
) : RecyclerView.Adapter<TestimonialVpVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestimonialVpVH {
        val binding =
            FeedbackItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TestimonialVpVH(binding)
    }

    override fun getItemCount(): Int {
        return testimonials.size
    }

    override fun onBindViewHolder(holder: TestimonialVpVH, position: Int) {
        holder.bind(testimonials[position])
    }
}

class TestimonialVpVH(
    private val binding: FeedbackItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: Testimonial.Data) {
        with(binding) {
            tvContent.text = data.text
            tvName.text = data.customerName
            tvPosition.text = data.company
            ivAvatar.loadImage(data.icon)
        }
    }
}