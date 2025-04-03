package com.whiskersapps.clawlauncher.settings.apps

sealed interface AppsSettingsScreenAction {
    data object NavigateBack : AppsSettingsScreenAction
    data class SetViewType(val type: String) : AppsSettingsScreenAction
    data class SetPortraitColumns(val cols: Float) : AppsSettingsScreenAction
    data class SetLandscapeColumns(val cols: Float) : AppsSettingsScreenAction
    data class SetUnfoldedPortraitColumns(val cols: Float) : AppsSettingsScreenAction
    data class SetUnfoldedLandscapeColumns(val cols: Float) : AppsSettingsScreenAction
    data class SetSearchBarPosition(val position: String) : AppsSettingsScreenAction
    data class SetShowSearchBar(val show: Boolean) : AppsSettingsScreenAction
    data class SetShowSearchBarPlaceholder(val show: Boolean) : AppsSettingsScreenAction
    data class SetSearchBarRadius(val radius: Float) : AppsSettingsScreenAction
    data class SetDisableAppsScreen(val disable: Boolean) : AppsSettingsScreenAction
    data class SetSplitList(val split: Boolean) : AppsSettingsScreenAction
}