package com.whiskersapps.clawlauncher.settings.style


sealed interface StyleSettingsScreenIntent {
    data object BackClicked : StyleSettingsScreenIntent

    data object IconPackClicked : StyleSettingsScreenIntent
    data object IconPackDialogClosed : StyleSettingsScreenIntent
    data class IconPackSelected(val iconPack: String) : StyleSettingsScreenIntent

    data object OpenDarkModeDialog : StyleSettingsScreenIntent
    data object CloseDarkModeDialog : StyleSettingsScreenIntent
    data class SetDarkMode(val darkMode: String) : StyleSettingsScreenIntent
    data object OpenThemeDialog : StyleSettingsScreenIntent
    data object CloseThemeDialog : StyleSettingsScreenIntent
    data object OpenDarkThemeDialog : StyleSettingsScreenIntent
    data object CloseDarkThemeDialog : StyleSettingsScreenIntent
    data class SelectThemeDialogTab(val index: Int) : StyleSettingsScreenIntent
    data class SetTheme(val themeId: String) : StyleSettingsScreenIntent
    data class SetDarkTheme(val themeId: String) : StyleSettingsScreenIntent
}