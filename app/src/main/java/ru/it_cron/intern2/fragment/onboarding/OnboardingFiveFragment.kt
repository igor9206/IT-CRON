package ru.it_cron.intern2.fragment.onboarding

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject
import ru.it_cron.intern2.databinding.FragmentOnboardingFiveBinding
import ru.it_cron.intern2.navigation.Screens
import ru.it_cron.intern2.util.Constants

class OnboardingFiveFragment : Fragment() {

    private val router by inject<Router>()
    private val prefs by inject<SharedPreferences>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentOnboardingFiveBinding.inflate(inflater, container, false)

        binding.buttonStart.setOnClickListener {
            prefs.edit().putBoolean(Constants.FIRST_START_APP, false).apply()
            router.newRootScreen(Screens.mainFragment())
        }

        return binding.root
    }

}