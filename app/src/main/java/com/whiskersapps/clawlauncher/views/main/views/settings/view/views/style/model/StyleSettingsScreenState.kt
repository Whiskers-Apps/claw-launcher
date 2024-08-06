package com.whiskersapps.clawlauncher.views.main.views.settings.view.views.style.model

import com.whiskersapps.clawlauncher.shared.model.IconPack
import com.whiskersapps.clawlauncher.shared.model.Settings

data class StyleSettingsScreenState(
    val loading: Boolean = true,
    val loadingSettings: Boolean = true,
    val settings: Settings = Settings(),
    val showDarkModeDialog: Boolean = false
)