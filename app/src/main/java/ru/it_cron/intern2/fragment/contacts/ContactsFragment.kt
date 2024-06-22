package ru.it_cron.intern2.fragment.contacts

import android.os.Bundle
import android.text.Spannable
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.buildSpannedString
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject
import ru.it_cron.intern2.R
import ru.it_cron.intern2.databinding.CardRequisitesBinding
import ru.it_cron.intern2.databinding.CardWorksTimeBinding
import ru.it_cron.intern2.databinding.FragmentContactsBinding
import ru.it_cron.intern2.navigation.Screens
import ru.it_cron.intern2.util.Constants
import ru.it_cron.intern2.util.Intents
import java.util.Calendar

class ContactsFragment : Fragment() {

    private val router by inject<Router>()
    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentContactsBinding.inflate(inflater, container, false)
        .apply { _binding = this }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentDay = getCurrentDay()
        val timeWork = resources.getTextArray(R.array.work_time)

        val requisites = resources.getStringArray(R.array.requisites)
            .toList()
            .chunked(2)
            .associate { (key, value) -> key to value }

        with(binding) {
            buttonBack.setOnClickListener { router.exit() }
            buttonShare.setOnClickListener {
                Intents.shareIntent(
                    requisites.entries.joinToString("\n") { (key, value) -> "$key: $value" },
                    requireContext()
                )
            }
            buttonInstagram.setOnClickListener {
                Intents.instagramIntent(
                    requireContext()
                )
            }
            buttonFacebook.setOnClickListener { Intents.facebookIntent(requireContext()) }
            buttonTelegram.setOnClickListener { Intents.telegramIntent(requireContext()) }
            customerContainer.tvEmailItCron.setOnClickListener {
                Intents.emailIntent(
                    requireContext(),
                    Constants.EMAIL_IT_CRON
                )
            }
            customerContainer.buttonApplication.setOnClickListener {
                router.navigateTo(Screens.applicationFragment())
            }
            customerContainer.buttonTelegramBottom.setOnClickListener {
                Intents.telegramIntent(requireContext())
            }

            timeWork.forEach {
                CardWorksTimeBinding.inflate(
                    LayoutInflater.from(requireContext()),
                    timeWorkContainer,
                    true
                ).apply {
                    content.text = it
                    cardDay.alpha = when {
                        it.contains(currentDay, ignoreCase = true) -> 1F
                        else -> 0.5F
                    }
                }
            }

            requisites.forEach { (key, value) ->
                CardRequisitesBinding.inflate(
                    LayoutInflater.from(requireContext()),
                    requisitesContainer,
                    true
                ).apply {
                    title.apply {
                        text = key
                        alpha = 0.5F
                    }
                    content.text = value
                }
            }

            tvPortfolio.text = buildSpannedString {
                append(getString(R.string.portfolio))
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
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getCurrentDay(): String {
        val currentDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        return when (currentDay) {
            Calendar.MONDAY -> getString(R.string.monday)
            Calendar.TUESDAY -> getString(R.string.tuesday)
            Calendar.WEDNESDAY -> getString(R.string.wednesday)
            Calendar.THURSDAY -> getString(R.string.thursday)
            Calendar.FRIDAY -> getString(R.string.friday)
            else -> getString(R.string.weekend)
        }
    }

}