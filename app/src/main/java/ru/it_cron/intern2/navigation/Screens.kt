package ru.it_cron.intern2.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.it_cron.intern2.fragment.MainFragment
import ru.it_cron.intern2.fragment.application.ApplicationFragment
import ru.it_cron.intern2.fragment.cases.CaseFilterFragment
import ru.it_cron.intern2.fragment.cases.CasesFragment
import ru.it_cron.intern2.fragment.cases.DetailCaseFragment
import ru.it_cron.intern2.fragment.cases.ScreenShotsFragment
import ru.it_cron.intern2.fragment.company.CompanyFragment
import ru.it_cron.intern2.fragment.company.TestimonialFragment
import ru.it_cron.intern2.fragment.contacts.ContactsFragment
import ru.it_cron.intern2.fragment.error.ErrorInternetFragment
import ru.it_cron.intern2.fragment.onboarding.OnboardingFiveFragment
import ru.it_cron.intern2.fragment.onboarding.OnboardingSevenFragment
import ru.it_cron.intern2.fragment.onboarding.OnboardingSixFragment
import ru.it_cron.intern2.fragment.service.ServicesFragment
import ru.it_cron.intern2.fragment.splash.SplashFragment

object Screens {

    fun mainFragment() = FragmentScreen { MainFragment() }
    fun applicationFragment() = FragmentScreen { ApplicationFragment() }

    fun casesFragment() = FragmentScreen { CasesFragment() }
    fun caseFilterFragment() = FragmentScreen { CaseFilterFragment() }
    fun detailCaseFragment() = FragmentScreen { DetailCaseFragment() }
    fun screenShotsFragment() = FragmentScreen { ScreenShotsFragment() }

    fun companyFragment() = FragmentScreen { CompanyFragment() }
    fun serviceFragment() = FragmentScreen { ServicesFragment() }
    fun testimonialFragment() = FragmentScreen { TestimonialFragment() }
    fun contactsFragment() = FragmentScreen { ContactsFragment() }

    fun onboardingFiveFragment() = FragmentScreen { OnboardingFiveFragment() }
    fun onboardingSixFragment() = FragmentScreen { OnboardingSixFragment() }
    fun onboardingSevenFragment() = FragmentScreen { OnboardingSevenFragment() }

    fun splashFragment() = FragmentScreen { SplashFragment() }
    fun errorInternetFragment() = FragmentScreen { ErrorInternetFragment() }
}