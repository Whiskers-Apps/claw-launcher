package com.whiskersapps.clawlauncher.views.main.views.settings.views.home.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whiskersapps.clawlauncher.shared.data.SettingsRepository
import com.whiskersapps.clawlauncher.views.main.views.settings.views.home.intent.HomeSettingsScreenAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeSettingsScreenVM @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeSettingsScreenState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.settings.collect { settings ->
                _state.update { it.copy(loading = false, settings = settings) }
            }
        }
    }

    fun onAction(action: HomeSettingsScreenAction) {
        viewModelScope.launch(Dispatchers.IO) {
            when (action) {

                is HomeSettingsScreenAction.SetSearchBarRadius -> settingsRepository.setHomeSearchBarRadius(
                    action.radius.toInt()
                )

                is HomeSettingsScreenAction.SetShowSearchBar -> settingsRepository.setShowHomeSearchBar(
                    action.show
                )

                is HomeSettingsScreenAction.SetShowSearchBarPlaceholder -> settingsRepository.setShowHomeSearchBarPlaceholder(
                    action.show
                )

                is HomeSettingsScreenAction.SetShowSettings -> settingsRepository.setShowHomeSearchBarSettings(
                    action.show
                )

                HomeSettingsScreenAction.NavigateBack -> {}

                is HomeSettingsScreenAction.SaveSearchBarRadius -> settingsRepository.setHomeSearchBarRadius(action.radius.toInt())
            }
        }
    }
}