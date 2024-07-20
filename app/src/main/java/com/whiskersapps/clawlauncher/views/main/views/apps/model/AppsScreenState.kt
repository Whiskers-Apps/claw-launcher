package com.whiskersapps.clawlauncher.views.main.views.apps.model

import androidx.compose.ui.unit.Dp
import com.whiskersapps.clawlauncher.shared.model.AppShortcut

data class AppsScreenState(
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
    val showSearchBarPlaceholder: Boolean,
    val showSearchBarSettings: Boolean,
    val searchBarOpacity: Float,
    val searchBarRadius: Dp?,
    val searchText: String,
    val showSettingsDialog: Boolean
)
