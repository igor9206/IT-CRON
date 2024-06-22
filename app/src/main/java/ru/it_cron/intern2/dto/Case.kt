package ru.it_cron.intern2.dto

import com.google.gson.annotations.SerializedName

data class Case(
    @SerializedName("Error")
    val error: Error?,
    @SerializedName("Data")
    val `data`: List<Data>
) {
    data class Error(
        @SerializedName("Code")
        val code: Int,
        @SerializedName("Message")
        val message: String
    )

    data class Data(
        @SerializedName("CaseColor")
        val caseColor: String,
        @SerializedName("FeaturesTitle")
        val featuresTitle: String,
        @SerializedName("Filters")
        val filters: List<Filter>,
        @SerializedName("FriendlyURL")
        val friendlyURL: String,
        @SerializedName("Id")
        val id: String,
        @SerializedName("Image")
        val image: String,
        @SerializedName("Title")
        val title: String
    ) {
        data class Filter(
            @SerializedName("Id")
            val id: String,
            @SerializedName("Name")
            val name: String
        )
    }
}