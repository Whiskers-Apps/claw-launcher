package com.whiskersapps.clawlauncher.views.main.views.home.model

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.whiskersapps.clawlauncher.shared.model.Settings

data class HomeScreenState(
    val loading: Boolean = true,
    val clock: String = "",
    val date: String = "",
    val settings: Settings = Settings(),
    val showSettingsDialog: Boolean = false,
    val showMenuDialog: Boolean = false
)
