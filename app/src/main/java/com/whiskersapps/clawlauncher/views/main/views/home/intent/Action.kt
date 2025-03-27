package com.whiskersapps.clawlauncher.views.main.views.home.intent

sealed interface Action {
    data object OnChangeWallpaper : Action

    data object OnOpenSettings : Action

    data object OpenSearchSheet : Action

    data object OpenNotificationPanel : Action

    data object OpenMenuDialog : Action

    data object CloseMenuDialog : Action

    data object OnOpenCalendar : Action

    data object OnLockScreen : Action

    data object OnOpenLockSettings : Action

    data object ResetOpenLockSettings : Action

    data object ShowLockScreenDialog : Action

    data object CloseLockScreenDialog : Action
}