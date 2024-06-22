package ru.it_cron.intern2.navigation

sealed class NavigationCommand {
    data object ToErrorNetworkFragment : NavigationCommand()
    data object RequestSuccess : NavigationCommand()
    data object RequestFailure : NavigationCommand()
}