package com.whiskersapps.clawlauncher.settings.apps

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whiskersapps.clawlauncher.settings.di.SettingsRepo
import com.whiskersapps.clawlauncher.shared.utils.isFoldable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppsSettingsScreenVM(
    private val settingsRepo: SettingsRepo,
    private val app: Application
) : ViewModel() {

    private val _state = MutableStateFlow(AppsSettingsScreenState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.Main) {
            settingsRepo.settings.collect { settings ->
                _state.update {
                    it.copy(
                        loading = false,
                        disableAppsScreen = settings.disableAppsScreen,
                        viewType = settings.appsViewType,
                        portraitColumns = settings.portraitCols.toFloat(),
                        landscapeColumns = settings.landscapeCols.toFloat(),
                        isFoldable = isFoldable(app),
                        unfoldedPortraitColumns = settings.unfoldedPortraitCols.toFloat(),
                        unfoldedLandscapeColumns = settings.unfoldedLandscapeCols.toFloat(),
                        splitList = settings.splitListView,
                        showSearchBar = settings.showAppsSearchBar,
                        searchBarPosition = settings.appsSearchBarPosition,
                        showSearchBarPlaceholder = settings.showAppsSearchBarPlaceholder,
                        searchBarRadius = settings.appsSearchBarRadius.toFloat()
                    )
                }
            }
        }
    }

    fun onAction(action: AppsSettingsScreenAction) {
        viewModelScope.launch(Dispatchers.IO) {
            when (action) {
                AppsSettingsScreenAction.NavigateBack -> {}

                is AppsSettingsScreenAction.SetPortraitColumns -> settingsRepo.setPortraitCols(
                    action.cols.toInt()
                )

                is AppsSettingsScreenAction.SetLandscapeColumns -> settingsRepo.setLandscapeCols(
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

                is AppsSettingsScreenAction.SetUnfoldedPortraitColumns -> settingsRepo.setUnfoldedCols(
                    action.cols.toInt()
                )

                is AppsSettingsScreenAction.SetUnfoldedLandscapeColumns -> settingsRepo.setUnfoldedLandscapeCols(
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