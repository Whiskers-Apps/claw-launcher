package com.whiskersapps.clawlauncher.shared.data

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.whiskersapps.clawlauncher.shared.model.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

private val Context.dataStore by preferencesDataStore("settings")

class SettingsRepository(app: Application) {

    private val dataStore = app.dataStore

    val settingsFlow: Flow<Settings> = dataStore.data.catch {
        getDefaultSettings()
    }.map { preferences ->
        getSettings(preferences)
    }

    private fun getSettings(preferences: Preferences): Settings {
        return Settings(
            setupCompleted = preferences[Settings.KEY_SETUP_COMPLETED]
                ?: Settings.DEFAULT_SETUP_COMPLETED,
            layout = preferences[Settings.KEY_LAYOUT]
                ?: Settings.DEFAULT_KEY_LAYOUT
        )
    }

    private fun getDefaultSettings(): Settings {
        return Settings(
            setupCompleted = Settings.DEFAULT_SETUP_COMPLETED,
            layout = Settings.DEFAULT_KEY_LAYOUT
        )
    }

    suspend fun updateSetupCompleted(setupCompleted: Boolean) {
        dataStore.edit { it[Settings.KEY_SETUP_COMPLETED] = setupCompleted }
    }

    suspend fun updateLayout(layout: String){
        dataStore.edit { it[Settings.KEY_LAYOUT] = layout }
    }
}