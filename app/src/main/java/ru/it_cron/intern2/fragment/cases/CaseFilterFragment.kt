package ru.it_cron.intern2.fragment.cases

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import ru.it_cron.intern2.adapter.case.CaseFilterAdapter
import ru.it_cron.intern2.adapter.case.OnCaseFilterClickListener
import ru.it_cron.intern2.databinding.FragmentCaseFilterBinding
import ru.it_cron.intern2.model.CaseFilterName
import ru.it_cron.intern2.navigation.NavigationCommand
import ru.it_cron.intern2.navigation.Screens
import ru.it_cron.intern2.viewmodel.CaseViewModel

class CaseFilterFragment : Fragment() {

    private val caseViewModel: CaseViewModel by activityViewModel()
    private val router by inject<Router>()
    private lateinit var adapter: CaseFilterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = CaseFilterAdapter(object : OnCaseFilterClickListener {
            override fun select(caseFilterName: CaseFilterName) {
                caseViewModel.setFilter(caseFilterName)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCaseFilterBinding.inflate(inflater, container, false)

        getFilters()

        with(binding) {
            rvCaseFilters.adapter = adapter

            buttonCleaner.setOnClickListener {
                caseViewModel.cleanFilter()
            }

            buttonBack.setOnClickListener {
                router.exit()
            }
        }

        caseViewModel.caseFilters.observe(viewLifecycleOwner) { caseFilterModel ->
            adapter.submitList(caseFilterModel.filters)
            binding.buttonCleaner.isVisible = caseFilterModel.filters
                .filterIsInstance<CaseFilterName>()
                .firstOrNull { it.selected } != null

            when (caseFilterModel.load) {
                true -> binding.progressBar.show()
                else -> binding.progressBar.hide()
            }
        }

        caseViewModel.navigationEvent.observe(viewLifecycleOwner) {
            when (it) {
                is NavigationCommand.ToErrorNetworkFragment -> {
                    router.replaceScreen(Screens.errorInternetFragment())
                }

                else -> {}
            }
        }

        return binding.root
    }

    private fun getFilters() {
        when {
            caseViewModel.caseFilters.value == null -> caseViewModel.getCaseFilters()
            caseViewModel.caseFilters.value?.error == true -> caseViewModel.getCaseFilters()
        }
    }

}