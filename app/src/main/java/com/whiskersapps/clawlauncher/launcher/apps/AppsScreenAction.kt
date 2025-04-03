package com.whiskersapps.clawlauncher.launcher.apps

import androidx.fragment.app.FragmentActivity
import com.whiskersapps.clawlauncher.shared.model.App

sealed class AppsScreenAction {
    data object NavigateToHome : AppsScreenAction()
    data class SetSearchText(val text: String) : AppsScreenAction()
    data class OpenFirstApp(val fragmentActivity: FragmentActivity) : AppsScreenAction()
    data class OpenApp(val packageName: String, val fragmentActivity: FragmentActivity) :
        AppsScreenAction()

    data class OpenAppInfo(val packageName: String) : AppsScreenAction()
    data class RequestUninstall(val packageName: String) : AppsScreenAction()
    data class OpenShortcut(val packageName: String, val shortcut: App.Shortcut) :
        AppsScreenAction()

    data object OpenSettingsDialog : AppsScreenAction()
    data object CloseSettingsDialog : AppsScreenAction()
    data object CloseKeyboard : AppsScreenAction()
}