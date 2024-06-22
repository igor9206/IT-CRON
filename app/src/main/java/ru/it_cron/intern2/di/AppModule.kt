package ru.it_cron.intern2.di

import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.it_cron.intern2.util.Constants

val prefsModule = module {
    single {
        androidContext().getSharedPreferences(
            Constants.PREFS_NAME,
            AppCompatActivity.MODE_PRIVATE
        )
    }
}