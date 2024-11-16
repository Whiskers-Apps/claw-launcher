package com.whiskersapps.clawlauncher.shared.model

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.lighttigerxiv.whiskers_palette_kt.WhiskersColor
import com.lighttigerxiv.whiskers_palette_kt.getColor
import com.whiskersapps.clawlauncher.shared.utils.isAtLeastAndroid12

data class Settings(
    val setupCompleted: Boolean = DEFAULT_SETUP_COMPLETED,
    val appsViewType: String = DEFAULT_APPS_VIEW_TYPE,
    val portraitCols: Int = DEFAULT_PORTRAIT_COLS,
    val landscapeCols: Int = DEFAULT_LANDSCAPE_COLS,
    val unfoldedPortraitCols: Int = DEFAULT_UNFOLDED_PORTRAIT_COLS,
    val unfoldedLandscapeCols: Int = DEFAULT_UNFOLDED_LANDSCAPE_COLS,
    val showHomeSearchBar: Boolean = DEFAULT_SHOW_HOME_SEARCH_BAR,
    val showHomeSearchBarPlaceholder: Boolean = DEFAULT_SHOW_HOME_SEARCH_BAR_PLACEHOLDER,
    val showHomeSearchBarSettings: Boolean = DEFAULT_SHOW_HOME_SEARCH_BAR_SETTINGS,
    val homeSearchBarRadius: Int = DEFAULT_HOME_SEARCH_BAR_RADIUS,
    val showAppsSearchBar: Boolean = DEFAULT_SHOW_APPS_SEARCH_BAR,
    val showAppsSearchBarPlaceholder: Boolean = DEFAULT_SHOW_APPS_SEARCH_BAR_PLACEHOLDER,
    val showAppsSearchBarSettings: Boolean = DEFAULT_SHOW_APPS_SEARCH_BAR_SETTINGS,
    val appsSearchBarPosition: String = DEFAULT_APPS_SEARCH_BAR_POSITION,
    val appsSearchBarRadius: Int = DEFAULT_APPS_SEARCH_BAR_RADIUS,
    val defaultSearchEngine: String = DEFAULT_DEFAULT_SEARCH_ENGINE,
    val darkMode: String = DEFAULT_DARK_MODE,
    val theme: String = DEFAULT_THEME,
    val darkTheme: String = DEFAULT_DARK_THEME,
    val hiddenApps: List<String> = emptyList(),
    val secureApps: List<String> = emptyList(),
    val swipeUpToSearch: Boolean = DEFAULT_SWIPE_UP_TO_SEARCH,
    val disableAppsScreen: Boolean = DEFAULT_DISABLE_APPS_SCREEN,
    val tintClock: Boolean = DEFAULT_TINT_CLOCK,
    val splitListView: Boolean = DEFAULT_SPLIT_LIST_VIEW
) {
    companion object {
        val SETUP_COMPLETED = booleanPreferencesKey("setup-completed")
        const val DEFAULT_SETUP_COMPLETED = false

        val APPS_VIEW_TYPE = stringPreferencesKey("apps-view-type")
        const val DEFAULT_APPS_VIEW_TYPE = "grid"

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

        val SHOW_HOME_SEARCH_BAR_PLACEHOLDER =
            booleanPreferencesKey("show-home-search-bar-placeholder")
        const val DEFAULT_SHOW_HOME_SEARCH_BAR_PLACEHOLDER = true

        val SHOW_HOME_SEARCH_BAR_SETTINGS = booleanPreferencesKey("show-home-search-bar-settings")
        const val DEFAULT_SHOW_HOME_SEARCH_BAR_SETTINGS = true

        val HOME_SEARCH_BAR_RADIUS = intPreferencesKey("home-search-bar-radius")
        const val DEFAULT_HOME_SEARCH_BAR_RADIUS = 50

        val SHOW_APPS_SEARCH_BAR = booleanPreferencesKey("show-apps-search-bar")
        const val DEFAULT_SHOW_APPS_SEARCH_BAR = true

        val SHOW_APPS_SEARCH_BAR_PLACEHOLDER =
            booleanPreferencesKey("show-apps-search-bar-placeholder")
        const val DEFAULT_SHOW_APPS_SEARCH_BAR_PLACEHOLDER = true

        val SHOW_APPS_SEARCH_BAR_SETTINGS = booleanPreferencesKey("show-apps-search-bar-settings")
        const val DEFAULT_SHOW_APPS_SEARCH_BAR_SETTINGS = true

        val APPS_SEARCH_BAR_POSITION = stringPreferencesKey("apps-search-bar-position")
        const val DEFAULT_APPS_SEARCH_BAR_POSITION = "bottom"

        val APPS_SEARCH_BAR_RADIUS = intPreferencesKey("apps-search-bar-radius")
        const val DEFAULT_APPS_SEARCH_BAR_RADIUS = 50

        val DEFAULT_SEARCH_ENGINE = stringPreferencesKey("default-search-engine")
        const val DEFAULT_DEFAULT_SEARCH_ENGINE = ""

        val DARK_MODE = stringPreferencesKey("dark-mode")
        const val DEFAULT_DARK_MODE = "system"

        val THEME = stringPreferencesKey("theme")
        val DEFAULT_THEME = if (isAtLeastAndroid12()) "monet" else "tiger-banana"

        val DARK_THEME = stringPreferencesKey("dark-theme")
        val DEFAULT_DARK_THEME = if(isAtLeastAndroid12()) "monet" else "panther-banana"

        val SWIPE_UP_TO_SEARCH = booleanPreferencesKey("swipe-up-to-search")
        const val DEFAULT_SWIPE_UP_TO_SEARCH = true

        val DISABLE_APPS_SCREEN = booleanPreferencesKey("disable-apps-screen")
        const val DEFAULT_DISABLE_APPS_SCREEN = false

        val TINT_CLOCK = booleanPreferencesKey("tint-clock")
        const val DEFAULT_TINT_CLOCK = false

        val SPLIT_LIST_VIEW = booleanPreferencesKey("split-list-view")
        const val DEFAULT_SPLIT_LIST_VIEW = true
    }
}