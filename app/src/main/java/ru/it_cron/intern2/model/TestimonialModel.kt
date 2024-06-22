package ru.it_cron.intern2.model

import ru.it_cron.intern2.dto.Testimonial

data class TestimonialModel(
    val testimonial: Testimonial? = null,
    val load: Boolean = false,
    val error: Boolean = false
)
