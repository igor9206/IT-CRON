package ru.it_cron.intern2.adapter.case

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import ru.it_cron.intern2.databinding.ScreenShotItemBinding
import ru.it_cron.intern2.extension.loadImage
import kotlin.math.abs

class ScreenShotAdapter(
    private val list: List<String>,
) : RecyclerView.Adapter<ScreenShotVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScreenShotVH {
        val binding =
            ScreenShotItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ScreenShotVH(binding)
    }

    override fun onBindViewHolder(holder: ScreenShotVH, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

class ScreenShotVH(
    private val binding: ScreenShotItemBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(img: String) {
        binding.ivScreenShot.loadImage(img)
    }
}

class ZoomOutPageTransformer : ViewPager2.PageTransformer {

    private val minScale = 0.85f
    private val minAlpha = 0.5f

    override fun transformPage(view: View, position: Float) {
        view.apply {
            val pageWidth = width
            val pageHeight = height
            when {
                position < -1 -> {
                    alpha = 0f
                }

                position <= 1 -> {
                    val scaleFactor = minScale.coerceAtLeast(1 - abs(position))
                    val vertMargin = pageHeight * (1 - scaleFactor) / 2
                    val horMargin = pageWidth * (1 - scaleFactor) / 2
                    translationX = if (position < 0) {
                        horMargin - vertMargin / 2
                    } else {
                        horMargin + vertMargin / 2
                    }

                    scaleX = scaleFactor
                    scaleY = scaleFactor

                    alpha = (minAlpha +
                            (((scaleFactor - minScale) / (1 - minScale)) * (1 - minAlpha)))
                }

                else -> {
                    alpha = 0f
                }
            }
        }
    }
}