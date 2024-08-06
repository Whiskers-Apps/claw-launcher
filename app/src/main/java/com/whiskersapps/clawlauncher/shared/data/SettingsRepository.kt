package com.whiskersapps.clawlauncher.shared.data

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.whiskersapps.clawlauncher.shared.model.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("settings")

class SettingsRepository(app: Application) {

    private val dataStore = app.dataStore
    var settings = Settings()

    val settingsFlow: Flow<Settings> = dataStore.data.catch {
        Settings()
    }.map { preferences ->
        settings = getSettings(preferences)
        getSettings(preferences)
    }

    private fun getSettings(preferences: Preferences): Settings {
        return Settings(
            setupCompleted = preferences[Settings.SETUP_COMPLETED]
                ?: Settings.DEFAULT_SETUP_COMPLETED,

            appsViewType = preferences[Settings.APPS_VIEW_TYPE] ?: Settings.DEFAULT_APPS_VIEW_TYPE,

            portraitCols = preferences[Settings.PORTRAIT_COLS] ?: Settings.DEFAULT_PORTRAIT_COLS,

            landscapeCols = preferences[Settings.LANDSCAPE_COLS]
                ?: Settings.DEFAULT_LANDSCAPE_COLS,

            unfoldedPortraitCols = preferences[Settings.UNFOLDED_PORTRAIT_COLS] ?: Settings.DEFAULT_UNFOLDED_PORTRAIT_COLS,

            unfoldedLandscapeCols = preferences[Settings.UNFOLDED_LANDSCAPE_COLS]
                ?: Settings.DEFAULT_UNFOLDED_LANDSCAPE_COLS,

            showHomeSearchBar = preferences[Settings.SHOW_HOME_SEARCH_BAR]
                ?: Settings.DEFAULT_SHOW_HOME_SEARCH_BAR,

            showHomeSearchBarPlaceholder = preferences[Settings.SHOW_HOME_SEARCH_BAR_PLACEHOLDER]
                ?: Settings.DEFAULT_SHOW_HOME_SEARCH_BAR_PLACEHOLDER,

            showHomeSearchBarSettings = preferences[Settings.SHOW_HOME_SEARCH_BAR_SETTINGS]
                ?: Settings.DEFAULT_SHOW_HOME_SEARCH_BAR_SETTINGS,

            homeSearchBarRadius = preferences[Settings.HOME_SEARCH_BAR_RADIUS]
                ?: Settings.DEFAULT_HOME_SEARCH_BAR_RADIUS,

            showAppsSearchBar = preferences[Settings.SHOW_APPS_SEARCH_BAR]
                ?: Settings.DEFAULT_SHOW_APPS_SEARCH_BAR,

            appsSearchBarPosition = preferences[Settings.APPS_SEARCH_BAR_POSITION]
                ?: Settings.DEFAULT_APPS_SEARCH_BAR_POSITION,

            showAppsSearchBarPlaceholder = preferences[Settings.SHOW_APPS_SEARCH_BAR_PLACEHOLDER]
                ?: Settings.DEFAULT_SHOW_APPS_SEARCH_BAR_PLACEHOLDER,

            showAppsSearchBarSettings = preferences[Settings.SHOW_APPS_SEARCH_BAR_SETTINGS]
                ?: Settings.DEFAULT_SHOW_APPS_SEARCH_BAR_SETTINGS,

            appsSearchBarRadius = preferences[Settings.APPS_SEARCH_BAR_RADIUS]
                ?: Settings.DEFAULT_APPS_SEARCH_BAR_RADIUS,

            defaultSearchEngine = preferences[Settings.DEFAULT_SEARCH_ENGINE] ?: Settings.DEFAULT_DEFAULT_SEARCH_ENGINE,

            darkMode = preferences[Settings.DARK_MODE] ?: Settings.DEFAULT_DARK_MODE,

            lightTheme = preferences[Settings.LIGHT_THEME] ?: Settings.DEFAULT_LIGHT_THEME,

            darkTheme = preferences[Settings.DARK_THEME] ?: Settings.DEFAULT_DARK_THEME,

            customThemeBackground = preferences[Settings.CUSTOM_THEME_BACKGROUND] ?: Settings.DEFAULT_CUSTOM_THEME_BACKGROUND,

            customThemeSecondaryBackground = preferences[Settings.CUSTOM_THEME_SECONDARY_BACKGROUND] ?: Settings.DEFAULT_CUSTOM_THEME_SECONDARY_BACKGROUND,

            customThemeText = preferences[Settings.CUSTOM_THEME_TEXT] ?: Settings.DEFAULT_CUSTOM_THEME_TEXT
        )
    }

