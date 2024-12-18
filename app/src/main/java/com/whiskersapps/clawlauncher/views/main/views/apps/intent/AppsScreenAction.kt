package com.whiskersapps.clawlauncher.views.main.views.apps.intent

import androidx.fragment.app.FragmentActivity
import com.whiskersapps.clawlauncher.shared.model.AppShortcut

sealed class AppsScreenAction {
    data object NavigateToHome : AppsScreenAction()
    data class SetSearchText(val text: String) : AppsScreenAction()
    data class OpenFirstApp(val fragmentActivity: FragmentActivity) : AppsScreenAction()
    data class OpenApp(val packageName: String, val fragmentActivity: FragmentActivity) :
        AppsScreenAction()

    data class OpenAppInfo(val packageName: String) : AppsScreenAction()
    data class RequestUninstall(val packageName: String) : AppsScreenAction()
    data class OpenShortcut(val packageName: String, val shortcut: AppShortcut.Shortcut) :
        AppsScreenAction()

    data object OpenSettingsDialog : AppsScreenAction()
    data object CloseSettingsDialog : AppsScreenAction()
    data object CloseKeyboard : AppsScreenAction()
    data class SetViewType(val type: String) : AppsScreenAction()
    data class SetCols(val cols: Float) : AppsScreenAction()
    data class SetLandscapeCols(val cols: Float) : AppsScreenAction()
    data class SetUnfoldedCols(val cols: Float) : AppsScreenAction()
    data class SetUnfoldedLandscapeCols(val cols: Float) : AppsScreenAction()
    data class SetSearchBarPosition(val position: String) : AppsScreenAction()
    data class SetShowSearchBar(val show: Boolean) : AppsScreenAction()
    data class SetShowSearchBarPlaceholder(val show: Boolean) : AppsScreenAction()
    data class SetShowSearchBarSettings(val show: Boolean) : AppsScreenAction()
    data class SetSearchBarRadius(val radius: Float) : AppsScreenAction()
    data class SetDisableAppsScreen(val disable: Boolean) : AppsScreenAction()
    data class SetSplitList(val split: Boolean) : AppsScreenAction()
}