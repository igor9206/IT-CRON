package ru.it_cron.intern2.fragment.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import ru.it_cron.intern2.databinding.FragmentSplashBinding
import ru.it_cron.intern2.viewmodel.MainViewModel

class SplashFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSplashBinding.inflate(inflater, container, false)

        mainViewModel.getMenu()

        return binding.root
    }

}