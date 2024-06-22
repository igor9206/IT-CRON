package ru.it_cron.intern2.repository

import ru.it_cron.intern2.dto.Case
import ru.it_cron.intern2.dto.CaseFilter
import ru.it_cron.intern2.dto.DetailCase
import ru.it_cron.intern2.dto.Menu
import ru.it_cron.intern2.dto.RequestResponse
import ru.it_cron.intern2.dto.Testimonial
import ru.it_cron.intern2.model.ApplicationModel
import ru.it_cron.intern2.model.AttachmentModel

interface Repository {
    suspend fun getMenu(): Result<Menu>
    suspend fun getCases(): Result<Case>
    suspend fun getCaseFilters(): Result<CaseFilter>
    suspend fun getCaseById(id: String): Result<DetailCase>
    suspend fun getTestimonials(): Result<Testimonial>
    suspend fun request(
        applicationModel: ApplicationModel,
        attachmentModel: List<AttachmentModel>
    ): Result<RequestResponse>
}