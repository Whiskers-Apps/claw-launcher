package com.whiskersapps.clawlauncher.shared.data

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.window.layout.FoldingFeature
import com.whiskersapps.clawlauncher.shared.model.SecuritySettings
import com.whiskersapps.clawlauncher.shared.model.Settings
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.APPS_SEARCH_BAR_POSITION
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.APPS_SEARCH_BAR_RADIUS
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.APPS_VIEW_TYPE
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.DARK_MODE
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.DARK_THEME
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.DEFAULT_APPS_SEARCH_BAR_POSITION
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.DEFAULT_APPS_SEARCH_BAR_RADIUS
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.DEFAULT_APPS_VIEW_TYPE
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.DEFAULT_DARK_MODE
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.DEFAULT_DARK_THEME
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.DEFAULT_DEFAULT_SEARCH_ENGINE
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.DEFAULT_DISABLE_APPS_SCREEN
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.DEFAULT_HOME_SEARCH_BAR_RADIUS
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.DEFAULT_LANDSCAPE_COLS
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.DEFAULT_PORTRAIT_COLS
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.DEFAULT_SEARCH_ENGINE
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.DEFAULT_SETUP_COMPLETED
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.DEFAULT_SHOW_APPS_SEARCH_BAR
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.DEFAULT_SHOW_APPS_SEARCH_BAR_PLACEHOLDER
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.DEFAULT_SHOW_APPS_SEARCH_BAR_SETTINGS
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.DEFAULT_SHOW_HOME_SEARCH_BAR
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.DEFAULT_SHOW_HOME_SEARCH_BAR_PLACEHOLDER
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.DEFAULT_SHOW_HOME_SEARCH_BAR_SETTINGS
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.DEFAULT_SPLIT_LIST_VIEW
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.DEFAULT_SWIPE_UP_TO_SEARCH
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.DEFAULT_THEME
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.DEFAULT_TINT_CLOCK
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.DEFAULT_UNFOLDED_LANDSCAPE_COLS
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.DEFAULT_UNFOLDED_PORTRAIT_COLS
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.DISABLE_APPS_SCREEN
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.HOME_SEARCH_BAR_RADIUS
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.LANDSCAPE_COLS
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.PORTRAIT_COLS
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.SETUP_COMPLETED
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.SHOW_APPS_SEARCH_BAR
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.SHOW_APPS_SEARCH_BAR_PLACEHOLDER
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.SHOW_APPS_SEARCH_BAR_SETTINGS
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.SHOW_HOME_SEARCH_BAR
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.SHOW_HOME_SEARCH_BAR_PLACEHOLDER
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.SHOW_HOME_SEARCH_BAR_SETTINGS
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.SPLIT_LIST_VIEW
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.SWIPE_UP_TO_SEARCH
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.THEME
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.TINT_CLOCK
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.UNFOLDED_LANDSCAPE_COLS
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.UNFOLDED_PORTRAIT_COLS
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.toRealmList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

private val Context.dataStore by preferencesDataStore("settings")

