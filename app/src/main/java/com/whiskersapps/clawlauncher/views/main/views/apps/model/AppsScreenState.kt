package com.whiskersapps.clawlauncher.views.main.views.apps.model

import com.whiskersapps.clawlauncher.shared.data.SettingsRepository
import com.whiskersapps.clawlauncher.shared.data.SettingsRepository.Companion.GridColsCount
import com.whiskersapps.clawlauncher.shared.model.AppShortcut
import com.whiskersapps.clawlauncher.shared.model.Settings

data class AppsScreenState(
    val loading: Boolean = true,
    val loadingApps: Boolean = true,
    val loadingSettings: Boolean = true,
    val appShortcuts: List<AppShortcut> = emptyList(),
    val securedApps: List<String> = emptyList(),
    val disableAppsScreen: Boolean = Settings.DEFAULT_DISABLE_APPS_SCREEN,
    val viewType: String = Settings.DEFAULT_APPS_VIEW_TYPE,
    val cols: Int = Settings.DEFAULT_PORTRAIT_COLS,
    val landscapeCols: Int = Settings.DEFAULT_LANDSCAPE_COLS,
    val unfoldedCols: Int = Settings.DEFAULT_UNFOLDED_PORTRAIT_COLS,
    val unfoldedLandscapeCols: Int = Settings.DEFAULT_UNFOLDED_LANDSCAPE_COLS,
    val showSearchBar: Boolean = Settings.DEFAULT_SHOW_APPS_SEARCH_BAR,
    val searchBarPosition: String = Settings.DEFAULT_APPS_SEARCH_BAR_POSITION,
    val showSearchBarPlaceholder: Boolean = Settings.DEFAULT_SHOW_APPS_SEARCH_BAR_PLACEHOLDER,
    val showSearchBarSettings: Boolean = Settings.DEFAULT_SHOW_APPS_SEARCH_BAR_SETTINGS,
    val searchBarRadius: Int = Settings.DEFAULT_APPS_SEARCH_BAR_RADIUS,
    val searchText: String = "",
    val showSettingsDialog: Boolean = false,
    val showAppMenu: Boolean = false,
    val gridColsCount: GridColsCount = GridColsCount()
)
