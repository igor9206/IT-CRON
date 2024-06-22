package ru.it_cron.intern2.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import ru.it_cron.intern2.R

fun ImageView.loadImage(url: String?) {
    if (url == null) {
        return
    }
    Glide.with(this)
        .load(url)
        .error(R.drawable.ic_error_outline_24)
        .timeout(10_000)
        .into(this)
}

fun ImageView.loadAvatar(url: String?) {
    if (url == null) {
        return
    }
    Glide.with(this)
        .load(url)
        .error(R.drawable.ic_error_outline_24)
        .timeout(10_000)
        .circleCrop()
        .into(this)
}

fun ImageView.loadBackground(url: String?) {
    if (url == null) {
        return
    }
    Glide.with(this)
        .load(url)
        .error(R.drawable.attachment_background)
        .centerCrop()
        .timeout(10_000)
        .into(this)
}