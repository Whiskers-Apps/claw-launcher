package com.whiskersapps.clawlauncher.settings.style

import androidx.compose.ui.graphics.Color
import com.whiskersapps.clawlauncher.shared.model.App
import com.whiskersapps.clawlauncher.shared.model.Settings
import com.whiskersapps.clawlauncher.shared.utils.isAtLeastAndroid12

data class StyleSettingsScreenState(
    val loading: Boolean = true,
    val loadingSettings: Boolean = true,

    // Settings
    val darkMode: String = Settings.DEFAULT_DARK_MODE,
    val theme: String = Settings.DEFAULT_THEME,
    val darkTheme: String = Settings.DEFAULT_DARK_THEME,
    val iconPack: String = "",
    val iconPacks: List<App> = emptyList(),

    // Dialogs
    val showIconPackDialog: Boolean = false,


    val showDarkModeDialog: Boolean = false,
    val showThemeDialog: Boolean = false,
    val showDarkThemeDialog: Boolean = false,
    val customBackgroundColor: Color = Color(0),
    val customSecondaryBackgroundColor: Color = Color(0),
    val customTextColor: Color = Color(0),
    val customAccentColor: Color = Color(0),
    val themeDialogTabIndex: Int = 0,
    val isAtLeastAndroid12: Boolean = isAtLeastAndroid12()
)