package ru.it_cron.intern2.fragment.application

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.buildSpannedString
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Router
import com.redmadrobot.inputmask.MaskedTextChangedListener
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import ru.it_cron.intern2.R
import ru.it_cron.intern2.adapter.application.AttachmentAdapter
import ru.it_cron.intern2.adapter.application.AttachmentClickListener
import ru.it_cron.intern2.databinding.CardAttachmentMenuBinding
import ru.it_cron.intern2.databinding.FragmentApplicationBinding
import ru.it_cron.intern2.fragment.agreement.PersonalDataDialog
import ru.it_cron.intern2.fragment.agreement.PrivacyPolicyDialog
import ru.it_cron.intern2.model.ApplicationModel
import ru.it_cron.intern2.model.AttachmentModel
import ru.it_cron.intern2.model.RequiredFieldsStateModel
import ru.it_cron.intern2.navigation.NavigationCommand
import ru.it_cron.intern2.navigation.Screens
import ru.it_cron.intern2.viewmodel.ApplicationViewModel

class ApplicationFragment : Fragment() {

    private val applicationViewModel: ApplicationViewModel by activityViewModel()
    private val router by inject<Router>()
    private var _binding: FragmentApplicationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentApplicationBinding.inflate(inflater, container, false)

        setContent()

        applicationViewModel.application.observe(viewLifecycleOwner) { applicationModel ->
            setApplicationContent(applicationModel)
        }

        applicationViewModel.attachment.observe(viewLifecycleOwner) { attachments ->
            setAttachmentContent(attachments)
        }

        applicationViewModel.navigationEvent.observe(viewLifecycleOwner) { event ->
            when (event) {
                NavigationCommand.RequestFailure -> showErrorRequestDialog()
                NavigationCommand.RequestSuccess -> showSuccessRequestDialog()
                NavigationCommand.ToErrorNetworkFragment -> router.navigateTo(Screens.errorInternetFragment())
            }
        }

        applicationViewModel.stateFields.observe(viewLifecycleOwner) {
            setButtonAvailability(it)
        }

        dataProcessingPrivacyPolicy()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setContent() {
        with(binding) {
            buttonBack.setOnClickListener { router.exit() }
            buttonAttach.setOnClickListener {
                AttachBottomSheets().show(
                    requireActivity().supportFragmentManager,
                    AttachBottomSheets.TAG
                )
            }
            buttonApplication.setOnClickListener {
                applicationViewModel.sendApplication()
            }

            etDescription.addTextChangedListener {
                applicationViewModel.setDescription(it.toString())
            }
            etName.addTextChangedListener {
                applicationViewModel.setContactName(it.toString())
            }
            etCompany.addTextChangedListener {
                applicationViewModel.setCompany(it.toString())
            }
            etEmail.addTextChangedListener {
                applicationViewModel.setContactEmail(it.toString())
            }
            MaskedTextChangedListener.installOn(
                etPhoneNumber,
                getString(R.string.phone_number_format),
                object : MaskedTextChangedListener.ValueListener {
                    override fun onTextChanged(
                        maskFilled: Boolean,
                        extractedValue: String,
                        formattedValue: String,
                        tailPlaceholder: String
                    ) {
                        applicationViewModel.setContactPhone(formattedValue)
                    }
                })
        }
    }

