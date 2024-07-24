package com.whiskersapps.clawlauncher.views.main.views.settings.view.views.home.intent

sealed class HomeSettingsScreenAction {
    data object NavigateBack: HomeSettingsScreenAction()
    data class SetShowSearchBar(val show: Boolean) : HomeSettingsScreenAction()
    data class SetShowSearchBarPlaceholder(val show: Boolean) : HomeSettingsScreenAction()
    data class SetShowSettings(val show: Boolean) : HomeSettingsScreenAction()
    data class SetSearchBarOpacity(val opacity: Float) : HomeSettingsScreenAction()
    data class SetSearchBarRadius(val radius: Float) : HomeSettingsScreenAction()
}