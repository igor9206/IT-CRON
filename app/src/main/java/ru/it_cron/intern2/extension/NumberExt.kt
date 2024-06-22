package ru.it_cron.intern2.extension

import java.math.RoundingMode

fun Number.toFileSizeInMB(): Double {
    return (this.toDouble() / 1024 / 1024).toBigDecimal()
        .setScale(2, RoundingMode.UP)
        .toDouble()
}