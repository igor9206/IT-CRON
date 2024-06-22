package ru.it_cron.intern2.fragment.application

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import ru.it_cron.intern2.databinding.DialogErrorRequestBinding

class ErrorRequestDialog : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, android.R.style.Theme_NoTitleBar_Fullscreen)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DialogErrorRequestBinding.inflate(inflater, container, false)
        binding.buttonOk.setOnClickListener { dismiss() }
        return binding.root
    }

    companion object {
        const val TAG = "ErrorRequestDialog"
    }
}