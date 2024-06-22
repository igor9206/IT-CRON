package ru.it_cron.intern2.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.it_cron.intern2.dto.Menu
import ru.it_cron.intern2.repository.Repository
import ru.it_cron.intern2.util.ConnectivityObserver
import ru.it_cron.intern2.util.NetworkConnectivityObserver

class MainViewModel(
    private val repository: Repository,
    context: Context
) : ViewModel() {

    private val _menu = MutableLiveData<Result<Menu>>()
    val menu: LiveData<Result<Menu>> = _menu

    val networkStatus: LiveData<ConnectivityObserver.Status> =
        NetworkConnectivityObserver(context)
            .observe()
            .asLiveData(Dispatchers.Default)

    fun getMenu() = viewModelScope.launch {
        delay(1000)
        _menu.value = repository.getMenu()
    }

}