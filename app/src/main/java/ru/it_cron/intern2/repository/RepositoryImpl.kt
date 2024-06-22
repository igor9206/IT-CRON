package ru.it_cron.intern2.repository

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import ru.it_cron.intern2.api.ApiService
import ru.it_cron.intern2.dto.Case
import ru.it_cron.intern2.dto.CaseFilter
import ru.it_cron.intern2.dto.DetailCase
import ru.it_cron.intern2.dto.Menu
import ru.it_cron.intern2.dto.RequestResponse
import ru.it_cron.intern2.dto.Testimonial
import ru.it_cron.intern2.model.ApplicationModel
import ru.it_cron.intern2.model.AttachmentModel

class RepositoryImpl(
    private val apiService: ApiService
) : Repository {

    override suspend fun getMenu(): Result<Menu> = runCatching {
        val response = apiService.getMenu()
        response.body() ?: error("${response.code()} + ${response.message()}")
    }

    override suspend fun getCases(): Result<Case> = runCatching {
        val response = apiService.getCases()
        response.body() ?: error("${response.code()} + ${response.message()}")
    }

    override suspend fun getCaseFilters(): Result<CaseFilter> = runCatching {
        val response = apiService.getCaseFilters()
        response.body() ?: error("${response.code()} + ${response.message()}")
    }

    override suspend fun getCaseById(id: String): Result<DetailCase> = runCatching {
        val response = apiService.getCaseById(id)
        response.body() ?: error("${response.code()} + ${response.message()}")
    }

    override suspend fun getTestimonials(): Result<Testimonial> = runCatching {
        val response = apiService.getTestimonials()
        response.body() ?: error("${response.code()} + ${response.message()}")
    }

    override suspend fun request(
        applicationModel: ApplicationModel,
        attachmentModel: List<AttachmentModel>
    ): Result<RequestResponse> = runCatching {
        val response = when {
            attachmentModel.isEmpty() -> apiService.request(
                applicationModel.services.toString(),
                applicationModel.budget,
                applicationModel.description,
                applicationModel.contactName,
                applicationModel.company,
                applicationModel.contactEmail,
                applicationModel.contactPhone,
                applicationModel.findUs
            )

            else -> {
                val attachments = attachmentModel.map {
                    MultipartBody.Part.createFormData(
                        "FileDescriptions",
                        it.file.name,
                        RequestBody.create(MediaType.parse(it.mimeType), it.file)
                    )
                }
                apiService.requestWithAttachment(
                    applicationModel.services.toString(),
                    applicationModel.budget,
                    applicationModel.description,
                    attachments,
                    applicationModel.contactName,
                    applicationModel.company,
                    applicationModel.contactEmail,
                    applicationModel.contactPhone,
                    applicationModel.findUs
                )
            }
        }

        response.body() ?: error("${response.code()} + ${response.message()}")
    }

}