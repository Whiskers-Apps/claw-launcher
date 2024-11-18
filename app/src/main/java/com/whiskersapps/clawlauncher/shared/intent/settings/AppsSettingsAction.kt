package com.whiskersapps.clawlauncher.shared.intent.settings

sealed class AppsSettingsAction {
    data class SetViewType(val type: String): AppsSettingsAction()
    data class SetPhoneCols(val cols: Float) : AppsSettingsAction()
    data class SetPhoneLandscapeCols(val cols: Float) : AppsSettingsAction()
    data class SetUnfoldedCols(val cols: Float) : AppsSettingsAction()
    data class SetUnfoldedLandscapeCols(val cols: Float) : AppsSettingsAction()
    data class SetSearchBarPosition(val position: String) : AppsSettingsAction()
    data class SetShowSearchBar(val show: Boolean) : AppsSettingsAction()
    data class SetShowSearchBarPlaceholder(val show: Boolean) : AppsSettingsAction()
    data class SetShowSearchBarSettings(val show: Boolean) : AppsSettingsAction()
    data class SetSearchBarRadius(val radius: Float): AppsSettingsAction()
    data class SetDisableAppsScreen(val disable: Boolean) : AppsSettingsAction()
    data class SetSplitList(val split: Boolean) : AppsSettingsAction()
}