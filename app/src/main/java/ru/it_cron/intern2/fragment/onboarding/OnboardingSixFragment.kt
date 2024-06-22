package ru.it_cron.intern2.fragment.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject
import ru.it_cron.intern2.databinding.FragmentOnboardingSixBinding
import ru.it_cron.intern2.navigation.Screens

class OnboardingSixFragment : Fragment() {

    private val router by inject<Router>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentOnboardingSixBinding.inflate(inflater, container, false)

        binding.buttonNext.setOnClickListener {
            router.replaceScreen(Screens.onboardingSevenFragment())
        }

        return binding.root
    }

}