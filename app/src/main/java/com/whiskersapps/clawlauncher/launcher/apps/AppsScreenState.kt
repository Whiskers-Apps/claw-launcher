package com.whiskersapps.clawlauncher.launcher.apps

import com.whiskersapps.clawlauncher.shared.model.App
import com.whiskersapps.clawlauncher.shared.model.Settings
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.DEFAULT_APPS_SEARCH_BAR_POSITION
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.DEFAULT_APPS_SEARCH_BAR_RADIUS
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.DEFAULT_APPS_VIEW_TYPE
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.DEFAULT_DISABLE_APPS_SCREEN
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.DEFAULT_LANDSCAPE_COLS
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.DEFAULT_PORTRAIT_COLS
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.DEFAULT_SHOW_APPS_SEARCH_BAR
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.DEFAULT_SHOW_APPS_SEARCH_BAR_PLACEHOLDER

data class AppsScreenState(
    // Loading
    val loading: Boolean = true,
    val loadingApps: Boolean = true,
    val loadingSettings: Boolean = true,


    val apps: List<App> = emptyList(),
    val securedApps: List<String> = emptyList(),
    val disableAppsScreen: Boolean = DEFAULT_DISABLE_APPS_SCREEN,
    val viewType: String = DEFAULT_APPS_VIEW_TYPE,
    val portraitCols: Int = DEFAULT_PORTRAIT_COLS,
    val landscapeCols: Int = DEFAULT_LANDSCAPE_COLS,
    val showSearchBar: Boolean = DEFAULT_SHOW_APPS_SEARCH_BAR,
    val searchBarPosition: String = DEFAULT_APPS_SEARCH_BAR_POSITION,
    val showSearchBarPlaceholder: Boolean = DEFAULT_SHOW_APPS_SEARCH_BAR_PLACEHOLDER,
    val searchBarRadius: Int = DEFAULT_APPS_SEARCH_BAR_RADIUS,
    val searchText: String = "",
    val showSettingsDialog: Boolean = false,
    val showAppMenu: Boolean = false,
    val splitList: Boolean = Settings.DEFAULT_SPLIT_LIST_VIEW
)
