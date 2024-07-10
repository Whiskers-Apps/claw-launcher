package com.whiskersapps.clawlauncher.views.main.views.home.viewmodel

import androidx.compose.ui.unit.Dp

data class HomeScreenUiState(
    val searchBarRadius: Dp,
    val searchBarTransparency: Float,
    val searchBarBlurStrength: Dp,
    val showSearchBar: Boolean,
    val showSearchLabel: Boolean,
    val dateText: String,
    val hourText: String
)
