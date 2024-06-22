package ru.it_cron.intern2.application

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.it_cron.intern2.di.apiModule
import ru.it_cron.intern2.di.navigationModule
import ru.it_cron.intern2.di.prefsModule
import ru.it_cron.intern2.di.repositoryModule
import ru.it_cron.intern2.di.viewModelModule

class ItCronApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ItCronApplication)
            modules(
                listOf(
                    navigationModule,
                    apiModule,
                    viewModelModule,
                    repositoryModule,
                    prefsModule,
                )
            )
        }

    }

}