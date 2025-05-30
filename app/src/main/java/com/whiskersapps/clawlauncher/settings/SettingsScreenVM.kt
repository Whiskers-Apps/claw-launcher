package com.whiskersapps.clawlauncher.settings

import android.app.Application
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.Settings
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whiskersapps.clawlauncher.settings.di.SettingsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class SettingsScreenVM constructor(
    private val app: Application,
    private val settingsRepo: SettingsRepo,
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsScreenState())
    val state = _state.asStateFlow()


    init {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepo.settings.collect { settings ->
                _state.update {
                    it.copy(
                        loading = false,
                        settings = settings,
                        isDefaultLauncher = isDefaultLauncher()
                    )
                }
            }
        }
    }

    fun onAction(action: SettingsScreenAction) {
        when (action) {
            SettingsScreenAction.NavigateBack -> {}
            SettingsScreenAction.NavigateToAbout -> {}
            SettingsScreenAction.NavigateToAppsSettings -> {}
            SettingsScreenAction.NavigateToBookmarksSettings -> {}
            SettingsScreenAction.NavigateToHomeSettings -> {}
            SettingsScreenAction.NavigateToSearchEnginesSettings -> {}
            SettingsScreenAction.NavigateToStyleSettings -> {}
            SettingsScreenAction.NavigateToSecuritySettings -> {}
            SettingsScreenAction.SetDefaultLauncher -> setDefaultLauncher()
            SettingsScreenAction.NavigateToLockScreenSettings -> {}
        }
    }

    private fun isDefaultLauncher(): Boolean {
        val intent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_HOME)
        }

        val resolve = app.packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)

        return resolve?.activityInfo?.packageName == app.packageName
    }

    private fun setDefaultLauncher() {

        val intent = Intent(Settings.ACTION_HOME_SETTINGS)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        app.startActivity(intent)

        _state.update { it.copy(isDefaultLauncher = isDefaultLauncher()) }
    }
}