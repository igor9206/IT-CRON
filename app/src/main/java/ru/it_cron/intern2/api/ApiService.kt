package ru.it_cron.intern2.api

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import ru.it_cron.intern2.dto.Case
import ru.it_cron.intern2.dto.CaseFilter
import ru.it_cron.intern2.dto.DetailCase
import ru.it_cron.intern2.dto.Menu
import ru.it_cron.intern2.dto.RequestResponse
import ru.it_cron.intern2.dto.Testimonial

interface ApiService {
    @GET("cabinet/menu")
    suspend fun getMenu(): Response<Menu>

    @Headers("Accept-Language: ru")
    @GET("cases")
    suspend fun getCases(): Response<Case>

    @Headers("Accept-Language: ru")
    @GET("filters")
    suspend fun getCaseFilters(): Response<CaseFilter>

    @Headers("Accept-Language: ru")
    @GET("cases/{id}")
    suspend fun getCaseById(@Path("id") id: String): Response<DetailCase>

    @Headers("Accept-Language: ru")
    @GET("testimonials")
    suspend fun getTestimonials(): Response<Testimonial>

    @Multipart
    @POST("request")
    suspend fun request(
        @Part("Services") services: String,
        @Part("Budget") budget: String,
        @Part("Description") description: String,
        @Part("ContactName") contactName: String,
        @Part("ContactCompany") contactCompany: String,
        @Part("ContactEmail") contactEmail: String,
        @Part("ContactPhone") contactPhone: String,
        @Part("RequestFrom") requestFrom: String
    ): Response<RequestResponse>

    @Multipart
    @POST("request")
    suspend fun requestWithAttachment(
        @Part("Services") services: String,
        @Part("Budget") budget: String,
        @Part("Description") description: String,
        @Part fileDescriptions: List<MultipartBody.Part>,
        @Part("ContactName") contactName: String,
        @Part("ContactCompany") contactCompany: String,
        @Part("ContactEmail") contactEmail: String,
        @Part("ContactPhone") contactPhone: String,
        @Part("RequestFrom") requestFrom: String
    ): Response<RequestResponse>
}