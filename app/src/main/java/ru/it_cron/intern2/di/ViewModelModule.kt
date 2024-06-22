package ru.it_cron.intern2.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.it_cron.intern2.repository.Repository
import ru.it_cron.intern2.viewmodel.ApplicationViewModel
import ru.it_cron.intern2.viewmodel.CaseViewModel
import ru.it_cron.intern2.viewmodel.CompanyViewModel
import ru.it_cron.intern2.viewmodel.MainViewModel

val viewModelModule = module {
    viewModel { MainViewModel(get<Repository>(), androidContext()) }
    viewModel { CaseViewModel(get<Repository>()) }
    viewModel { CompanyViewModel(get<Repository>()) }
    viewModel { ApplicationViewModel(get<Repository>()) }
}