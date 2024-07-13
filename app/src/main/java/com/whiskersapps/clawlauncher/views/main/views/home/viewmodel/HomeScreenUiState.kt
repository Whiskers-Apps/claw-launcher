package com.whiskersapps.clawlauncher.views.main.views.home.viewmodel

import androidx.compose.ui.unit.Dp

data class HomeScreenUiState(
    val showSearchBar: Boolean,
    val showPlaceholder: Boolean,
    val showSettings: Boolean,
    val searchBarOpacity: Float,
    val searchBarRadius: Dp?,
    val dateText: String,
    val hourText: String,
    val showSettingsDialog: Boolean,
    val showHomeDialog: Boolean
)
