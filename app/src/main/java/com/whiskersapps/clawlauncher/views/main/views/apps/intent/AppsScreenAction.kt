package com.whiskersapps.clawlauncher.views.main.views.apps.intent

sealed class AppsScreenAction {
    data object NavigateToHome : AppsScreenAction()
    data class SetSearchText(val text: String) : AppsScreenAction()
    data object OpenFirstApp : AppsScreenAction()
    data class OpenApp(val packageName: String) : AppsScreenAction()
    data class OpenAppInfo(val packageName: String) : AppsScreenAction()
    data class RequestUninstall(val packageName: String) : AppsScreenAction()
    data object OpenSettingsDialog : AppsScreenAction()
    data object CloseSettingsDialog : AppsScreenAction()
    data object CloseKeyboard : AppsScreenAction()
    data class SetViewType(val type: String): AppsScreenAction()
    data class SetCols(val cols: Float) : AppsScreenAction()
    data class SetLandscapeCols(val cols: Float) : AppsScreenAction()
    data class SetUnfoldedCols(val cols: Float) : AppsScreenAction()
    data class SetUnfoldedLandscapeCols(val cols: Float) : AppsScreenAction()
    data class SetSearchBarPosition(val position: String) : AppsScreenAction()
    data class SetShowSearchBar(val show: Boolean) : AppsScreenAction()
    data class SetShowSearchBarPlaceholder(val show: Boolean) : AppsScreenAction()
    data class SetShowSearchBarSettings(val show: Boolean) : AppsScreenAction()
    data class SetSearchBarRadius(val radius: Float): AppsScreenAction()
}