package com.lighttigerxiv.clawlauncher.shared.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lighttigerxiv.clawlauncher.shared.data.SettingsRepository
import com.lighttigerxiv.clawlauncher.shared.model.Settings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsVM @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    private val _settings = MutableStateFlow<Settings?>(null)
    val settings = _settings.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.Main) {
            settingsRepository.settingsFlow.collect { newSettings ->
                _settings.update { newSettings }
            }
        }
    }
}