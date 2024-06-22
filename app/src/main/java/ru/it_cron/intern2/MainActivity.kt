package ru.it_cron.intern2

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.it_cron.intern2.databinding.ActivityMainBinding
import ru.it_cron.intern2.navigation.Screens
import ru.it_cron.intern2.util.Constants
import ru.it_cron.intern2.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private val navigatorHolder by inject<NavigatorHolder>()
    private val navigator = AppNavigator(this, R.id.mainContainer)
    private val router by inject<Router>()

    private val mainViewModel: MainViewModel by viewModel()

    private val prefs by inject<SharedPreferences>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainContainer)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mainViewModel.networkStatus.observe(this) {}

        router.navigateTo(Screens.splashFragment())

        mainViewModel.menu.observe(this) {
            closeSplashFragment()
        }

    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    private fun closeSplashFragment() {
        when (prefs.getBoolean(Constants.FIRST_START_APP, true)) {
            true -> {
                router.newRootScreen(Screens.onboardingSixFragment())
            }

            else -> {
                router.newRootScreen(Screens.mainFragment())
            }
        }
    }

}