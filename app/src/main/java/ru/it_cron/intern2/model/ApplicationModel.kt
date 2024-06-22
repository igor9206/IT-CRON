package ru.it_cron.intern2.model

data class ApplicationModel(
    val services: List<String> = listOf(),
    val budget: String = "",
    val description: String = "",
    val contactName: String = "",
    val company: String = "",
    val contactEmail: String = "",
    val contactPhone: String = "",
    val findUs: String = "",
    val processingData: Boolean = false,
    val privacyPolicy: Boolean = false,
)