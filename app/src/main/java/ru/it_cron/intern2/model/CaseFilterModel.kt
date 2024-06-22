package ru.it_cron.intern2.model

data class CaseFilterModel(
    val filters: List<Filter> = emptyList(),
    val load: Boolean = false,
    val error: Boolean = false
)

sealed interface Filter {
    val id: String
}

data class CaseFilterCategory(
    override val id: String,
    val name: String
) : Filter

data class CaseFilterName(
    override val id: String,
    val name: String,
    val selected: Boolean = false
) : Filter