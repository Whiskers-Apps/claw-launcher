package com.whiskersapps.clawlauncher.views.main.views.settings.views.apps.intent

sealed class AppsSettingsScreenAction {
    data object NavigateBack: AppsSettingsScreenAction()
    data class SetViewType(val type: String): AppsSettingsScreenAction()
    data class SetPhoneCols(val cols: Float) : AppsSettingsScreenAction()
    data class SetPhoneLandscapeCols(val cols: Float) : AppsSettingsScreenAction()
    data class SetTabletCols(val cols: Float) : AppsSettingsScreenAction()
    data class SetTabletLandscapeCols(val cols: Float) : AppsSettingsScreenAction()
    data class SetSearchBarPosition(val position: String) : AppsSettingsScreenAction()
    data class SetShowSearchBar(val show: Boolean) : AppsSettingsScreenAction()
    data class SetShowSearchBarPlaceholder(val show: Boolean) : AppsSettingsScreenAction()
    data class SetShowSearchBarSettings(val show: Boolean) : AppsSettingsScreenAction()
    data class SetSearchBarRadius(val radius: Float): AppsSettingsScreenAction()
    data class SetDisableAppsScreen(val disable: Boolean) : AppsSettingsScreenAction()
}