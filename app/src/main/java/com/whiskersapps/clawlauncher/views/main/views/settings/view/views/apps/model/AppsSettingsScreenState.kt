package com.whiskersapps.clawlauncher.views.main.views.settings.view.views.apps.model

import com.whiskersapps.clawlauncher.shared.model.Settings

data class AppsSettingsScreenState(
    val loading: Boolean = true,
    val settings: Settings = Settings()
)