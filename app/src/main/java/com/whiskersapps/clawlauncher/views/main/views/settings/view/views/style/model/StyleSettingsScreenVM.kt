package com.whiskersapps.clawlauncher.views.main.views.settings.view.views.style.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whiskersapps.clawlauncher.shared.data.SettingsRepository
import com.whiskersapps.clawlauncher.views.main.views.settings.view.views.style.intent.StyleSettingsScreenAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StyleSettingsScreenVM @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(StyleSettingsScreenState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.Main) {
            settingsRepository.settingsFlow.collect { settings ->
                _state.update {
                    it.copy(
                        loading = false,
                        loadingSettings = false,
                        settings = settings
                    )
                }
            }
        }
    }

    fun onAction(action: StyleSettingsScreenAction) {
        viewModelScope.launch(Dispatchers.Main) {
            when (action) {
                StyleSettingsScreenAction.NavigateBack -> {}
                StyleSettingsScreenAction.OpenDarkModeDialog -> setShowDarkModeDialog(true)
                StyleSettingsScreenAction.CloseDarkModeDialog -> setShowDarkModeDialog(false)
                is StyleSettingsScreenAction.SetDarkMode -> setDarkMode(action.darkMode)
            }
        }
    }

    private suspend fun setDarkMode(darkMode: String){
        settingsRepository.setDarkMode(darkMode)
        setShowDarkModeDialog(false)
    }

    private fun setShowDarkModeDialog(show: Boolean) {
        _state.update { it.copy(showDarkModeDialog = show) }
    }
}