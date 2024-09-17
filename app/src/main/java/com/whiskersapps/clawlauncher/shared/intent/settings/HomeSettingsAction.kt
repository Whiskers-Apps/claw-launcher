package com.whiskersapps.clawlauncher.shared.intent.settings

sealed class HomeSettingsAction {
    data class SetSwipeUpToSearch(val swipeUp: Boolean): HomeSettingsAction()
    data class SetShowSearchBar(val show: Boolean) : HomeSettingsAction()
    data class SetShowSearchBarPlaceholder(val show: Boolean) : HomeSettingsAction()
    data class SetShowSettings(val show: Boolean) : HomeSettingsAction()
    data class SetSearchBarRadius(val radius: Float) : HomeSettingsAction()
    data class SaveSearchBarRadius(val radius: Float) : HomeSettingsAction()
}