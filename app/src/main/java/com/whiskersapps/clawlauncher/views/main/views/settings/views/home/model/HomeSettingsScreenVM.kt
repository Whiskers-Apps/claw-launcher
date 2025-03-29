package com.whiskersapps.clawlauncher.views.main.views.settings.views.home.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whiskersapps.clawlauncher.settings.SettingsRepo
import com.whiskersapps.clawlauncher.views.main.views.settings.views.home.intent.HomeSettingsScreenAction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeSettingsScreenVM(
    private val settingsRepo: SettingsRepo
) : ViewModel() {

    private val _state = MutableStateFlow(HomeSettingsScreenState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepo.settings.collect { settings ->
                _state.update { it.copy(loading = false, settings = settings) }
            }
        }
    }

    fun onAction(action: HomeSettingsScreenAction) {
        viewModelScope.launch(Dispatchers.IO) {
            when (action) {

                is HomeSettingsScreenAction.SetSearchBarRadius -> settingsRepo.setHomeSearchBarRadius(
                    action.radius.toInt()
                )

                is HomeSettingsScreenAction.SetShowSearchBar -> settingsRepo.setShowHomeSearchBar(
                    action.show
                )

                is HomeSettingsScreenAction.SetShowSearchBarPlaceholder -> settingsRepo.setShowHomeSearchBarPlaceholder(
                    action.show
                )

                is HomeSettingsScreenAction.SetShowSettings -> settingsRepo.setShowHomeSearchBarSettings(
                    action.show
                )

                HomeSettingsScreenAction.NavigateBack -> {}

                is HomeSettingsScreenAction.SaveSearchBarRadius -> settingsRepo.setHomeSearchBarRadius(
                    action.radius.toInt()
                )

                is HomeSettingsScreenAction.SetSwipeUpToSearch -> settingsRepo.setSwipeUpToSearch(
                    action.swipeUp
                )

                is HomeSettingsScreenAction.SetTintIcon -> settingsRepo.setTintClock(action.tint)

                is HomeSettingsScreenAction.SetClockPlacement -> settingsRepo.setClockPlacement(
                    action.placement
                )
            }
        }
    }
}