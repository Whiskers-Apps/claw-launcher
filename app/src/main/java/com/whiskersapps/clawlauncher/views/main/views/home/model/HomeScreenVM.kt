package com.whiskersapps.clawlauncher.views.main.views.home.model

import android.app.Application
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whiskersapps.clawlauncher.shared.data.SettingsRepository
import com.whiskersapps.clawlauncher.views.main.views.home.intent.HomeScreenAction
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
                            showMenuDialog = false
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

    fun onAction(action: HomeScreenAction) {
        when (action) {
            HomeScreenAction.NavigateToSettings -> {}
            HomeScreenAction.OpenSearchSheet -> {}
            HomeScreenAction.OpenNotificationPanel -> openNotificationPanel()
            HomeScreenAction.OpenMenuDialog -> updateShowMenuDialog(true)
            HomeScreenAction.CloseMenuDialog -> updateShowMenuDialog(false)
            HomeScreenAction.OpenSettingsDialog -> updateShowSettingsDialog(true)
            HomeScreenAction.CloseSettingsDialog -> updateShowSettingsDialog(false)
            is HomeScreenAction.UpdateSearchBarOpacity -> updateSearchBarOpacity(action.opacity)
            is HomeScreenAction.UpdateSearchBarRadius -> updateSearchBarRadius(action.radius)
            is HomeScreenAction.UpdateShowSearchBar -> updateShowSearchBar(action.show)
            is HomeScreenAction.UpdateShowSearchBarPlaceholder -> updateShowSearchBarPlaceholder(
                action.show
            )

            is HomeScreenAction.UpdateShowSettings -> updateShowSettings(action.show)
        }
    }

    private fun openNotificationPanel() {
        try {
            Class.forName("android.app.StatusBarManager")
                .getMethod("expandNotificationsPanel")
                .invoke(app.getSystemService("statusbar"))
        } catch (e: Exception) {
            println("Error expanding notifications bar. $e")
        }
    }

    private fun updateShowSettingsDialog(show: Boolean) {
        _uiState.update { it?.copy(showSettingsDialog = show) }
    }

    private fun updateShowSearchBar(show: Boolean) {
        viewModelScope.launch {
            settingsRepository.updateShowHomeSearchBar(show)
        }
    }

    private fun updateShowSearchBarPlaceholder(show: Boolean) {
        viewModelScope.launch {
            settingsRepository.updateShowHomeSearchBarPlaceholder(show)
        }
    }

    private fun updateShowSettings(show: Boolean) {
        viewModelScope.launch {
            settingsRepository.updateShowHomeSearchBarSettings(show)
        }
    }

    private fun updateSearchBarOpacity(opacity: Float) {
        viewModelScope.launch {
            settingsRepository.updateHomeSearchBarOpacity(opacity)
        }
    }

    private fun updateSearchBarRadius(radius: Float) {
        viewModelScope.launch {
            settingsRepository.updateHomeSearchBarRadius(radius.toInt())
        }
    }

    private fun updateShowMenuDialog(show: Boolean) {
        _uiState.update { it?.copy(showMenuDialog = show) }
    }
}