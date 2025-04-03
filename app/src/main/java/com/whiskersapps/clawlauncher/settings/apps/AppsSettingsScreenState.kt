package com.whiskersapps.clawlauncher.settings.apps

import com.whiskersapps.clawlauncher.shared.model.Settings

data class AppsSettingsScreenState(
    val loading: Boolean = true,

    val disableAppsScreen: Boolean = Settings.DEFAULT_DISABLE_APPS_SCREEN,
    val viewType: String = Settings.DEFAULT_APPS_VIEW_TYPE,

    // Grid View
    val portraitColumns: Float = Settings.DEFAULT_PORTRAIT_COLS.toFloat(),
    val landscapeColumns: Float = Settings.DEFAULT_LANDSCAPE_COLS.toFloat(),

    val isFoldable: Boolean = false,
    val unfoldedPortraitColumns: Float = Settings.DEFAULT_UNFOLDED_PORTRAIT_COLS.toFloat(),
    val unfoldedLandscapeColumns: Float = Settings.DEFAULT_UNFOLDED_LANDSCAPE_COLS.toFloat(),

    // List View
    val splitList: Boolean = Settings.DEFAULT_SPLIT_LIST_VIEW,

    // Search Bar
    val showSearchBar: Boolean = Settings.DEFAULT_SHOW_APPS_SEARCH_BAR,
    val searchBarPosition: String = Settings.DEFAULT_APPS_SEARCH_BAR_POSITION,
    val showSearchBarPlaceholder: Boolean = Settings.DEFAULT_SHOW_APPS_SEARCH_BAR_PLACEHOLDER,
    val searchBarRadius: Float = Settings.DEFAULT_APPS_SEARCH_BAR_RADIUS.toFloat()
)