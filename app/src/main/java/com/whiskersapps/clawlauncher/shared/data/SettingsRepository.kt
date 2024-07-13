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

    val settingsFlow: Flow<Settings> = dataStore.data.catch {
        Settings()
    }.map { preferences ->
        getSettings(preferences)
    }

    private fun getSettings(preferences: Preferences): Settings {
        return Settings(
            setupCompleted = preferences[Settings.KEY_SETUP_COMPLETED]
                ?: Settings.DEFAULT_SETUP_COMPLETED,

            layout = preferences[Settings.KEY_LAYOUT] ?: Settings.DEFAULT_KEY_LAYOUT,

            iconPadding = preferences[Settings.KEY_ICON_PADDING] ?: Settings.DEFAULT_ICON_PADDING,

            appsViewType = preferences[Settings.APPS_VIEW_TYPE] ?: Settings.DEFAULT_APPS_VIEW_TYPE,

            appsOpacity = preferences[Settings.APPS_OPACITY] ?: Settings.DEFAULT_APPS_OPACITY,

            phoneCols = preferences[Settings.PHONE_COLS] ?: Settings.DEFAULT_PHONE_COLS,

            phoneLandscapeCols = preferences[Settings.PHONE_LANDSCAPE_COLS]
                ?: Settings.DEFAULT_PHONE_LANDSCAPE_COLS,

            tabletCols = preferences[Settings.TABLET_COLS] ?: Settings.DEFAULT_TABLET_COLS,

            tabletLandscapeCols = preferences[Settings.TABLET_LANDSCAPE_COLS]
                ?: Settings.DEFAULT_TABLET_LANDSCAPE_COLS,

            showHomeSearchBar = preferences[Settings.SHOW_HOME_SEARCH_BAR]
                ?: Settings.DEFAULT_SHOW_HOME_SEARCH_BAR,

            showHomeSearchBarPlaceholder = preferences[Settings.SHOW_HOME_SEARCH_BAR_PLACEHOLDER]
                ?: Settings.DEFAULT_SHOW_HOME_SEARCH_BAR_PLACEHOLDER,

            showHomeSearchBarSettings = preferences[Settings.SHOW_HOME_SEARCH_BAR_SETTINGS]
                ?: Settings.DEFAULT_SHOW_HOME_SEARCH_BAR_SETTINGS,

            homeSearchBarOpacity = preferences[Settings.HOME_SEARCH_BAR_OPACITY]
                ?: Settings.DEFAULT_HOME_SEARCH_BAR_OPACITY,

            homeSearchBarRadius = preferences[Settings.HOME_SEARCH_BAR_RADIUS] ?: Settings.DEFAULT_HOME_SEARCH_BAR_RADIUS,

            showAppsSearchBar = preferences[Settings.SHOW_APPS_SEARCH_BAR]
                ?: Settings.DEFAULT_SHOW_APPS_SEARCH_BAR,

            appsSearchBarPosition = preferences[Settings.APPS_SEARCH_BAR_POSITION]
                ?: Settings.DEFAULT_APPS_SEARCH_BAR_POSITION,

            appsSearchBarOpacity = preferences[Settings.APPS_SEARCH_BAR_OPACITY]
                ?: Settings.DEFAULT_APPS_SEARCH_BAR_OPACITY,

            )
    }

    suspend fun updateSetupCompleted(setupCompleted: Boolean) {
        dataStore.edit { it[Settings.KEY_SETUP_COMPLETED] = setupCompleted }
    }

    suspend fun updateLayout(layout: String) {
        dataStore.edit { it[Settings.KEY_LAYOUT] = layout }
    }

    suspend fun updateIconPadding(iconPadding: Int) {
        dataStore.edit { it[Settings.KEY_ICON_PADDING] = iconPadding }
    }

    suspend fun updateAppsViewType(appsViewType: String) {
        dataStore.edit { it[Settings.APPS_VIEW_TYPE] = appsViewType }
    }

    suspend fun updateAppsOpacity(opacity: Float) {
        dataStore.edit { it[Settings.APPS_OPACITY] = opacity }
    }

    suspend fun updatePhoneCols(phoneCols: Int) {
        dataStore.edit { it[Settings.PHONE_COLS] = phoneCols }
    }

    suspend fun updatePhoneLandscapeCols(landscapeCols: Int) {
        dataStore.edit { it[Settings.PHONE_LANDSCAPE_COLS] = landscapeCols }
    }

    suspend fun updateTabletCols(tabletCols: Int) {
        dataStore.edit { it[Settings.TABLET_COLS] = tabletCols }
    }

    suspend fun updateTabletLandscapeCols(landscapeCols: Int) {
        dataStore.edit { it[Settings.TABLET_LANDSCAPE_COLS] = landscapeCols }
    }

    suspend fun updateShowHomeSearchBar(showHomeSearchBar: Boolean) {
        dataStore.edit { it[Settings.SHOW_HOME_SEARCH_BAR] = showHomeSearchBar }
    }

    suspend fun updateShowHomeSearchBarPlaceholder(showHomeSearchBarPlaceholder: Boolean) {
        dataStore.edit { it[Settings.SHOW_HOME_SEARCH_BAR_PLACEHOLDER] = showHomeSearchBarPlaceholder }
    }

    suspend fun updateShowHomeSearchBarSettings(showHomeSearchBarSettings: Boolean) {
        dataStore.edit { it[Settings.SHOW_HOME_SEARCH_BAR_SETTINGS] = showHomeSearchBarSettings }
    }

    suspend fun updateHomeSearchBarOpacity(homeSearchBarOpacity: Float) {
        dataStore.edit { it[Settings.HOME_SEARCH_BAR_OPACITY] = homeSearchBarOpacity }
    }

    suspend fun updateHomeSearchBarRadius(radius: Int){
        dataStore.edit { it[Settings.HOME_SEARCH_BAR_RADIUS] = radius }
    }

    suspend fun updateShowAppsSearchBar(showAppsSearchBar: Boolean) {
        dataStore.edit { it[Settings.SHOW_APPS_SEARCH_BAR] = showAppsSearchBar }
    }

    suspend fun updateAppsSearchBarPosition(appsSearchBarPosition: String) {
        dataStore.edit { it[Settings.APPS_SEARCH_BAR_POSITION] = appsSearchBarPosition }
    }

    suspend fun updateAppsSearchBarOpacity(appsSearchBarOpacity: Float) {
        dataStore.edit { it[Settings.APPS_SEARCH_BAR_OPACITY] = appsSearchBarOpacity }
    }
}