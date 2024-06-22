package ru.it_cron.intern2.model

data class RequiredFieldsStateModel(
    val fieldsIsNotEmpty: Boolean = false,
    val emailIsValid: Boolean = true,
    val attachmentIsValid: Boolean = true
)