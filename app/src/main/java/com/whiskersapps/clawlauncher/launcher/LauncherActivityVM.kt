package com.whiskersapps.clawlauncher.launcher

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whiskersapps.clawlauncher.settings.SettingsRepo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class LauncherActivityVM (
    settingsRepo: SettingsRepo,
) : ViewModel() {
    val settings = settingsRepo.settingsFlow.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        null
    )
}