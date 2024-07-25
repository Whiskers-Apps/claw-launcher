package com.whiskersapps.clawlauncher.views.main.views.home.model

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.whiskersapps.clawlauncher.shared.model.Settings

data class HomeScreenUiState(
    val loading: Boolean = true,
    val clock: String = "",
    val date: String = "",
    val showSearchBar: Boolean = Settings.DEFAULT_SHOW_HOME_SEARCH_BAR,
    val showPlaceholder: Boolean = Settings.DEFAULT_SHOW_HOME_SEARCH_BAR_PLACEHOLDER,
    val showSettings: Boolean = Settings.DEFAULT_SHOW_HOME_SEARCH_BAR_SETTINGS,
    val searchBarOpacity: Float = Settings.DEFAULT_HOME_SEARCH_BAR_OPACITY,
    val searchBarRadius: Dp? = Settings.DEFAULT_HOME_SEARCH_BAR_RADIUS.dp,
    val showSettingsDialog: Boolean = false,
    val showMenuDialog: Boolean = false
)
