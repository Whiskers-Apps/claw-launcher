package com.whiskersapps.clawlauncher.views.main.views.settings.view.views.style.model

import com.whiskersapps.clawlauncher.shared.model.Settings

data class StyleSettingsScreenState(
    val loading: Boolean = true,
    val settings: Settings = Settings()
)