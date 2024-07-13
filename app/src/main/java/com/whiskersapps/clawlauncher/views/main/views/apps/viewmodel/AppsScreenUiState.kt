package com.whiskersapps.clawlauncher.views.main.views.apps.viewmodel

import androidx.compose.ui.unit.Dp
import com.whiskersapps.clawlauncher.shared.model.AppShortcut

data class AppsScreenUiState(
    val appShortcuts: List<AppShortcut>,
    val layout: String,
    val iconPadding: Dp,
    val viewType: String,
    val opacity: Float,
    val phoneCols: Int,
    val phoneLandscapeCols: Int,
    val tabletCols: Int,
    val tabletLandscapeCols: Int,
    val showSearchBar: Boolean,
    val searchBarPosition: String,
    val searchBarOpacity: Float,
    val searchText: String,
    val showSettingsDialog: Boolean
)
