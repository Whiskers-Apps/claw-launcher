package com.whiskersapps.clawlauncher.launcher.home

sealed interface HomeScreenAction {
    data object OnChangeWallpaper : HomeScreenAction

    data object OnOpenSettings : HomeScreenAction

    data object OpenSearchSheet : HomeScreenAction

    data object OpenNotificationPanel : HomeScreenAction

    data object OpenMenuDialog : HomeScreenAction

    data object CloseMenuDialog : HomeScreenAction

    data object OnOpenCalendar : HomeScreenAction

    data object OnLockScreen : HomeScreenAction

    data object OnOpenLockSettings : HomeScreenAction

    data object ResetOpenLockSettings : HomeScreenAction

    data object ShowLockScreenDialog : HomeScreenAction

    data object CloseLockScreenDialog : HomeScreenAction
}