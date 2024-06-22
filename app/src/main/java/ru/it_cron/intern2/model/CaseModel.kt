package ru.it_cron.intern2.model

import ru.it_cron.intern2.dto.Case

data class CaseModel(
    val cases: Case? = null,
    val load: Boolean = false,
    val error: Boolean = false
)