class SettingsRepository(
    val app: Application,
    val realm: Realm
) {

    companion object {
        data class GridColsCount(
            val portrait: Int = DEFAULT_PORTRAIT_COLS,
            val landscape: Int = DEFAULT_LANDSCAPE_COLS
        )
    }

    private val dataStore = app.dataStore

    private val _settings = MutableStateFlow(Settings())
    val settings = _settings.asStateFlow()

    private var useFoldableColumns = false

    val settingsFlow: Flow<Settings> = dataStore.data
        .catch {
            Settings()
            _settings.update { Settings() }
        }
        .map { preferences ->
            val newSettings = Settings(
                setupCompleted = preferences[SETUP_COMPLETED]
                    ?: DEFAULT_SETUP_COMPLETED,

                appsViewType = preferences[APPS_VIEW_TYPE]
                    ?: DEFAULT_APPS_VIEW_TYPE,

                portraitCols = preferences[PORTRAIT_COLS]
                    ?: DEFAULT_PORTRAIT_COLS,

                landscapeCols = preferences[LANDSCAPE_COLS]
                    ?: DEFAULT_LANDSCAPE_COLS,

                unfoldedPortraitCols = preferences[UNFOLDED_PORTRAIT_COLS]
                    ?: DEFAULT_UNFOLDED_PORTRAIT_COLS,

                unfoldedLandscapeCols = preferences[UNFOLDED_LANDSCAPE_COLS]
                    ?: DEFAULT_UNFOLDED_LANDSCAPE_COLS,

                showHomeSearchBar = preferences[SHOW_HOME_SEARCH_BAR]
                    ?: DEFAULT_SHOW_HOME_SEARCH_BAR,

                showHomeSearchBarPlaceholder = preferences[SHOW_HOME_SEARCH_BAR_PLACEHOLDER]
                    ?: DEFAULT_SHOW_HOME_SEARCH_BAR_PLACEHOLDER,

                showHomeSearchBarSettings = preferences[SHOW_HOME_SEARCH_BAR_SETTINGS]
                    ?: DEFAULT_SHOW_HOME_SEARCH_BAR_SETTINGS,

                homeSearchBarRadius = preferences[HOME_SEARCH_BAR_RADIUS]
                    ?: DEFAULT_HOME_SEARCH_BAR_RADIUS,

                showAppsSearchBar = preferences[SHOW_APPS_SEARCH_BAR]
                    ?: DEFAULT_SHOW_APPS_SEARCH_BAR,

                appsSearchBarPosition = preferences[APPS_SEARCH_BAR_POSITION]
                    ?: DEFAULT_APPS_SEARCH_BAR_POSITION,

                showAppsSearchBarPlaceholder = preferences[SHOW_APPS_SEARCH_BAR_PLACEHOLDER]
                    ?: DEFAULT_SHOW_APPS_SEARCH_BAR_PLACEHOLDER,

                showAppsSearchBarSettings = preferences[SHOW_APPS_SEARCH_BAR_SETTINGS]
                    ?: DEFAULT_SHOW_APPS_SEARCH_BAR_SETTINGS,

                appsSearchBarRadius = preferences[APPS_SEARCH_BAR_RADIUS]
                    ?: DEFAULT_APPS_SEARCH_BAR_RADIUS,

                defaultSearchEngine = preferences[DEFAULT_SEARCH_ENGINE]
                    ?: DEFAULT_DEFAULT_SEARCH_ENGINE,

                darkMode = preferences[DARK_MODE] ?: DEFAULT_DARK_MODE,

                theme = preferences[THEME] ?: DEFAULT_THEME,

                darkTheme = preferences[DARK_THEME] ?: DEFAULT_DARK_THEME,

                hiddenApps = getHiddenApps(),

                secureApps = getSecureApps(),

                swipeUpToSearch = preferences[SWIPE_UP_TO_SEARCH]
                    ?: DEFAULT_SWIPE_UP_TO_SEARCH,

                disableAppsScreen = preferences[DISABLE_APPS_SCREEN]
                    ?: DEFAULT_DISABLE_APPS_SCREEN,

                tintClock = preferences[TINT_CLOCK] ?: DEFAULT_TINT_CLOCK,

                splitListView = preferences[SPLIT_LIST_VIEW] ?: DEFAULT_SPLIT_LIST_VIEW
            )

            _settings.update { newSettings }

            newSettings
        }


    private val _gridColsCount = MutableStateFlow(GridColsCount())
    val gridColsCount = _gridColsCount.asStateFlow()

    fun setGridColsCount(feature: FoldingFeature?) {
        useFoldableColumns =
            feature?.state == FoldingFeature.State.FLAT || feature?.state == FoldingFeature.State.HALF_OPENED

        refreshGridColsCount()
    }

    private fun refreshGridColsCount(){
        val settings = settings.value

        _gridColsCount.update {
            GridColsCount(
                portrait = if (useFoldableColumns) settings.unfoldedPortraitCols else settings.portraitCols,
                landscape = if (useFoldableColumns) settings.unfoldedLandscapeCols else settings.landscapeCols
            )
        }
    }

    suspend fun setSetupCompleted(setupCompleted: Boolean) {
        dataStore.edit { it[SETUP_COMPLETED] = setupCompleted }
    }

    suspend fun setAppsViewType(appsViewType: String) {
        dataStore.edit { it[APPS_VIEW_TYPE] = appsViewType }
    }

    suspend fun setPortraitCols(cols: Int) {
        dataStore.edit { it[PORTRAIT_COLS] = cols }
        refreshGridColsCount()
    }

    suspend fun setLandscapeCols(cols: Int) {
        dataStore.edit { it[LANDSCAPE_COLS] = cols }
        refreshGridColsCount()
    }

    suspend fun setUnfoldedCols(cols: Int) {
        dataStore.edit { it[UNFOLDED_PORTRAIT_COLS] = cols }
        refreshGridColsCount()
    }

    suspend fun setUnfoldedLandscapeCols(cols: Int) {
        dataStore.edit { it[UNFOLDED_LANDSCAPE_COLS] = cols }
        refreshGridColsCount()
    }

    suspend fun setShowHomeSearchBar(show: Boolean) {
        println("Show home search bar: $show")
        dataStore.edit { it[SHOW_HOME_SEARCH_BAR] = show }
    }

    suspend fun setShowHomeSearchBarPlaceholder(show: Boolean) {
        dataStore.edit {
            it[SHOW_HOME_SEARCH_BAR_PLACEHOLDER] = show
        }
    }

    suspend fun setShowHomeSearchBarSettings(showHomeSearchBarSettings: Boolean) {
        dataStore.edit { it[SHOW_HOME_SEARCH_BAR_SETTINGS] = showHomeSearchBarSettings }
    }

    suspend fun setHomeSearchBarRadius(radius: Int) {
        dataStore.edit { it[HOME_SEARCH_BAR_RADIUS] = radius }
    }

    suspend fun setShowAppsSearchBar(showAppsSearchBar: Boolean) {
        dataStore.edit { it[SHOW_APPS_SEARCH_BAR] = showAppsSearchBar }
    }

    suspend fun setAppsSearchBarPosition(appsSearchBarPosition: String) {
        dataStore.edit { it[APPS_SEARCH_BAR_POSITION] = appsSearchBarPosition }
    }

    suspend fun updateDefaultSearchEngine(id: String) {
        dataStore.edit { it[DEFAULT_SEARCH_ENGINE] = id }
    }

    suspend fun setShowAppsSearchBarPlaceholder(show: Boolean) {
        dataStore.edit { it[SHOW_APPS_SEARCH_BAR_PLACEHOLDER] = show }
    }

    suspend fun setShowAppsSearchBarSettings(show: Boolean) {
        dataStore.edit { it[SHOW_APPS_SEARCH_BAR_SETTINGS] = show }
    }

    suspend fun setAppsSearchBarRadius(radius: Int) {
        dataStore.edit { it[APPS_SEARCH_BAR_RADIUS] = radius }
    }

    suspend fun setDarkMode(darkMode: String) {
        dataStore.edit { it[DARK_MODE] = darkMode }
    }

    suspend fun setTheme(theme: String) {
        dataStore.edit { it[THEME] = theme }
    }

    suspend fun setDarkTheme(theme: String) {
        dataStore.edit { it[DARK_THEME] = theme }
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
        dataStore.edit { it[SWIPE_UP_TO_SEARCH] = swipeUp }
    }

    suspend fun setDisableAppsScreen(disable: Boolean) {
        dataStore.edit { it[DISABLE_APPS_SCREEN] = disable }
    }

    suspend fun setTintClock(tint: Boolean){
        dataStore.edit { it[TINT_CLOCK] = tint }
    }

    suspend fun setSplitList(split: Boolean){
        dataStore.edit { it[SPLIT_LIST_VIEW] = split }
    }
}