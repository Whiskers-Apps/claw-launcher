package com.whiskersapps.clawlauncher.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whiskersapps.clawlauncher.launcher.search_engines.SearchEnginesRepo
import com.whiskersapps.clawlauncher.settings.SettingsRepo
import com.whiskersapps.clawlauncher.shared.model.Settings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OnBoardingActivityVM (
    settingsRepo: SettingsRepo,
    searchEnginesRepo: SearchEnginesRepo
) : ViewModel() {

    private val _settings = MutableStateFlow<Settings?>(null)
    val settings = _settings.asStateFlow()

    init {
        viewModelScope.launch {
            settingsRepo.settingsFlow.collect { newSettings ->
                _settings.update { newSettings }
            }
        }
        viewModelScope.launch {
            searchEnginesRepo.data.collect { data ->
                if (!data.loading) {
                    if (data.searchEngines.isEmpty()) {
                        searchEnginesRepo.initEngines()
                    }
                }
            }
        }
    }
}