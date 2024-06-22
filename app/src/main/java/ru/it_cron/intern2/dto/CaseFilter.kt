package ru.it_cron.intern2.dto

import com.google.gson.annotations.SerializedName

data class CaseFilter(
    @SerializedName("Data")
    val `data`: List<Data>,
    @SerializedName("Error")
    val error: Error
) {
    data class Data(
        @SerializedName("Filters")
        val filters: List<Filter>,
        @SerializedName("Id")
        val id: String,
        @SerializedName("Name")
        val name: String
    ) {
        data class Filter(
            @SerializedName("Id")
            val id: String,
            @SerializedName("Name")
            val name: String
        )
    }

    data class Error(
        @SerializedName("Code")
        val code: Int,
        @SerializedName("Message")
        val message: String
    )
}