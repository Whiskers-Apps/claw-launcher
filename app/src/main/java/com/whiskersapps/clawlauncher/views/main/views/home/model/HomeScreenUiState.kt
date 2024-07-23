package com.whiskersapps.clawlauncher.views.main.views.home.model

import androidx.compose.ui.unit.Dp

data class HomeScreenUiState(
    val clock: String = "",
    val date: String = "",
    val showSearchBar: Boolean,
    val showPlaceholder: Boolean,
    val showSettings: Boolean,
    val searchBarOpacity: Float,
    val searchBarRadius: Dp?,
    val showSettingsDialog: Boolean,
    val showMenuDialog: Boolean
)
