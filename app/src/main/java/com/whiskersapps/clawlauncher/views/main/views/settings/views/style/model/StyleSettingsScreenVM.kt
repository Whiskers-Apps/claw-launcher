package com.whiskersapps.clawlauncher.views.main.views.settings.views.style.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whiskersapps.clawlauncher.shared.data.SettingsRepository
import com.whiskersapps.clawlauncher.views.main.views.settings.views.style.intent.StyleSettingsScreenAction
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
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.settings.collect { settings ->
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
        when (action) {
            StyleSettingsScreenAction.NavigateBack -> {}
            StyleSettingsScreenAction.OpenDarkModeDialog -> setShowDarkModeDialog(true)
            StyleSettingsScreenAction.CloseDarkModeDialog -> setShowDarkModeDialog(false)
            is StyleSettingsScreenAction.SetDarkMode -> setDarkMode(action.darkMode)
            StyleSettingsScreenAction.CloseThemeDialog -> closeThemeDialog()
            StyleSettingsScreenAction.OpenThemeDialog -> openThemeDialog()
            StyleSettingsScreenAction.CloseDarkThemeDialog -> closeDarkThemeDialog()
            StyleSettingsScreenAction.OpenDarkThemeDialog -> openDarkThemeDialog()
            is StyleSettingsScreenAction.SelectThemeDialogTab -> selectThemeDialogTab(action.index)
            is StyleSettingsScreenAction.SetTheme -> setTheme(action.themeId)
            is StyleSettingsScreenAction.SetDarkTheme -> setDarkTheme(action.themeId)
        }
    }

    private fun setDarkMode(darkMode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.setDarkMode(darkMode)
            setShowDarkModeDialog(false)
        }
    }

    private fun setShowDarkModeDialog(show: Boolean) {
        _state.update { it.copy(showDarkModeDialog = show) }
    }

    private fun openThemeDialog() {
        _state.update { it.copy(showThemeDialog = true) }
    }

    private fun closeThemeDialog() {
        _state.update { it.copy(showThemeDialog = false) }
    }

    private fun openDarkThemeDialog() {
        _state.update { it.copy(showDarkThemeDialog = true) }
    }

    private fun closeDarkThemeDialog() {
        _state.update { it.copy(showDarkThemeDialog = false) }
    }

    private fun selectThemeDialogTab(index: Int) {
        _state.update { it.copy(themeDialogTabIndex = index) }
    }

    private fun setTheme(theme: String) {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.setTheme(theme)
        }
    }

    private fun setDarkTheme(theme: String) {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.setDarkTheme(theme)
        }
    }
}