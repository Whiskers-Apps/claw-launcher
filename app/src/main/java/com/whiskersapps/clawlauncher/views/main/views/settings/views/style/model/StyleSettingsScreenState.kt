package com.whiskersapps.clawlauncher.views.main.views.settings.views.style.model

import androidx.compose.ui.graphics.Color
import com.whiskersapps.clawlauncher.shared.model.Settings
import com.whiskersapps.clawlauncher.shared.utils.isAtLeastAndroid12

data class StyleSettingsScreenState(
    val loading: Boolean = true,
    val loadingSettings: Boolean = true,
    val settings: Settings = Settings(),
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