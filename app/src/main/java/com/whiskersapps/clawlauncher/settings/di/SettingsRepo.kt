package com.whiskersapps.clawlauncher.settings.di

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.whiskersapps.clawlauncher.shared.model.SecuritySettings
import com.whiskersapps.clawlauncher.shared.model.Settings
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.toRealmList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

val Context.dataStore by preferencesDataStore("settings")

class SettingsRepo(
    app: Application,
    private val realm: Realm
) {
    private val dataStore = app.dataStore

    private val _settings = MutableStateFlow(Settings())
    val settings = _settings.asStateFlow()


    val settingsFlow: Flow<Settings> = dataStore.data
        .catch {
            Settings()
            _settings.update { Settings() }
        }
        .map { preferences ->

            val newSettings = Settings(
                setupCompleted = preferences[Settings.SETUP_COMPLETED]
                    ?: Settings.DEFAULT_SETUP_COMPLETED,

                appsViewType = preferences[Settings.APPS_VIEW_TYPE]
                    ?: Settings.DEFAULT_APPS_VIEW_TYPE,

                portraitCols = preferences[Settings.PORTRAIT_COLS]
                    ?: Settings.DEFAULT_PORTRAIT_COLS,

                landscapeCols = preferences[Settings.LANDSCAPE_COLS]
                    ?: Settings.DEFAULT_LANDSCAPE_COLS,

                unfoldedPortraitCols = preferences[Settings.UNFOLDED_PORTRAIT_COLS]
                    ?: Settings.DEFAULT_UNFOLDED_PORTRAIT_COLS,

                unfoldedLandscapeCols = preferences[Settings.UNFOLDED_LANDSCAPE_COLS]
                    ?: Settings.DEFAULT_UNFOLDED_LANDSCAPE_COLS,

                showHomeSearchBar = preferences[Settings.SHOW_HOME_SEARCH_BAR]
                    ?: Settings.DEFAULT_SHOW_HOME_SEARCH_BAR,

                showHomeSearchBarPlaceholder = preferences[Settings.SHOW_HOME_SEARCH_BAR_PLACEHOLDER]
                    ?: Settings.DEFAULT_SHOW_HOME_SEARCH_BAR_PLACEHOLDER,

                homeSearchBarRadius = preferences[Settings.HOME_SEARCH_BAR_RADIUS]
                    ?: Settings.DEFAULT_HOME_SEARCH_BAR_RADIUS,

                showAppsSearchBar = preferences[Settings.SHOW_APPS_SEARCH_BAR]
                    ?: Settings.DEFAULT_SHOW_APPS_SEARCH_BAR,

                appsSearchBarPosition = preferences[Settings.APPS_SEARCH_BAR_POSITION]
                    ?: Settings.DEFAULT_APPS_SEARCH_BAR_POSITION,

                showAppsSearchBarPlaceholder = preferences[Settings.SHOW_APPS_SEARCH_BAR_PLACEHOLDER]
                    ?: Settings.DEFAULT_SHOW_APPS_SEARCH_BAR_PLACEHOLDER,

                appsSearchBarRadius = preferences[Settings.APPS_SEARCH_BAR_RADIUS]
                    ?: Settings.DEFAULT_APPS_SEARCH_BAR_RADIUS,

                defaultSearchEngine = preferences[Settings.DEFAULT_SEARCH_ENGINE]
                    ?: Settings.DEFAULT_DEFAULT_SEARCH_ENGINE,

                darkMode = preferences[Settings.DARK_MODE] ?: Settings.DEFAULT_DARK_MODE,

                theme = preferences[Settings.THEME] ?: Settings.DEFAULT_THEME,

                darkTheme = preferences[Settings.DARK_THEME] ?: Settings.DEFAULT_DARK_THEME,

                hiddenApps = getHiddenApps(),

                secureApps = getSecureApps(),

                swipeUpToSearch = preferences[Settings.SWIPE_UP_TO_SEARCH]
                    ?: Settings.DEFAULT_SWIPE_UP_TO_SEARCH,

                disableAppsScreen = preferences[Settings.DISABLE_APPS_SCREEN]
                    ?: Settings.DEFAULT_DISABLE_APPS_SCREEN,

                tintClock = preferences[Settings.TINT_CLOCK] ?: Settings.DEFAULT_TINT_CLOCK,

                splitListView = preferences[Settings.SPLIT_LIST_VIEW]
                    ?: Settings.DEFAULT_SPLIT_LIST_VIEW,

                clockPlacement = preferences[Settings.CLOCK_PLACEMENT]
                    ?: Settings.DEFAULT_CLOCK_PLACEMENT,

                pillShapeClock = preferences[Settings.PILL_SHAPE_CLOCK]
                    ?: Settings.DEFAULT_PILL_SHAPE_CLOCK,

                hideAppLabels = preferences[Settings.HIDE_APP_LABELS]
                    ?: Settings.DEFAULT_HIDE_APP_LABELS,

                iconPack = preferences[Settings.ICON_PACK] ?: Settings.DEFAULT_ICON_PACK
            )

            _settings.update { newSettings }

            newSettings
        }

    suspend fun setSetupCompleted(setupCompleted: Boolean) {
        dataStore.edit { it[Settings.SETUP_COMPLETED] = setupCompleted }
    }

    suspend fun setAppsViewType(appsViewType: String) {
        dataStore.edit { it[Settings.APPS_VIEW_TYPE] = appsViewType }
    }

    suspend fun setPortraitCols(cols: Int) {
        dataStore.edit { it[Settings.PORTRAIT_COLS] = cols }
    }

    suspend fun setLandscapeCols(cols: Int) {
        dataStore.edit { it[Settings.LANDSCAPE_COLS] = cols }
    }

    suspend fun setUnfoldedCols(cols: Int) {
        dataStore.edit { it[Settings.UNFOLDED_PORTRAIT_COLS] = cols }
    }

    suspend fun setUnfoldedLandscapeCols(cols: Int) {
        dataStore.edit { it[Settings.UNFOLDED_LANDSCAPE_COLS] = cols }
    }

    suspend fun setShowHomeSearchBar(show: Boolean) {
        dataStore.edit { it[Settings.SHOW_HOME_SEARCH_BAR] = show }
    }

    suspend fun setShowHomeSearchBarPlaceholder(show: Boolean) {
        dataStore.edit {
            it[Settings.SHOW_HOME_SEARCH_BAR_PLACEHOLDER] = show
        }
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

    suspend fun setAppsSearchBarRadius(radius: Int) {
        dataStore.edit { it[Settings.APPS_SEARCH_BAR_RADIUS] = radius }
    }

    suspend fun setDarkMode(darkMode: String) {
        dataStore.edit { it[Settings.DARK_MODE] = darkMode }
    }

    suspend fun setTheme(theme: String) {
        dataStore.edit { it[Settings.THEME] = theme }
    }

    suspend fun setDarkTheme(theme: String) {
        dataStore.edit { it[Settings.DARK_THEME] = theme }
    }

    private fun getHiddenApps(): List<String> {
        return realm.query<SecuritySettings>().find().firstOrNull()?.hiddenApps ?: emptyList()
    }

    fun setHiddenApps(apps: List<String>) {
        realm.writeBlocking {
            val securitySettings = query<SecuritySettings>().find().firstOrNull()

            if (securitySettings == null) {
                val settings = SecuritySettings().apply {
                    hiddenApps = apps.toRealmList()
                }

                copyToRealm(settings)
            } else {
                securitySettings.hiddenApps = apps.toRealmList()
            }
        }

        _settings.update { it.copy(hiddenApps = apps) }
    }

    private fun getSecureApps(): List<String> {
        return realm.query<SecuritySettings>().first().find()?.secureApps ?: emptyList()
    }

    fun setSecureApps(apps: List<String>) {
        realm.writeBlocking {
            val securitySettings = query<SecuritySettings>().find().firstOrNull()

            if (securitySettings == null) {
                val settings = SecuritySettings().apply {
                    secureApps = apps.toRealmList()
                }

                copyToRealm(settings)
            } else {
                securitySettings.secureApps = apps.toRealmList()
            }
        }

        _settings.update { it.copy(secureApps = apps) }
    }

    suspend fun setSwipeUpToSearch(swipeUp: Boolean) {
        dataStore.edit { it[Settings.SWIPE_UP_TO_SEARCH] = swipeUp }
    }

    suspend fun setDisableAppsScreen(disable: Boolean) {
        dataStore.edit { it[Settings.DISABLE_APPS_SCREEN] = disable }
    }

    suspend fun setTintClock(tint: Boolean) {
        dataStore.edit { it[Settings.TINT_CLOCK] = tint }
    }

    suspend fun setSplitList(split: Boolean) {
        dataStore.edit { it[Settings.SPLIT_LIST_VIEW] = split }
    }

    suspend fun setClockPlacement(placement: String) {
        dataStore.edit { it[Settings.CLOCK_PLACEMENT] = placement }
    }

    suspend fun setPillShapeClock(pill: Boolean) {
        dataStore.edit { it[Settings.PILL_SHAPE_CLOCK] = pill }
    }

    suspend fun setHideAppLabels(hide: Boolean) {
        dataStore.edit { it[Settings.HIDE_APP_LABELS] = hide }
    }

    suspend fun setIconPack(packageName: String) {
        dataStore.edit { it[Settings.ICON_PACK] = packageName }
    }
}