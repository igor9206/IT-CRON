package ru.it_cron.intern2.dto

import com.google.gson.annotations.SerializedName

data class Menu(
    @SerializedName("Error")
    val error: String?,
    @SerializedName("Data")
    val `data`: Data
) {
    data class Data(
        @SerializedName("IsCabinetAvailable")
        val isCabinetAvailable: Boolean
    )
}