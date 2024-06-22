package ru.it_cron.intern2.fragment.application

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject
import ru.it_cron.intern2.databinding.DialogSuccessRequestBinding
import ru.it_cron.intern2.navigation.Screens

class SuccessRequestDialog : DialogFragment() {

    private val router by inject<Router>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, android.R.style.Theme_NoTitleBar_Fullscreen)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DialogSuccessRequestBinding.inflate(inflater, container, false)
        binding.buttonOk.setOnClickListener {
            dismiss()
            router.newRootScreen(Screens.mainFragment())
        }
        return binding.root
    }

    companion object {
        const val TAG = "SuccessRequestDialog"
    }
}