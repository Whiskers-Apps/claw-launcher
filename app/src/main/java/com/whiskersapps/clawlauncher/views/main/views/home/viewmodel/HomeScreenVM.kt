package com.whiskersapps.clawlauncher.views.main.views.home.viewmodel

import android.app.Application
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whiskersapps.clawlauncher.shared.data.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenVM @Inject constructor(
    val settingsRepository: SettingsRepository,
    val app: Application
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeScreenUiState?>(null)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.Main) {
            settingsRepository.settingsFlow.collect { settings ->
                if (uiState.value == null) {
                    _uiState.update {
                        HomeScreenUiState(
                            searchBarRadius = if (settings.homeSearchBarRadius != -1) settings.homeSearchBarRadius.dp else null,
                            searchBarOpacity = settings.homeSearchBarOpacity,
                            showSearchBar = settings.showHomeSearchBar,
                            showPlaceholder = settings.showHomeSearchBarPlaceholder,
                            showSettings = settings.showHomeSearchBarSettings,
                            dateText = "",
                            hourText = "",
                            showSettingsDialog = false,
                            showHomeDialog = false
                        )
                    }
                } else {
                    _uiState.update {
                        it?.copy(
                            showSearchBar = settings.showHomeSearchBar,
                            showPlaceholder = settings.showHomeSearchBarPlaceholder,
                            searchBarOpacity = settings.homeSearchBarOpacity,
                            searchBarRadius = if (settings.homeSearchBarRadius != -1) settings.homeSearchBarRadius.dp else null,
                            showSettings = settings.showHomeSearchBarSettings,
                        )
                    }
                }
            }
        }
    }

    fun openNotificationPanel() {
        try {
            Class.forName("android.app.StatusBarManager")
                .getMethod("expandNotificationsPanel")
                .invoke(app.getSystemService("statusbar"))
        } catch (e: Exception) {
            println("Error expanding notifications bar. $e")
        }
    }

    fun updateShowSettingsDialog(show: Boolean) {
        _uiState.update { it?.copy(showSettingsDialog = show) }
    }

    fun updateShowSearchBar(show: Boolean) {
        viewModelScope.launch {
            settingsRepository.updateShowHomeSearchBar(show)
        }
    }

    fun updateShowSearchBarPlaceholder(show: Boolean) {
        viewModelScope.launch {
            settingsRepository.updateShowHomeSearchBarPlaceholder(show)
        }
    }

    fun updateShowSettings(show: Boolean) {
        viewModelScope.launch {
            settingsRepository.updateShowHomeSearchBarSettings(show)
        }
    }

    fun updateSearchBarOpacity(opacity: Float) {
        viewModelScope.launch {
            settingsRepository.updateHomeSearchBarOpacity(opacity)
        }
    }

    fun updateSearchBarRadius(radius: Float) {
        viewModelScope.launch {
            settingsRepository.updateHomeSearchBarRadius(radius.toInt())
        }
    }

    fun updateShowHomeDialog(show: Boolean) {
        _uiState.update { it?.copy(showHomeDialog = show) }
    }
}