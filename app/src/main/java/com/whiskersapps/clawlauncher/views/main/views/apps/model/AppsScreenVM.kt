package com.whiskersapps.clawlauncher.views.main.views.apps.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whiskersapps.clawlauncher.shared.data.AppsRepository
import com.whiskersapps.clawlauncher.shared.data.SettingsRepository
import com.whiskersapps.clawlauncher.views.main.views.apps.intent.AppsScreenAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppsScreenVM @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val appsRepository: AppsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AppsScreenState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.Main) {
            settingsRepository.settingsFlow.collect { settings ->
                _state.update {
                    it.copy(
                        loadingSettings = false,
                        loading = it.loadingApps,
                        viewType = settings.appsViewType,
                        cols = settings.portraitCols,
                        landscapeCols = settings.landscapeCols,
                        unfoldedCols = settings.unfoldedPortraitCols,
                        unfoldedLandscapeCols = settings.unfoldedLandscapeCols,
                        showSearchBar = settings.showAppsSearchBar,
                        searchBarPosition = settings.appsSearchBarPosition,
                        showSearchBarPlaceholder = settings.showAppsSearchBarPlaceholder,
                        showSearchBarSettings = settings.showAppsSearchBarSettings,
                        searchBarRadius = settings.appsSearchBarRadius,
                    )
                }
            }
        }

        viewModelScope.launch(Dispatchers.Main) {
            appsRepository.apps.collect { apps ->
                _state.update {
                    it.copy(
                        loading = it.loadingSettings,
                        loadingApps = false,
                        appShortcuts = apps
                    )
                }

                filterApps()
            }
        }
    }

    fun onAction(action: AppsScreenAction) {
        viewModelScope.launch(Dispatchers.Main) {
            when (action) {
                AppsScreenAction.NavigateToHome -> {}

                is AppsScreenAction.SetSearchText -> {
                    setSearchText(action.text)
                }

                AppsScreenAction.OpenFirstApp -> {
                    openFirstApp()
                }

                is AppsScreenAction.OpenApp -> {
                    openApp(action.packageName)
                }

                is AppsScreenAction.OpenAppInfo -> {
                    openAppInfo(action.packageName)
                }

                is AppsScreenAction.RequestUninstall -> {
                    requestUninstall(action.packageName)
                }

                AppsScreenAction.OpenSettingsDialog -> {
                    setShowSettingsDialog(true)
                }

                AppsScreenAction.CloseSettingsDialog -> {
                    setShowSettingsDialog(false)
                }

                AppsScreenAction.CloseKeyboard -> {}

                is AppsScreenAction.SetViewType -> {
                    settingsRepository.setAppsViewType(action.type)
                }

                is AppsScreenAction.SetPhoneCols -> {
                    settingsRepository.setPortraitCols(action.cols.toInt())
                }

                is AppsScreenAction.SetPhoneLandscapeCols -> {
                    settingsRepository.setLandscapeCols(action.cols.toInt())
                }

                is AppsScreenAction.SetUnfoldedCols -> {
                    settingsRepository.setUnfoldedCols(action.cols.toInt())
                }

                is AppsScreenAction.SetUnfoldedLandscapeCols -> {
                    settingsRepository.setUnfoldedLandscapeCols(action.cols.toInt())
                }

                is AppsScreenAction.SetSearchBarPosition -> {
                    settingsRepository.setAppsSearchBarPosition(action.position)
                }

                is AppsScreenAction.SetShowSearchBar -> {
                    settingsRepository.setShowAppsSearchBar(action.show)
                }

                is AppsScreenAction.SetShowSearchBarPlaceholder -> {
                    settingsRepository.setShowAppsSearchBarPlaceholder(action.show)
                }

                is AppsScreenAction.SetShowSearchBarSettings -> {
                    settingsRepository.setShowAppsSearchBarSettings(action.show)
                }

                is AppsScreenAction.SetSearchBarRadius -> {
                    settingsRepository.setAppsSearchBarRadius(action.radius.toInt())
                }
            }
        }
    }

    private fun setSearchText(text: String) {
        _state.update { it.copy(searchText = text) }
        filterApps()
    }

    private fun filterApps() {
        viewModelScope.launch(Dispatchers.IO) {
            val apps = appsRepository.getSearchedApps(state.value.searchText)
            _state.update { it.copy(appShortcuts = apps) }
        }
    }

    private fun openApp(packageName: String) {
        appsRepository.openApp(packageName)

        _state.update {
            it.copy(
                searchText = "",
                appShortcuts = appsRepository.apps.value
            )
        }
    }

    private fun openFirstApp() {
        if (state.value.appShortcuts.isNotEmpty()) {
            openApp(state.value.appShortcuts[0].packageName)
        }
    }

    private fun setShowSettingsDialog(show: Boolean) {
        _state.update { it.copy(showSettingsDialog = show) }
    }

    private fun openAppInfo(packageName: String) {
        appsRepository.openAppInfo(packageName)
    }

    private fun requestUninstall(packageName: String) {
        appsRepository.requestUninstall(packageName)
    }
}