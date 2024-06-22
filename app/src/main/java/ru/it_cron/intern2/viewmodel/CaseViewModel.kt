package ru.it_cron.intern2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.it_cron.intern2.dto.Case
import ru.it_cron.intern2.model.CaseFilterCategory
import ru.it_cron.intern2.model.CaseFilterModel
import ru.it_cron.intern2.model.CaseFilterName
import ru.it_cron.intern2.model.CaseModel
import ru.it_cron.intern2.model.DetailCaseModel
import ru.it_cron.intern2.navigation.NavigationCommand
import ru.it_cron.intern2.repository.Repository
import ru.it_cron.intern2.util.SingleLiveEvent

class CaseViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _navigationEvent = SingleLiveEvent<NavigationCommand>()
    val navigationEvent: LiveData<NavigationCommand> = _navigationEvent

    private val _cases = MutableLiveData<CaseModel>()
    val cases: LiveData<CaseModel>
        get() = getSelectedCaseFilters().let { filters ->
            _cases.map { caseModel ->
                if (filters.isEmpty()) {
                    caseModel
                } else {
                    caseModel.copy(
                        cases = caseModel.cases?.copy(
                            data = caseModel.cases.data.filter { dataCase ->
                                filters.any { dataCase.filters.contains(it) }
                            }
                        )
                    )
                }
            }
        }

    private val _caseFilters = MutableLiveData<CaseFilterModel>()
    val caseFilters: LiveData<CaseFilterModel> = _caseFilters

    private val _detailCase = MutableLiveData<DetailCaseModel>()
    val detailCase: LiveData<DetailCaseModel> = _detailCase

    init {
        getCases()
    }

    fun getCases() = viewModelScope.launch {
        _cases.value = CaseModel(load = true)
        repository.getCases()
            .onSuccess {
                delay(250)
                _cases.value = CaseModel(cases = it)
            }
            .onFailure {
                delay(250)
                _cases.value = CaseModel(error = true)
                _navigationEvent.value = NavigationCommand.ToErrorNetworkFragment
            }
    }

    fun getCaseFilters() = viewModelScope.launch {
        _caseFilters.value = CaseFilterModel(load = true)
        repository.getCaseFilters()
            .onSuccess { caseFilter ->
                delay(250)
                val list = caseFilter.data.flatMap { category ->
                    listOf(CaseFilterCategory(category.id, category.name)) +
                            category.filters.map { name ->
                                CaseFilterName(name.id, name.name)
                            }

                }
                _caseFilters.value = CaseFilterModel(filters = list)
            }
            .onFailure {
                delay(250)
                _caseFilters.value = CaseFilterModel(error = true)
                _navigationEvent.value = NavigationCommand.ToErrorNetworkFragment
            }
    }

    fun setFilter(caseFilterName: CaseFilterName) {
        _caseFilters.value = _caseFilters.value?.let { caseFilterModel ->
            caseFilterModel.copy(
                filters = caseFilterModel.filters.map { filter ->
                    when (filter) {
                        is CaseFilterName -> {
                            if (filter.id == caseFilterName.id) {
                                filter.copy(selected = !filter.selected)
                            } else filter
                        }

                        else -> filter
                    }
                }
            )
        }
    }

    fun cleanFilter() {
        _caseFilters.value = _caseFilters.value?.let { caseFilterModel ->
            caseFilterModel.copy(
                filters = caseFilterModel.filters.map { filter ->
                    when (filter) {
                        is CaseFilterName -> filter.copy(selected = false)
                        else -> filter
                    }
                }
            )
        }
    }

    private fun getSelectedCaseFilters(): List<Case.Data.Filter> =
        _caseFilters.value?.let { caseFilterModel ->
            caseFilterModel.filters
                .filterIsInstance<CaseFilterName>()
                .filter { it.selected }
                .map { Case.Data.Filter(it.id, it.name) }
        } ?: emptyList()


    fun getCaseById(case: Case.Data) = viewModelScope.launch {
        val nextCase = _cases.value?.cases?.data?.let { list ->
            list.indexOf(case).let {
                list.getOrNull(it + 1)
            }
        }
        _detailCase.value = DetailCaseModel(image = case.image, nextCase = nextCase, load = true)
        repository.getCaseById(case.id)
            .onSuccess {
                delay(250)
                _detailCase.value = _detailCase.value!!.copy(case = it, load = false)
            }
            .onFailure {
                delay(250)
                _detailCase.value = _detailCase.value!!.copy(error = true, load = false)
                _navigationEvent.value = NavigationCommand.ToErrorNetworkFragment
            }
    }

}