    private fun setApplicationContent(applicationModel: ApplicationModel) {
        val services = listOf(
            getString(R.string.ux_test),
            getString(R.string.mobile_app_design),
            getString(R.string.web_design),
            getString(R.string.web_develop),
            getString(R.string.mobile_app_develop),
            getString(R.string.strategy),
            getString(R.string.creative),
            getString(R.string.analytics),
            getString(R.string.testing),
            getString(R.string.other),
        )
        val budget = listOf(
            getString(R.string.budget_05),
            getString(R.string.budget_1),
            getString(R.string.budget_1_3),
            getString(R.string.budget_3_5),
            getString(R.string.budget_5_10),
            getString(R.string.budget_more_10)
        )
        val findUs = listOf(
            getString(R.string.social_network),
            getString(R.string.recomendation),
            getString(R.string.works),
            getString(R.string.ratings),
            getString(R.string.mailing),
            getString(R.string.ad),
        )

        with(binding) {
            serviceContainer.removeAllViews()
            services.forEach { item ->
                CardAttachmentMenuBinding.inflate(
                    LayoutInflater.from(requireContext()),
                    serviceContainer,
                    true
                ).apply {
                    tv.text = item
                    cardService.setOnClickListener { applicationViewModel.setService(item) }

                    val isServiceSelected = applicationModel.services.contains(tv.text)
                    tv.setTextColor(if (isServiceSelected) Color.WHITE else Color.BLACK)
                    cardService.isChecked = isServiceSelected
                }.root
            }

            budgetContainer.removeAllViews()
            budget.forEach { item ->
                CardAttachmentMenuBinding.inflate(
                    LayoutInflater.from(requireContext()),
                    budgetContainer,
                    true
                ).apply {
                    tv.text = item
                    cardService.setOnClickListener { applicationViewModel.setBudget(item) }

                    val isServiceSelected = applicationModel.budget.contains(tv.text)
                    tv.setTextColor(if (isServiceSelected) Color.WHITE else Color.BLACK)
                    cardService.isChecked = isServiceSelected
                }.root
            }

            findUsContainer.removeAllViews()
            findUs.forEach { item ->
                CardAttachmentMenuBinding.inflate(
                    LayoutInflater.from(requireContext()),
                    findUsContainer,
                    true
                ).apply {
                    tv.text = item
                    cardService.setOnClickListener { applicationViewModel.setFindUs(item) }

                    val isServiceSelected = applicationModel.findUs.contains(tv.text)
                    tv.setTextColor(if (isServiceSelected) Color.WHITE else Color.BLACK)
                    cardService.isChecked = isServiceSelected
                }.root
            }

            etDescription.text.let {
                if (it.toString() == applicationModel.description) {
                    return@let
                }
                etDescription.setText(applicationModel.description)
            }
            etName.text.let {
                if (it.toString() == applicationModel.contactName) {
                    return@let
                }
                etName.setText(applicationModel.contactName)
            }
            etCompany.text.let {
                if (it.toString() == applicationModel.company) {
                    return@let
                }
                etCompany.setText(applicationModel.company)
            }
            etEmail.text.let {
                if (it.toString() == applicationModel.contactEmail) {
                    return@let
                }
                etEmail.setText(applicationModel.contactEmail)
            }
            etPhoneNumber.text.let {
                if (it.toString() == applicationModel.contactPhone) {
                    return@let
                }
                etPhoneNumber.setText(applicationModel.contactPhone)
            }

            buttonProcessingData.isChecked = applicationModel.processingData
            buttonPrivacyPolicy.isChecked = applicationModel.privacyPolicy
        }
    }

    private fun setAttachmentContent(attachments: List<AttachmentModel>) {
        with(binding) {
            rvAttachment.apply {
                isVisible = attachments.isNotEmpty()
                adapter = AttachmentAdapter(attachments, object : AttachmentClickListener {
                    override fun removeAttachment(attachmentModel: AttachmentModel) {
                        applicationViewModel.removeAttachment(attachmentModel)
                    }
                })
            }
        }
    }

    private fun dataProcessingPrivacyPolicy() {
        val clickablePersonalData = object : ClickableSpan() {
            override fun onClick(widget: View) {
                when (widget.id) {
                    R.id.tvProcessingData -> {
                        showPersonalDataDialog()
                    }

                    R.id.tvPrivacyPolicy -> {
                        showPrivacyPolicyDialog()
                    }
                }

            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
            }
        }

        with(binding) {
            tvProcessingData.apply {
                text = buildSpannedString {
                    append(getString(R.string.give_consent))
                    append(
                        getString(R.string.processing_personal_data),
                        clickablePersonalData,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
                movementMethod = LinkMovementMethod.getInstance()
            }

            tvPrivacyPolicy.apply {
                text = buildSpannedString {
                    append(getString(R.string.agree_with))
                    append(
                        getString(R.string.privacy),
                        clickablePersonalData,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
                movementMethod = LinkMovementMethod.getInstance()
            }

            buttonProcessingData.setOnClickListener {
                applicationViewModel.setProcessingData()
            }

            buttonPrivacyPolicy.setOnClickListener {
                applicationViewModel.setPrivacyPolicy()
            }
        }
    }

    private fun setButtonAvailability(state: RequiredFieldsStateModel) {
        with(binding) {
            buttonApplication.apply {
                if (state.fieldsIsNotEmpty && state.emailIsValid) {
                    isClickable = true
                    setCardForegroundColor(ColorStateList.valueOf(Color.TRANSPARENT))
                } else {
                    isClickable = false
                    setCardForegroundColor(ColorStateList.valueOf(Color.BLACK).withAlpha(90))
                }
            }

            etEmail.error = if (state.emailIsValid) null else getString(R.string.incorrect_email)

            buttonAttach.apply {
                isClickable = state.attachmentIsValid
                setCardForegroundColor(
                    if (state.attachmentIsValid) {
                        ColorStateList.valueOf(Color.TRANSPARENT)
                    } else ColorStateList.valueOf(Color.BLACK).withAlpha(90)
                )
            }
        }
    }

    private fun showPersonalDataDialog() {
        PersonalDataDialog().show(childFragmentManager, PersonalDataDialog.TAG)
    }

    private fun showPrivacyPolicyDialog() {
        PrivacyPolicyDialog().show(childFragmentManager, PrivacyPolicyDialog.TAG)
    }

    private fun showErrorRequestDialog() {
        ErrorRequestDialog().show(childFragmentManager, ErrorRequestDialog.TAG)
    }

    private fun showSuccessRequestDialog() {
        SuccessRequestDialog().show(childFragmentManager, SuccessRequestDialog.TAG)
    }

}