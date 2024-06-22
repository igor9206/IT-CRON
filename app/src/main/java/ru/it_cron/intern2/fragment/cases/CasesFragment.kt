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
import ru.it_cron.intern2.adapter.case.CaseAdapter
import ru.it_cron.intern2.adapter.case.OnCaseClickListener
import ru.it_cron.intern2.databinding.FragmentCasesBinding
import ru.it_cron.intern2.dto.Case
import ru.it_cron.intern2.model.CaseFilterName
import ru.it_cron.intern2.navigation.NavigationCommand
import ru.it_cron.intern2.navigation.Screens
import ru.it_cron.intern2.util.ConnectivityObserver
import ru.it_cron.intern2.viewmodel.CaseViewModel
import ru.it_cron.intern2.viewmodel.MainViewModel

class CasesFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModel()
    private val caseViewModel: CaseViewModel by activityViewModel()
    private val router by inject<Router>()
    private lateinit var adapter: CaseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = CaseAdapter(object : OnCaseClickListener {
            override fun openCase(case: Case.Data) {
                caseViewModel.getCaseById(case)
                router.navigateTo(Screens.detailCaseFragment())
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCasesBinding.inflate(inflater, container, false)

        if (caseViewModel.cases.value?.error == true) {
            caseViewModel.getCases()
        }

        with(binding) {
            rvCases.adapter = adapter

            buttonBack.setOnClickListener {
                router.exit()
            }

            buttonFilter.setOnClickListener {
                when (mainViewModel.networkStatus.value) {
                    ConnectivityObserver.Status.AVAILABLE -> router.navigateTo(Screens.caseFilterFragment())
                    else -> router.navigateTo(Screens.errorInternetFragment())
                }
            }
        }

        caseViewModel.cases.observe(viewLifecycleOwner) { caseModel ->
            val currentList = adapter.currentList.size
            adapter.submitList(caseModel.cases?.data) {
                if (currentList != caseModel.cases?.data?.size) {
                    binding.rvCases.scrollToPosition(0)
                }
            }

            binding.cardEmptyMsg.isVisible = caseModel.cases?.data?.isEmpty() == true

            when (caseModel.load) {
                true -> binding.progressBar.show()
                else -> binding.progressBar.hide()
            }

        }

        caseViewModel.caseFilters.observe(viewLifecycleOwner) { caseFilterModel ->
            binding.buttonFilter.isSelected = caseFilterModel.filters
                .filterIsInstance<CaseFilterName>()
                .firstOrNull { it.selected } != null
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

}