package com.whiskersapps.clawlauncher.views.main.views.home.intent

sealed class HomeScreenAction{
    data object ChangeWallpaper: HomeScreenAction()
    data object NavigateToSettings: HomeScreenAction()
    data object OpenSearchSheet: HomeScreenAction()
    data object OpenNotificationPanel: HomeScreenAction()
    data object OpenMenuDialog: HomeScreenAction()
    data object CloseMenuDialog: HomeScreenAction()
    data object OpenSettingsDialog: HomeScreenAction()
    data object CloseSettingsDialog: HomeScreenAction()
    data class SetShowSearchBar(val show: Boolean): HomeScreenAction()
    data class SetShowSearchBarPlaceholder(val show: Boolean): HomeScreenAction()
    data class SetShowSettings(val show: Boolean): HomeScreenAction()
    data class SetSearchBarRadius(val radius: Float): HomeScreenAction()
    data class SaveSearchBarRadius(val radius: Float): HomeScreenAction()
    data class SetSwipeUpToSearch(val swipeUp: Boolean): HomeScreenAction()
    data object OnOpenCalendar: HomeScreenAction()
    data class SetTintIcon(val tint: Boolean): HomeScreenAction()
    data object OnLockScreen: HomeScreenAction()
    data object OnCloseLockAccessibilityDialog: HomeScreenAction()
    data object OnOpenAccessibilitySettings: HomeScreenAction()
}