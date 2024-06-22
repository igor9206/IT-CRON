package ru.it_cron.intern2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.it_cron.intern2.model.ApplicationModel
import ru.it_cron.intern2.model.AttachmentModel
import ru.it_cron.intern2.model.RequiredFieldsStateModel
import ru.it_cron.intern2.navigation.NavigationCommand
import ru.it_cron.intern2.repository.Repository
import ru.it_cron.intern2.util.SingleLiveEvent

class ApplicationViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _application = MutableLiveData(ApplicationModel())
    val application: LiveData<ApplicationModel> = _application

    private val _attachment = MutableLiveData<List<AttachmentModel>>(listOf())
    val attachment: LiveData<List<AttachmentModel>> = _attachment

    private val _navigationEvent = SingleLiveEvent<NavigationCommand>()
    val navigationEvent: LiveData<NavigationCommand> = _navigationEvent

    private val _stateFields = MutableLiveData(RequiredFieldsStateModel())
    val stateFields: LiveData<RequiredFieldsStateModel> = _stateFields

    fun setService(service: String) {
        _application.value = _application.value?.let { applicationModel ->
            applicationModel.copy(
                services = if (applicationModel.services.contains(service)) {
                    applicationModel.services.filter { it != service }
                } else {
                    applicationModel.services + listOf(service)
                }
            )
        }
    }

    fun setBudget(budget: String) {
        _application.value = _application.value?.let { applicationModel ->
            applicationModel.copy(
                budget = if (budget == applicationModel.budget) "" else budget
            )
        }
        checkFields()
    }

    fun setFindUs(from: String) {
        _application.value = _application.value?.let { applicationModel ->
            applicationModel.copy(
                findUs = if (from == applicationModel.findUs) "" else from
            )
        }
        checkFields()
    }

    fun setDescription(content: String) {
        _application.value = _application.value?.copy(
            description = content
        )
        checkFields()
    }

    fun setContactName(name: String) {
        _application.value = _application.value?.copy(
            contactName = name
        )
        checkFields()
    }

    fun setCompany(companyName: String) {
        _application.value = _application.value?.copy(
            company = companyName
        )
    }

    fun setContactEmail(email: String) {
        _application.value = _application.value?.copy(
            contactEmail = email
        )
        checkFields()
        checkEmail()
    }

    fun setContactPhone(phone: String) {
        _application.value = _application.value?.copy(
            contactPhone = phone
        )
        checkFields()
    }

    fun setProcessingData() {
        _application.value = _application.value?.let { applicationModel ->
            applicationModel.copy(
                processingData = !applicationModel.processingData
            )
        }
        checkFields()
    }

    fun setPrivacyPolicy() {
        _application.value = _application.value?.let { applicationModel ->
            applicationModel.copy(
                privacyPolicy = !applicationModel.privacyPolicy
            )
        }
        checkFields()
    }

    fun setAttachment(attachmentModel: AttachmentModel) {
        val size = attachmentModel.size
        _attachment.value = _attachment.value?.let { attachments ->
            when {
                (attachments.sumOf { it.size } + size) > 15 -> return
                attachments.isEmpty() -> listOf(attachmentModel)
                else -> attachments + attachmentModel.copy(id = attachments.last().id + 1)
            }
        }
        checkAttachmentRequirements()
    }

    fun removeAttachment(attachmentModel: AttachmentModel) {
        _attachment.value = attachment.value?.filter { it != attachmentModel }
        checkAttachmentRequirements()
    }

    fun sendApplication() = viewModelScope.launch {
        val applicationModel = _application.value ?: return@launch
        val attachments = _attachment.value ?: return@launch

        repository.request(applicationModel, attachments)
            .onSuccess {
                when {
                    it.error == null -> {
                        _navigationEvent.value = NavigationCommand.RequestSuccess
                        cleanApplication()
                    }

                    else -> _navigationEvent.value = NavigationCommand.RequestFailure
                }
            }
            .onFailure {
                _navigationEvent.value = NavigationCommand.ToErrorNetworkFragment
            }
    }

    private fun cleanApplication() {
        _application.value = ApplicationModel()
        _attachment.value = listOf()
    }

    private fun checkFields() {
        val applicationModel = _application.value ?: return

        val fields = with(applicationModel) {
            description.isNotBlank() && contactName.isNotBlank()
                    && contactEmail.isNotBlank() && contactPhone.isNotBlank()
                    && services.isNotEmpty() && budget.isNotBlank()
                    && findUs.isNotBlank() && contactPhone.length == 17
                    && processingData && privacyPolicy

        }
        _stateFields.value = _stateFields.value?.copy(
            fieldsIsNotEmpty = fields
        )
    }

    private fun checkEmail() {
        val applicationModel = _application.value ?: return

        val isValid = android.util.Patterns.EMAIL_ADDRESS
            .matcher(applicationModel.contactEmail)
            .matches()
        _stateFields.value = _stateFields.value?.copy(
            emailIsValid = isValid
        )
    }

    private fun checkAttachmentRequirements() {
        val attachmentModel = _attachment.value ?: return
        val maxSizeAttachments = 15
        val maxCountAttachments = 5
        val check = attachmentModel.sumOf { it.size } < maxSizeAttachments
                && attachmentModel.size < maxCountAttachments

        _stateFields.value = _stateFields.value?.copy(
            attachmentIsValid = check
        )
    }

}