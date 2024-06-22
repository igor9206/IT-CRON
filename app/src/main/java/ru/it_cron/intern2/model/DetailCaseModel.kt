package ru.it_cron.intern2.model

import ru.it_cron.intern2.dto.Case
import ru.it_cron.intern2.dto.DetailCase

data class DetailCaseModel(
    val case: DetailCase? = null,
    val image: String,
    val nextCase: Case.Data? = null,
    val load: Boolean = false,
    val error: Boolean = false
)
