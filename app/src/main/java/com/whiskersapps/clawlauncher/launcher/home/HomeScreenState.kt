package com.whiskersapps.clawlauncher.launcher.home

import com.whiskersapps.clawlauncher.shared.model.Settings
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.DEFAULT_CLOCK_PLACEMENT
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.DEFAULT_DARK_MODE
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.DEFAULT_HOME_SEARCH_BAR_RADIUS
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.DEFAULT_PILL_SHAPE_CLOCK
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.DEFAULT_SHOW_HOME_SEARCH_BAR
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.DEFAULT_SWIPE_UP_TO_SEARCH
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.DEFAULT_TINT_CLOCK

data class HomeScreenState(
    // Loading
    val loading: Boolean = true,

    // Widgets
    val clock: String = "",
    val date: String = "",

    // Settings
    val clockPlacement: String = DEFAULT_CLOCK_PLACEMENT,
    val enableSwipeUp: Boolean = DEFAULT_SWIPE_UP_TO_SEARCH,
    val showSearchBar: Boolean = DEFAULT_SHOW_HOME_SEARCH_BAR,
    val showPlaceholder: Boolean = DEFAULT_SHOW_HOME_SEARCH_BAR,
    val searchBarRadius: Float = DEFAULT_HOME_SEARCH_BAR_RADIUS.toFloat(),
    val tintClock: Boolean = DEFAULT_TINT_CLOCK,
    val pillShapeClock: Boolean = DEFAULT_PILL_SHAPE_CLOCK,

    // Dialogs
    val showMenuDialog: Boolean = false,
    val showLockScreenDialog: Boolean = false
)