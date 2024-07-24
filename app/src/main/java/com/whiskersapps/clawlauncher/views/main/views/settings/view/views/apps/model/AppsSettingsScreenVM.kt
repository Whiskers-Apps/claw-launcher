package com.whiskersapps.clawlauncher.views.main.views.settings.view.views.apps.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whiskersapps.clawlauncher.shared.data.SettingsRepository
import com.whiskersapps.clawlauncher.views.main.views.settings.view.views.apps.intent.AppsSettingsScreenAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppsSettingsScreenVM @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AppsSettingsScreenState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.Main) {
            settingsRepository.settingsFlow.collect { settings ->
                _state.update { it.copy(loading = false, settings = settings) }
            }
        }
    }

    fun onAction(action: AppsSettingsScreenAction) {
        viewModelScope.launch(Dispatchers.Main) {
            when (action) {
                AppsSettingsScreenAction.NavigateBack -> {}
                is AppsSettingsScreenAction.SetBackgroundOpacity -> settingsRepository.setAppsOpacity(
                    action.opacity
                )

                is AppsSettingsScreenAction.SetPhoneCols -> settingsRepository.setPhoneCols(
                    action.cols.toInt()
                )

                is AppsSettingsScreenAction.SetPhoneLandscapeCols -> settingsRepository.setPhoneLandscapeCols(
                    action.cols.toInt()
                )

                is AppsSettingsScreenAction.SetSearchBarOpacity -> settingsRepository.setAppsSearchBarOpacity(
                    action.opacity
                )

                is AppsSettingsScreenAction.SetSearchBarPosition -> settingsRepository.setAppsSearchBarPosition(
                    action.position
                )

                is AppsSettingsScreenAction.SetSearchBarRadius -> settingsRepository.setAppsSearchBarRadius(
                    action.radius.toInt()
                )

                is AppsSettingsScreenAction.SetShowSearchBar -> settingsRepository.setShowAppsSearchBar(
                    action.show
                )

                is AppsSettingsScreenAction.SetShowSearchBarPlaceholder -> settingsRepository.setShowAppsSearchBarPlaceholder(
                    action.show
                )

                is AppsSettingsScreenAction.SetShowSearchBarSettings -> settingsRepository.setShowAppsSearchBarSettings(
                    action.show
                )

                is AppsSettingsScreenAction.SetTabletCols -> settingsRepository.setTabletCols(
                    action.cols.toInt()
                )

                is AppsSettingsScreenAction.SetTabletLandscapeCols -> settingsRepository.setTabletLandscapeCols(
                    action.cols.toInt()
                )

                is AppsSettingsScreenAction.SetViewType -> settingsRepository.setAppsViewType(
                    action.type
                )
            }
        }
    }
}