package com.whiskersapps.clawlauncher.views.main.views.home.intent

sealed class HomeScreenAction{
    data object NavigateToSettings: HomeScreenAction()
    data object OpenSearchSheet: HomeScreenAction()
    data object OpenNotificationPanel: HomeScreenAction()
    data object OpenMenuDialog: HomeScreenAction()
    data object CloseMenuDialog: HomeScreenAction()
    data object OpenSettingsDialog: HomeScreenAction()
    data object CloseSettingsDialog: HomeScreenAction()
    data class UpdateShowSearchBar(val show: Boolean): HomeScreenAction()
    data class UpdateShowSearchBarPlaceholder(val show: Boolean): HomeScreenAction()
    data class UpdateShowSettings(val show: Boolean): HomeScreenAction()
    data class UpdateSearchBarOpacity(val opacity: Float): HomeScreenAction()
    data class UpdateSearchBarRadius(val radius: Float): HomeScreenAction()

}