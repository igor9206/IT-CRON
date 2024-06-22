package ru.it_cron.intern2.fragment.company

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import ru.it_cron.intern2.adapter.testimonial.TestimonialRvAdapter
import ru.it_cron.intern2.databinding.FragmentTestimonialBinding
import ru.it_cron.intern2.navigation.Screens
import ru.it_cron.intern2.util.Constants
import ru.it_cron.intern2.util.Intents
import ru.it_cron.intern2.viewmodel.CompanyViewModel

class TestimonialFragment : Fragment() {

    private val companyViewModel: CompanyViewModel by activityViewModel()
    private val router by inject<Router>()
    private var _binding: FragmentTestimonialBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentTestimonialBinding.inflate(inflater, container, false)
        .apply { _binding = this }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val testimonialRvAdapter = TestimonialRvAdapter()

        with(binding) {
            rvTestimonial.adapter = testimonialRvAdapter
            buttonBack.setOnClickListener { router.exit() }
            buttonShowMore.setOnClickListener { companyViewModel.showMore() }
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
        }

        companyViewModel.portionTestimonials.observe(viewLifecycleOwner) {
            companyViewModel.testimonials.value?.testimonial?.data?.let { allTestimonial ->
                binding.buttonShowMore.isVisible = allTestimonial.size > it.size
            }
            testimonialRvAdapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}