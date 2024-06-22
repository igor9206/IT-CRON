package ru.it_cron.intern2.fragment.error

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject
import ru.it_cron.intern2.databinding.FragmentErrorInternetBinding

class ErrorInternetFragment : Fragment() {

    private val router by inject<Router>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentErrorInternetBinding.inflate(inflater, container, false)

        binding.buttonClose.setOnClickListener {
            router.exit()
        }

        return binding.root
    }

}