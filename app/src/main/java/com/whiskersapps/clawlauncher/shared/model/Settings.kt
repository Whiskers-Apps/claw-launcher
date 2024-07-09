package com.whiskersapps.clawlauncher.shared.model

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

data class Settings(
    val setupCompleted: Boolean,
    val layout: String
){
    companion object{
        val KEY_SETUP_COMPLETED = booleanPreferencesKey("setup-completed")
        const val DEFAULT_SETUP_COMPLETED = false
        val KEY_LAYOUT = stringPreferencesKey("layout")
        const val DEFAULT_KEY_LAYOUT = "minimal"
    }
}