package ru.it_cron.intern2.dto

import com.google.gson.annotations.SerializedName

data class Testimonial(
    @SerializedName("Error")
    val error: Error?,
    @SerializedName("Data")
    val `data`: List<Data>
) {
    data class Data(
        @SerializedName("CaseId")
        val caseId: String,
        @SerializedName("Company")
        val company: String,
        @SerializedName("CustomerName")
        val customerName: String,
        @SerializedName("Icon")
        val icon: String,
        @SerializedName("Id")
        val id: String,
        @SerializedName("Text")
        val text: String
    )

    data class Error(
        @SerializedName("Code")
        val code: Int,
        @SerializedName("Message")
        val message: String
    )
}