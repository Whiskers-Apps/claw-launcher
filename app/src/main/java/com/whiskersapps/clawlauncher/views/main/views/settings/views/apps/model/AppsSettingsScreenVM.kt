package com.whiskersapps.clawlauncher.views.main.views.settings.views.apps.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whiskersapps.clawlauncher.settings.SettingsRepo
import com.whiskersapps.clawlauncher.views.main.views.settings.views.apps.intent.AppsSettingsScreenAction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppsSettingsScreenVM constructor(
    private val settingsRepo: SettingsRepo
) : ViewModel() {

    private val _state = MutableStateFlow(AppsSettingsScreenState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepo.settings.collect { settings ->
                _state.update { it.copy(loading = false, settings = settings) }
            }
        }
    }

    fun onAction(action: AppsSettingsScreenAction) {
        viewModelScope.launch(Dispatchers.IO) {
            when (action) {
                AppsSettingsScreenAction.NavigateBack -> {}

                is AppsSettingsScreenAction.SetPhoneCols -> settingsRepo.setPortraitCols(
                    action.cols.toInt()
                )

                is AppsSettingsScreenAction.SetPhoneLandscapeCols -> settingsRepo.setLandscapeCols(
                    action.cols.toInt()
                )

                is AppsSettingsScreenAction.SetSearchBarPosition -> settingsRepo.setAppsSearchBarPosition(
                    action.position
                )

                is AppsSettingsScreenAction.SetSearchBarRadius -> settingsRepo.setAppsSearchBarRadius(
                    action.radius.toInt()
                )

                is AppsSettingsScreenAction.SetShowSearchBar -> settingsRepo.setShowAppsSearchBar(
                    action.show
                )

                is AppsSettingsScreenAction.SetShowSearchBarPlaceholder -> settingsRepo.setShowAppsSearchBarPlaceholder(
                    action.show
                )

                is AppsSettingsScreenAction.SetShowSearchBarSettings -> settingsRepo.setShowAppsSearchBarSettings(
                    action.show
                )

                is AppsSettingsScreenAction.SetTabletCols -> settingsRepo.setUnfoldedCols(
                    action.cols.toInt()
                )

                is AppsSettingsScreenAction.SetTabletLandscapeCols -> settingsRepo.setUnfoldedLandscapeCols(
                    action.cols.toInt()
                )

                is AppsSettingsScreenAction.SetViewType -> settingsRepo.setAppsViewType(
                    action.type
                )

                is AppsSettingsScreenAction.SetDisableAppsScreen -> settingsRepo.setDisableAppsScreen(
                    action.disable
                )

                is AppsSettingsScreenAction.SetSplitList -> {
                    settingsRepo.setSplitList(action.split)
                }
            }
        }
    }
}