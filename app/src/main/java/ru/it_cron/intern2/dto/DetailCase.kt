package ru.it_cron.intern2.dto

import com.google.gson.annotations.SerializedName

data class DetailCase(
    @SerializedName("Data")
    val `data`: Data,
    @SerializedName("Error")
    val error: Error
) {
    data class Data(
        @SerializedName("AndroidUrl")
        val androidUrl: String?,
        @SerializedName("Audience")
        val audience: String,
        @SerializedName("CaseColor")
        val caseColor: String,
        @SerializedName("FeaturesDone")
        val featuresDone: List<String>?,
        @SerializedName("FeaturesTitle")
        val featuresTitle: String?,
        @SerializedName("Filters")
        val filters: List<Filter>,
        @SerializedName("FriendlyURL")
        val friendlyURL: String,
        val iOSUrl: String?,
        @SerializedName("Id")
        val id: String,
        @SerializedName("Images")
        val images: List<String>,
        @SerializedName("Platforms")
        val platforms: List<Platform>,
        @SerializedName("QRCode")
        val qRCode: String,
        @SerializedName("ShortQrCode")
        val shortQrCode: String,
        @SerializedName("Stages")
        val stages: List<Stage>,
        @SerializedName("Task")
        val task: String,
        @SerializedName("Technologies")
        val technologies: List<Technology>,
        @SerializedName("TestimonialId")
        val testimonialId: String,
        @SerializedName("Title")
        val title: String,
        @SerializedName("WebUrl")
        val webUrl: String?
    ) {
        data class Filter(
            @SerializedName("Id")
            val id: String,
            @SerializedName("Name")
            val name: String
        )

        data class Platform(
            @SerializedName("Id")
            val id: String,
            @SerializedName("Name")
            val name: String
        )

        data class Stage(
            @SerializedName("Id")
            val id: String,
            @SerializedName("Name")
            val name: String
        )

        data class Technology(
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