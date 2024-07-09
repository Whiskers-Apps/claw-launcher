package com.lighttigerxiv.clawlauncher.features.setup.layout.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lighttigerxiv.clawlauncher.shared.data.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LayoutScreenVM @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<LayoutScreenUiState?>(null)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.Main) {
            settingsRepository.settingsFlow.collect { settings ->
                _uiState.update { LayoutScreenUiState(layout = settings.layout) }
            }
        }
    }

    fun updateLayout(layout: String) {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.updateLayout(layout)
        }
    }

    fun finishSetup(onFinish: () -> Unit) {
        viewModelScope.launch {
            settingsRepository.updateSetupCompleted(true)
            onFinish()
        }
    }
}