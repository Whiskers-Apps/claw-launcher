package com.whiskersapps.clawlauncher.shared.model

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

data class Settings(
    val setupCompleted: Boolean = DEFAULT_SETUP_COMPLETED,
    val layout: String = DEFAULT_KEY_LAYOUT,
    val iconPadding: Int = DEFAULT_ICON_PADDING,
    val appsViewType: String = DEFAULT_APPS_VIEW_TYPE,
    val appsOpacity: Float = DEFAULT_APPS_OPACITY,
    val phoneCols: Int = DEFAULT_PHONE_COLS,
    val phoneLandscapeCols: Int = DEFAULT_PHONE_LANDSCAPE_COLS,
    val tabletCols: Int = DEFAULT_TABLET_COLS,
    val tabletLandscapeCols: Int = DEFAULT_TABLET_LANDSCAPE_COLS,
    val showHomeSearchBar: Boolean = DEFAULT_SHOW_HOME_SEARCH_BAR,
    val showHomeSearchBarPlaceholder: Boolean = DEFAULT_SHOW_HOME_SEARCH_BAR_PLACEHOLDER,
    val showHomeSearchBarSettings: Boolean = DEFAULT_SHOW_HOME_SEARCH_BAR_SETTINGS,
    val homeSearchBarRadius: Int = DEFAULT_HOME_SEARCH_BAR_RADIUS,
    val homeSearchBarOpacity: Float = DEFAULT_HOME_SEARCH_BAR_OPACITY,
    val showAppsSearchBar: Boolean = DEFAULT_SHOW_APPS_SEARCH_BAR,
    val appsSearchBarPosition: String = DEFAULT_APPS_SEARCH_BAR_POSITION,
    val appsSearchBarOpacity: Float = DEFAULT_APPS_SEARCH_BAR_OPACITY,
    val defaultSearchEngine: String = DEFAULT_DEFAULT_SEARCH_ENGINE
){
    companion object{
        val KEY_SETUP_COMPLETED = booleanPreferencesKey("setup-completed")
        const val DEFAULT_SETUP_COMPLETED = false

        val KEY_LAYOUT = stringPreferencesKey("layout")
        const val DEFAULT_KEY_LAYOUT = "minimal"

        val KEY_ICON_PADDING = intPreferencesKey("icon-padding")
        const val DEFAULT_ICON_PADDING = 16

        val APPS_VIEW_TYPE = stringPreferencesKey("apps-view-type")
        const val DEFAULT_APPS_VIEW_TYPE = "grid"

        val APPS_OPACITY = floatPreferencesKey("apps-opacity")
        const val DEFAULT_APPS_OPACITY = 1f

        val PHONE_COLS = intPreferencesKey("phone-cols")
        const val DEFAULT_PHONE_COLS = 4

        val PHONE_LANDSCAPE_COLS = intPreferencesKey("phone-landscape-cols")
        const val DEFAULT_PHONE_LANDSCAPE_COLS = 6

        val TABLET_COLS = intPreferencesKey("tablet-cols")
        const val DEFAULT_TABLET_COLS = 6

        val TABLET_LANDSCAPE_COLS = intPreferencesKey("tablet-landscape-cols")
        const val DEFAULT_TABLET_LANDSCAPE_COLS = 6

        val SHOW_HOME_SEARCH_BAR = booleanPreferencesKey("show-home-search-bar")
        const val DEFAULT_SHOW_HOME_SEARCH_BAR = true

        val SHOW_HOME_SEARCH_BAR_PLACEHOLDER = booleanPreferencesKey("show-home-search-bar-placeholder")
        const val DEFAULT_SHOW_HOME_SEARCH_BAR_PLACEHOLDER = true

        val SHOW_HOME_SEARCH_BAR_SETTINGS = booleanPreferencesKey("show-home-search-bar-settings")
        const val DEFAULT_SHOW_HOME_SEARCH_BAR_SETTINGS = true

        val HOME_SEARCH_BAR_RADIUS = intPreferencesKey("home-search-bar-radius")
        const val DEFAULT_HOME_SEARCH_BAR_RADIUS = -1

        val HOME_SEARCH_BAR_OPACITY = floatPreferencesKey("home-search-bar-opacity")
        const val DEFAULT_HOME_SEARCH_BAR_OPACITY = 1f

        val SHOW_APPS_SEARCH_BAR = booleanPreferencesKey("show-apps-search-bar")
        const val DEFAULT_SHOW_APPS_SEARCH_BAR = true

        val APPS_SEARCH_BAR_POSITION = stringPreferencesKey("apps-search-bar-position")
        const val DEFAULT_APPS_SEARCH_BAR_POSITION = "bottom"

        val APPS_SEARCH_BAR_OPACITY = floatPreferencesKey("apps-search-bar-opacity")
        const val DEFAULT_APPS_SEARCH_BAR_OPACITY = 1f

        val DEFAULT_SEARCH_ENGINE = stringPreferencesKey("default-search-engine")
        const val DEFAULT_DEFAULT_SEARCH_ENGINE = ""
    }
}