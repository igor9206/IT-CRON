package ru.it_cron.intern2.fragment.company

import android.os.Bundle
import android.text.Spannable
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.buildSpannedString
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Router
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import ru.it_cron.intern2.R
import ru.it_cron.intern2.adapter.testimonial.TestimonialVpAdapter
import ru.it_cron.intern2.databinding.FragmentCompanyBinding
import ru.it_cron.intern2.navigation.Screens
import ru.it_cron.intern2.util.Constants
import ru.it_cron.intern2.util.Intents
import ru.it_cron.intern2.viewmodel.CompanyViewModel

class CompanyFragment : Fragment() {

    private val companyViewModel: CompanyViewModel by activityViewModel()
    private val router by inject<Router>()
    private lateinit var vpAdapter: TestimonialVpAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCompanyBinding.inflate(inflater, container, false)

        with(binding) {
            buttonBack.setOnClickListener { router.exit() }

            tbInstagram.setOnClickListener { Intents.instagramIntent(requireContext()) }
            tbFacebook.setOnClickListener { Intents.facebookIntent(requireContext()) }
            tbTelegram.setOnClickListener { Intents.telegramIntent(requireContext()) }

            tvInstagram.setOnClickListener { Intents.instagramIntent(requireContext()) }
            tvFacebook.setOnClickListener { Intents.facebookIntent(requireContext()) }
            tvTelegram.setOnClickListener { Intents.telegramIntent(requireContext()) }

            tvJoinUs.text = buildSpannedString {
                append(getString(R.string.join_us_description))
                append(
                    Constants.EMAIL_HR,
                    object : ClickableSpan() {
                        override fun onClick(widget: View) {
                            Intents.emailIntent(requireContext(), Constants.EMAIL_HR)
                        }
                    },
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

            customerContainer.tvEmailItCron.setOnClickListener {
                Intents.emailIntent(
                    requireContext(),
                    Constants.EMAIL_IT_CRON
                )
            }
            customerContainer.buttonApplication.setOnClickListener { router.navigateTo(Screens.applicationFragment()) }

            customerContainer.buttonTelegramBottom.setOnClickListener {
                Intents.telegramIntent(
                    requireContext()
                )
            }

            showAll.setOnClickListener { router.navigateTo(Screens.testimonialFragment()) }
        }

        companyViewModel.testimonials.observe(viewLifecycleOwner) {
            if (it.testimonial?.data != null) {
                vpAdapter = TestimonialVpAdapter(it.testimonial.data)
                binding.vpFeedback.adapter = vpAdapter
                TabLayoutMediator(binding.tabLayout, binding.vpFeedback) { _, _ ->
                }.attach()

            }

            binding.containerFeedback.isVisible = !it.error || !it.testimonial?.data.isNullOrEmpty()
        }

        return binding.root
    }

}