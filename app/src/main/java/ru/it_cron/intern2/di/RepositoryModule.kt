package ru.it_cron.intern2.di

import org.koin.dsl.module
import ru.it_cron.intern2.repository.Repository
import ru.it_cron.intern2.repository.RepositoryImpl

val repositoryModule = module { single<Repository> { RepositoryImpl(get()) } }