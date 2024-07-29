package com.whiskersapps.clawlauncher.shared.model

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

data class Settings(
    val setupCompleted: Boolean = DEFAULT_SETUP_COMPLETED,
    val iconPadding: Int = DEFAULT_ICON_PADDING,
    val appsViewType: String = DEFAULT_APPS_VIEW_TYPE,
    val appsOpacity: Float = DEFAULT_APPS_OPACITY,
    val portraitCols: Int = DEFAULT_PORTRAIT_COLS,
    val landscapeCols: Int = DEFAULT_LANDSCAPE_COLS,
    val unfoldedPortraitCols: Int = DEFAULT_UNFOLDED_PORTRAIT_COLS,
    val unfoldedLandscapeCols: Int = DEFAULT_UNFOLDED_LANDSCAPE_COLS,
    val showHomeSearchBar: Boolean = DEFAULT_SHOW_HOME_SEARCH_BAR,
    val showHomeSearchBarPlaceholder: Boolean = DEFAULT_SHOW_HOME_SEARCH_BAR_PLACEHOLDER,
    val showHomeSearchBarSettings: Boolean = DEFAULT_SHOW_HOME_SEARCH_BAR_SETTINGS,
    val homeSearchBarRadius: Int = DEFAULT_HOME_SEARCH_BAR_RADIUS,
    val homeSearchBarOpacity: Float = DEFAULT_HOME_SEARCH_BAR_OPACITY,
    val showAppsSearchBar: Boolean = DEFAULT_SHOW_APPS_SEARCH_BAR,
    val showAppsSearchBarPlaceholder: Boolean = DEFAULT_SHOW_APPS_SEARCH_BAR_PLACEHOLDER,
    val showAppsSearchBarSettings: Boolean = DEFAULT_SHOW_APPS_SEARCH_BAR_SETTINGS,
    val appsSearchBarPosition: String = DEFAULT_APPS_SEARCH_BAR_POSITION,
    val appsSearchBarOpacity: Float = DEFAULT_APPS_SEARCH_BAR_OPACITY,
    val appsSearchBarRadius: Int = DEFAULT_APPS_SEARCH_BAR_RADIUS,
    val defaultSearchEngine: String = DEFAULT_DEFAULT_SEARCH_ENGINE,
    val iconPack: String = DEFAULT_ICON_PACK,
    val darkMode: String = DEFAULT_DARK_MODE
){
    companion object{
        val SETUP_COMPLETED = booleanPreferencesKey("setup-completed")
        const val DEFAULT_SETUP_COMPLETED = false

        val ICON_PADDING = intPreferencesKey("icon-padding")
        const val DEFAULT_ICON_PADDING = 16

        val APPS_VIEW_TYPE = stringPreferencesKey("apps-view-type")
        const val DEFAULT_APPS_VIEW_TYPE = "grid"

        val APPS_OPACITY = floatPreferencesKey("apps-opacity")
        const val DEFAULT_APPS_OPACITY = 1f

        val PORTRAIT_COLS = intPreferencesKey("portrait-cols")
        const val DEFAULT_PORTRAIT_COLS = 4

        val LANDSCAPE_COLS = intPreferencesKey("landscape-cols")
        const val DEFAULT_LANDSCAPE_COLS = 7

        val UNFOLDED_PORTRAIT_COLS = intPreferencesKey("unfolded-portrait-cols")
        const val DEFAULT_UNFOLDED_PORTRAIT_COLS = 7

        val UNFOLDED_LANDSCAPE_COLS = intPreferencesKey("unfolded-landscape-cols")
        const val DEFAULT_UNFOLDED_LANDSCAPE_COLS = 7

        val SHOW_HOME_SEARCH_BAR = booleanPreferencesKey("show-home-search-bar")
        const val DEFAULT_SHOW_HOME_SEARCH_BAR = true

        val SHOW_HOME_SEARCH_BAR_PLACEHOLDER = booleanPreferencesKey("show-home-search-bar-placeholder")
        const val DEFAULT_SHOW_HOME_SEARCH_BAR_PLACEHOLDER = true

        val SHOW_HOME_SEARCH_BAR_SETTINGS = booleanPreferencesKey("show-home-search-bar-settings")
        const val DEFAULT_SHOW_HOME_SEARCH_BAR_SETTINGS = true

        val HOME_SEARCH_BAR_RADIUS = intPreferencesKey("home-search-bar-radius")
        const val DEFAULT_HOME_SEARCH_BAR_RADIUS = 100

        val HOME_SEARCH_BAR_OPACITY = floatPreferencesKey("home-search-bar-opacity")
        const val DEFAULT_HOME_SEARCH_BAR_OPACITY = 1f

        val SHOW_APPS_SEARCH_BAR = booleanPreferencesKey("show-apps-search-bar")
        const val DEFAULT_SHOW_APPS_SEARCH_BAR = true

        val SHOW_APPS_SEARCH_BAR_PLACEHOLDER = booleanPreferencesKey("show-apps-search-bar-placeholder")
        const val DEFAULT_SHOW_APPS_SEARCH_BAR_PLACEHOLDER = true

        val SHOW_APPS_SEARCH_BAR_SETTINGS = booleanPreferencesKey("show-apps-search-bar-settings")
        const val DEFAULT_SHOW_APPS_SEARCH_BAR_SETTINGS = true

        val APPS_SEARCH_BAR_POSITION = stringPreferencesKey("apps-search-bar-position")
        const val DEFAULT_APPS_SEARCH_BAR_POSITION = "bottom"

        val APPS_SEARCH_BAR_OPACITY = floatPreferencesKey("apps-search-bar-opacity")
        const val DEFAULT_APPS_SEARCH_BAR_OPACITY = 1f

        val APPS_SEARCH_BAR_RADIUS = intPreferencesKey("apps-search-bar-radius")
        const val DEFAULT_APPS_SEARCH_BAR_RADIUS = 100

        val DEFAULT_SEARCH_ENGINE = stringPreferencesKey("default-search-engine")
        const val DEFAULT_DEFAULT_SEARCH_ENGINE = ""

        val ICON_PACK = stringPreferencesKey("icon-pack")
        const val DEFAULT_ICON_PACK = "system"

        val DARK_MODE = stringPreferencesKey("dark-mode")
        const val DEFAULT_DARK_MODE = "system"
    }
}