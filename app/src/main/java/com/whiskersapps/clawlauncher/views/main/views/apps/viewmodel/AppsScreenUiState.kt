package com.whiskersapps.clawlauncher.views.main.views.apps.viewmodel

import androidx.compose.ui.unit.Dp
import com.whiskersapps.clawlauncher.shared.model.AppShortcut

data class AppsScreenUiState(
    val appShortcuts: List<AppShortcut>,
    val layout: String,
    val viewType: String,
    val phoneCols: Int,
    val phoneRows: Int,
    val phoneLandscapeCols: Int,
    val phoneLandscapeRows: Int,
    val tabletCols: Int,
    val tabletRows: Int,
    val tabletLandscapeCols: Int,
    val tabletLandscapeRows: Int,
    val showSearchBar: Boolean,
    val searchBarPosition: String,
    val blurRadius: Dp,
    val searchText: String
)
