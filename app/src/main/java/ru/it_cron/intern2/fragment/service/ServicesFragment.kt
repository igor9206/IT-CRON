package ru.it_cron.intern2.fragment.service

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.it_cron.intern2.databinding.FragmentServicesBinding

class ServicesFragment : Fragment() {

    private var _binding: FragmentServicesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentServicesBinding.inflate(inflater, container, false)
        .apply { _binding = this }
        .root

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}