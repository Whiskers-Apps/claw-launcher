package com.whiskersapps.clawlauncher.views.main.views.home.model

data class HomeScreenState(
    val loading: Boolean = true,
    val clock: String = "",
    val date: String = "",
    val showSettingsDialog: Boolean = false,
    val showMenuDialog: Boolean = false,
    val showSearchBar: Boolean = true,
    val showPlaceholder: Boolean = true,
    val showSearchBarSettings: Boolean = true,
    val searchBarRadius: Float = 50f
)
