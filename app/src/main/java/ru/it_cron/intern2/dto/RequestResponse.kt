package ru.it_cron.intern2.dto

import com.google.gson.annotations.SerializedName

data class RequestResponse(
    @SerializedName("Data")
    val `data`: String?,
    @SerializedName("Error")
    val error: Error?,
) {
    data class Error(
        @SerializedName("Code")
        val code: Int,
        @SerializedName("Message")
        val message: String?
    )
}