    suspend fun setSetupCompleted(setupCompleted: Boolean) {
        dataStore.edit { it[Settings.SETUP_COMPLETED] = setupCompleted }
    }

    suspend fun setAppsViewType(appsViewType: String) {
        dataStore.edit { it[Settings.APPS_VIEW_TYPE] = appsViewType }
    }

    suspend fun setPortraitCols(phoneCols: Int) {
        dataStore.edit { it[Settings.PORTRAIT_COLS] = phoneCols }
    }

    suspend fun setLandscapeCols(landscapeCols: Int) {
        dataStore.edit { it[Settings.LANDSCAPE_COLS] = landscapeCols }
    }

    suspend fun setUnfoldedCols(tabletCols: Int) {
        dataStore.edit { it[Settings.UNFOLDED_PORTRAIT_COLS] = tabletCols }
    }

    suspend fun setUnfoldedLandscapeCols(landscapeCols: Int) {
        dataStore.edit { it[Settings.UNFOLDED_LANDSCAPE_COLS] = landscapeCols }
    }

    suspend fun setShowHomeSearchBar(showHomeSearchBar: Boolean) {
        dataStore.edit { it[Settings.SHOW_HOME_SEARCH_BAR] = showHomeSearchBar }
    }

    suspend fun setShowHomeSearchBarPlaceholder(showHomeSearchBarPlaceholder: Boolean) {
        dataStore.edit {
            it[Settings.SHOW_HOME_SEARCH_BAR_PLACEHOLDER] = showHomeSearchBarPlaceholder
        }
    }

    suspend fun setShowHomeSearchBarSettings(showHomeSearchBarSettings: Boolean) {
        dataStore.edit { it[Settings.SHOW_HOME_SEARCH_BAR_SETTINGS] = showHomeSearchBarSettings }
    }

    suspend fun setHomeSearchBarRadius(radius: Int) {
        dataStore.edit { it[Settings.HOME_SEARCH_BAR_RADIUS] = radius }
    }

    suspend fun setShowAppsSearchBar(showAppsSearchBar: Boolean) {
        dataStore.edit { it[Settings.SHOW_APPS_SEARCH_BAR] = showAppsSearchBar }
    }

    suspend fun setAppsSearchBarPosition(appsSearchBarPosition: String) {
        dataStore.edit { it[Settings.APPS_SEARCH_BAR_POSITION] = appsSearchBarPosition }
    }

    suspend fun updateDefaultSearchEngine(id: String) {
        dataStore.edit { it[Settings.DEFAULT_SEARCH_ENGINE] = id }
    }

    suspend fun setShowAppsSearchBarPlaceholder(show: Boolean) {
        dataStore.edit { it[Settings.SHOW_APPS_SEARCH_BAR_PLACEHOLDER] = show }
    }

    suspend fun setShowAppsSearchBarSettings(show: Boolean) {
        dataStore.edit { it[Settings.SHOW_APPS_SEARCH_BAR_SETTINGS] = show }
    }

    suspend fun setAppsSearchBarRadius(radius: Int){
        dataStore.edit { it[Settings.APPS_SEARCH_BAR_RADIUS] = radius }
    }

    suspend fun setDarkMode(darkMode: String){
        dataStore.edit { it[Settings.DARK_MODE] = darkMode }
    }

    suspend fun setLightTheme(lightTheme: String){
        dataStore.edit { it[Settings.LIGHT_THEME] = lightTheme }
    }

    suspend fun setDarkTheme(darkTheme: String){
        dataStore.edit { it[Settings.DARK_THEME] = darkTheme }
    }

    suspend fun setCustomThemeBackground(color: String){
        dataStore.edit { it[Settings.CUSTOM_THEME_BACKGROUND] = color }
    }

    suspend fun setCustomThemeSecondaryBackground(color: String){
        dataStore.edit { it[Settings.CUSTOM_THEME_SECONDARY_BACKGROUND] = color }
    }

    suspend fun setCustomThemeText(color: String){
        dataStore.edit { it[Settings.CUSTOM_THEME_TEXT] = color }
    }
}