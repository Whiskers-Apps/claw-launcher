package com.whiskersapps.clawlauncher.views.main.views.settings.views.home.model

import com.whiskersapps.clawlauncher.shared.model.Settings

data class HomeSettingsScreenState(
    // Loading
    val loading: Boolean = true,

    // Settings
    val tintClock: Boolean = Settings.DEFAULT_TINT_CLOCK,
    val clockPlacement: String = Settings.DEFAULT_CLOCK_PLACEMENT,
    val swipeUpToSearch: Boolean = Settings.DEFAULT_SWIPE_UP_TO_SEARCH,
    val showSearchBar: Boolean = Settings.DEFAULT_SHOW_HOME_SEARCH_BAR,
    val showPlaceholder: Boolean = Settings.DEFAULT_SHOW_HOME_SEARCH_BAR_PLACEHOLDER,
    val searchBarRadius: Float = Settings.DEFAULT_APPS_SEARCH_BAR_RADIUS.toFloat(),
    val pillShapeClock: Boolean = Settings.DEFAULT_PILL_SHAPE_CLOCK
)
