package com.whiskersapps.clawlauncher.settings.style


sealed class StyleSettingsScreenAction {
    data object NavigateBack : StyleSettingsScreenAction()
    data object OpenDarkModeDialog : StyleSettingsScreenAction()
    data object CloseDarkModeDialog : StyleSettingsScreenAction()
    data class SetDarkMode(val darkMode: String) : StyleSettingsScreenAction()
    data object OpenThemeDialog : StyleSettingsScreenAction()
    data object CloseThemeDialog : StyleSettingsScreenAction()
    data object OpenDarkThemeDialog : StyleSettingsScreenAction()
    data object CloseDarkThemeDialog : StyleSettingsScreenAction()
    data class SelectThemeDialogTab(val index: Int) : StyleSettingsScreenAction()
    data class SetTheme(val themeId: String) : StyleSettingsScreenAction()
    data class SetDarkTheme(val themeId: String) : StyleSettingsScreenAction()
}