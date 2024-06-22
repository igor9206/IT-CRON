package ru.it_cron.intern2.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import ru.it_cron.intern2.databinding.FragmentMainBinding
import ru.it_cron.intern2.navigation.Screens
import ru.it_cron.intern2.util.ConnectivityObserver
import ru.it_cron.intern2.util.Constants
import ru.it_cron.intern2.util.Intents
import ru.it_cron.intern2.viewmodel.MainViewModel

class MainFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModel()
    private val router by inject<Router>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater, container, false)

        with(binding) {
            tvCases.setOnClickListener {
                when (mainViewModel.networkStatus.value) {
                    ConnectivityObserver.Status.AVAILABLE -> router.navigateTo(Screens.casesFragment())
                    else -> router.navigateTo(Screens.errorInternetFragment())
                }
            }
            tvCompany.setOnClickListener { router.navigateTo(Screens.companyFragment()) }
            tvServices.setOnClickListener { router.navigateTo(Screens.serviceFragment()) }
            tvContacts.setOnClickListener { router.navigateTo(Screens.contactsFragment()) }

            tvEmail.setOnClickListener {
                Intents.emailIntent(
                    requireContext(),
                    Constants.EMAIL_IT_CRON
                )
            }
            ivFacebook.setOnClickListener { Intents.facebookIntent(requireContext()) }
            ivInstagram.setOnClickListener { Intents.instagramIntent(requireContext()) }
            ivTelegram.setOnClickListener { Intents.telegramIntent(requireContext()) }

            buttonApplication.setOnClickListener { router.navigateTo(Screens.applicationFragment()) }
        }

        return binding.root
    }

}