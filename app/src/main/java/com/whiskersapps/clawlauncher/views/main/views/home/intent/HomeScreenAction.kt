package com.whiskersapps.clawlauncher.views.main.views.home.intent

sealed class HomeScreenAction{
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
    data class SetSearchBarOpacity(val opacity: Float): HomeScreenAction()
    data class SetSearchBarRadius(val radius: Float): HomeScreenAction()

}