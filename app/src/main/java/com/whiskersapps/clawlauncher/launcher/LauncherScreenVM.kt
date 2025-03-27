package com.whiskersapps.clawlauncher.launcher

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whiskersapps.clawlauncher.launcher.apps.AppsRepo
import com.whiskersapps.clawlauncher.settings.SettingsRepo
import com.whiskersapps.clawlauncher.shared.model.App
import com.whiskersapps.clawlauncher.shared.model.Settings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LauncherScreenVM @Inject constructor(
    private val settingsRepo: SettingsRepo,
    private val appsRepo: AppsRepo
) : ViewModel() {
    private val _state = MutableStateFlow<LauncherScreenState>(LauncherScreenState.Loading)
    val state = _state.asStateFlow()

    private var settings: Settings? = null
    private var apps: List<App>? = null

    init {
        fun verifyLoading() {
            if (settings != null && apps != null) {
                _state.update { LauncherScreenState.Loaded(settings!!) }
            }
        }

        viewModelScope.launch(Dispatchers.Main) {
            settingsRepo.settings.collect { newSettings ->
                settings = newSettings
                verifyLoading()
            }
        }

        viewModelScope.launch(Dispatchers.Main) {
            appsRepo.apps.collect { newApps ->
                apps = newApps
                verifyLoading()
            }
        }
    }
}