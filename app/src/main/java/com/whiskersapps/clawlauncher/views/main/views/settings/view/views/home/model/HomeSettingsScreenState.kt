package com.whiskersapps.clawlauncher.views.main.views.settings.view.views.home.model

import com.whiskersapps.clawlauncher.shared.model.Settings

data class HomeSettingsScreenState(
    val loading: Boolean = true,
    val settings: Settings = Settings()
)
