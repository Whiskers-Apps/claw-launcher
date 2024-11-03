package com.whiskersapps.clawlauncher.views.main.model

import com.whiskersapps.clawlauncher.shared.model.Settings

data class MainScreenState(
    val loading: Boolean = true,
    val settings: Settings = Settings()
)
