package com.whiskersapps.clawlauncher.views.main.views.settings.view.views.style.intent

sealed class StyleSettingsScreenAction {
    data object NavigateBack : StyleSettingsScreenAction()
    data object OpenDarkModeDialog : StyleSettingsScreenAction()
    data object CloseDarkModeDialog : StyleSettingsScreenAction()
    data class SetDarkMode(val darkMode: String) : StyleSettingsScreenAction()
}