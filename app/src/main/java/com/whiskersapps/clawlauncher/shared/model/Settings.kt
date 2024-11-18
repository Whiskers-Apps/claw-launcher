package com.whiskersapps.clawlauncher.shared.model

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.lighttigerxiv.whiskers_palette_kt.WhiskersColor
import com.lighttigerxiv.whiskers_palette_kt.getColor
import com.whiskersapps.clawlauncher.shared.utils.isAtLeastAndroid12

data class Settings(
    /**
     * Check if initial setup was completed
     */
    val setupCompleted: Boolean = DEFAULT_SETUP_COMPLETED,

    /**
     * The view type on the apps screen
     *
     * Possible values: "list", "grid"
     */
    val appsViewType: String = DEFAULT_APPS_VIEW_TYPE,

    /**
     * The amount of app columns to show when the device is in portrait. It affects search results columns too
     */
    val portraitCols: Int = DEFAULT_PORTRAIT_COLS,

    /**
     * The amount of app columns to show when the device is in landscape. It affects search results columns too
     */
    val landscapeCols: Int = DEFAULT_LANDSCAPE_COLS,

    /**
     * The amount of app columns to show when the device is unfolded and in portrait. It affects search results columns too
     */
    val unfoldedPortraitCols: Int = DEFAULT_UNFOLDED_PORTRAIT_COLS,

    /**
     * The amount of app columns to show when the device is unfolded and in landscape. It affects search results columns too
     */
    val unfoldedLandscapeCols: Int = DEFAULT_UNFOLDED_LANDSCAPE_COLS,

    /**
     * Show the home search bar
     */
    val showHomeSearchBar: Boolean = DEFAULT_SHOW_HOME_SEARCH_BAR,

    /**
     * Show the placeholder on the home screen
     */
    val showHomeSearchBarPlaceholder: Boolean = DEFAULT_SHOW_HOME_SEARCH_BAR_PLACEHOLDER,

    /**
     * Show the settings icon on the home search bar
     */
    val showHomeSearchBarSettings: Boolean = DEFAULT_SHOW_HOME_SEARCH_BAR_SETTINGS,

    /**
     * The search bar radius on the home screen
     */
    val homeSearchBarRadius: Int = DEFAULT_HOME_SEARCH_BAR_RADIUS,

    /**
     * Show the search bar on the apps screen
     */
    val showAppsSearchBar: Boolean = DEFAULT_SHOW_APPS_SEARCH_BAR,

    /**
     * Show the search placeholder on the apps search bar
     */
    val showAppsSearchBarPlaceholder: Boolean = DEFAULT_SHOW_APPS_SEARCH_BAR_PLACEHOLDER,

    /**
     * Show the settings icon on the search bar in the apps screen
     */
    val showAppsSearchBarSettings: Boolean = DEFAULT_SHOW_APPS_SEARCH_BAR_SETTINGS,

    /**
     * The search bar position in the apps screen
     *
     * Possible values: "top", "bottom"
     */
    val appsSearchBarPosition: String = DEFAULT_APPS_SEARCH_BAR_POSITION,

    /**
     * The search bar radius on the apps screen
     */
    val appsSearchBarRadius: Int = DEFAULT_APPS_SEARCH_BAR_RADIUS,

    /**
     * The default search engine to be used when searching the web
     */
    val defaultSearchEngine: String = DEFAULT_DEFAULT_SEARCH_ENGINE,

    /**
     * The selected dark mode. It will override the device mode in case it's not system
     *
     * Possible values: "system", "light", "dark"
     */
    val darkMode: String = DEFAULT_DARK_MODE,

    /**
     * The selected light theme.
     *
     * Possible values: "monet", "tiger-{accent}""
     */
    val theme: String = DEFAULT_THEME,

    /**
     * The selected dark theme.
     *
     * Possible values: "monet", "panther-{accent}""
     */
    val darkTheme: String = DEFAULT_DARK_THEME,

    /**
     * A list of apps package names that will be hidden on search and apps screen
     */
    val hiddenApps: List<String> = emptyList(),

    /**
     * A list of apps package names that are required to open with fingerprint
     */
    val secureApps: List<String> = emptyList(),

    /**
     * Enable swipe to search on the home screen
     */
    val swipeUpToSearch: Boolean = DEFAULT_SWIPE_UP_TO_SEARCH,

    /**
     * Disable the all apps screen
     */
    val disableAppsScreen: Boolean = DEFAULT_DISABLE_APPS_SCREEN,

    /**
     * Tint the home screen clock with accent color
     */
    val tintClock: Boolean = DEFAULT_TINT_CLOCK,

    /**
     * Split the apps list view in two on bigger screens
     */
    val splitListView: Boolean = DEFAULT_SPLIT_LIST_VIEW,

    /**
     * The clock placement in the home screen.
     *
     * Possible values: "top", "center", "bottom"
     */
    val clockPlacement: String = DEFAULT_CLOCK_PLACEMENT
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

        val CLOCK_PLACEMENT = stringPreferencesKey("clock-placement")
        const val DEFAULT_CLOCK_PLACEMENT = "top"
    }
}