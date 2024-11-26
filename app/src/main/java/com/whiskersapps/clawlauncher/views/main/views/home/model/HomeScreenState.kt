package com.whiskersapps.clawlauncher.views.main.views.home.model

import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.DEFAULT_CLOCK_PLACEMENT
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.DEFAULT_TINT_CLOCK

data class HomeScreenState(
    val loading: Boolean = true,
    val clock: String = "",
    val date: String = "",
    val showSettingsDialog: Boolean = false,
    val showMenuDialog: Boolean = false,
    val swipeUpToSearch: Boolean = false,
    val showSearchBar: Boolean = true,
    val showPlaceholder: Boolean = true,
    val showSearchBarSettings: Boolean = true,
    val searchBarRadius: Float = 50f,
    val tintClock: Boolean = DEFAULT_TINT_CLOCK,
    val clockPlacement: String = DEFAULT_CLOCK_PLACEMENT,
    val openLockSettings: Boolean = false,
    val showLockScreenDialog: Boolean = false
)
