package com.whiskersapps.clawlauncher.views.main.views.settings.views.home.intent

sealed interface HomeSettingsScreenAction {
    data object NavigateBack : HomeSettingsScreenAction
    data class SetShowSearchBar(val show: Boolean) : HomeSettingsScreenAction
    data class SetShowSearchBarPlaceholder(val show: Boolean) : HomeSettingsScreenAction
    data class SetSearchBarRadius(val radius: Float) : HomeSettingsScreenAction
    data class SaveSearchBarRadius(val radius: Float) : HomeSettingsScreenAction
    data class SetSwipeUpToSearch(val swipeUp: Boolean) : HomeSettingsScreenAction
    data class SetTintIcon(val tint: Boolean) : HomeSettingsScreenAction
    data class SetClockPlacement(val placement: String) : HomeSettingsScreenAction
    data class SetPillShapeClock(val pill: Boolean) : HomeSettingsScreenAction
}