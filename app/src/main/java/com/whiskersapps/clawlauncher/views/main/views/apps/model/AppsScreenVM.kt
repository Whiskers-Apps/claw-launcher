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
        viewModelScope.launch(Dispatchers.IO) {
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

        viewModelScope.launch(Dispatchers.IO) {
            appsRepository.apps.collect { apps ->
                _state.update {
                    it.copy(
                        loading = it.loadingSettings,
                        loadingApps = false,
                        appShortcuts = apps
                    )
                }

                _state.update {
                    it.copy(
                        appShortcuts = appsRepository.getSearchedApps(state.value.searchText)
                    )
                }
            }
        }
    }

    fun onAction(action: AppsScreenAction) {
        when (action) {
            AppsScreenAction.NavigateToHome -> {}

            is AppsScreenAction.SetSearchText -> setSearchText(action.text)

            AppsScreenAction.OpenFirstApp -> openFirstApp()

            is AppsScreenAction.OpenApp -> openApp(action.packageName)

            is AppsScreenAction.OpenAppInfo -> openAppInfo(action.packageName)

            is AppsScreenAction.RequestUninstall -> requestUninstall(action.packageName)

            AppsScreenAction.OpenSettingsDialog -> setShowSettingsDialog(true)

            AppsScreenAction.CloseSettingsDialog -> setShowSettingsDialog(false)

            AppsScreenAction.CloseKeyboard -> {}

            is AppsScreenAction.SetViewType -> setViewType(action.type)

            is AppsScreenAction.SetCols -> setCols(action.cols.toInt())

            is AppsScreenAction.SetLandscapeCols -> setLandscapeCols(action.cols.toInt())

            is AppsScreenAction.SetUnfoldedCols -> setUnfoldedCols(action.cols.toInt())

            is AppsScreenAction.SetUnfoldedLandscapeCols -> setUnfoldedLandscapeCols(action.cols.toInt())

            is AppsScreenAction.SetSearchBarPosition -> setSearchBarPosition(action.position)

            is AppsScreenAction.SetShowSearchBar -> setShowSearchBar(action.show)

            is AppsScreenAction.SetShowSearchBarPlaceholder -> setShowSearchBarPlaceholder(
                action.show
            )

            is AppsScreenAction.SetShowSearchBarSettings -> setShowSearchBarSettings(action.show)

            is AppsScreenAction.SetSearchBarRadius -> setSearchBarRadius(action.radius.toInt())
        }
    }

    private fun setSearchText(text: String) {
        _state.update {
            it.copy(searchText = text)
        }

        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(appShortcuts = appsRepository.getSearchedApps(state.value.searchText))
            }
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

    private fun setViewType(type: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(viewType = type) }
            settingsRepository.setAppsViewType(type)
        }
    }

    private fun setCols(cols: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(cols = cols) }
            settingsRepository.setPortraitCols(cols)
        }
    }

    private fun setLandscapeCols(cols: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(landscapeCols = cols) }
            settingsRepository.setLandscapeCols(cols)
        }
    }

    private fun setUnfoldedCols(cols: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(unfoldedCols = cols) }
            settingsRepository.setUnfoldedCols(cols)
        }
    }

    private fun setUnfoldedLandscapeCols(cols: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(unfoldedLandscapeCols = cols) }
            settingsRepository.setUnfoldedLandscapeCols(cols)
        }
    }

    private fun setSearchBarPosition(position: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(searchBarPosition = position) }
            settingsRepository.setAppsSearchBarPosition(position)
        }
    }

    private fun setShowSearchBar(show: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(showSearchBar = show) }
            settingsRepository.setShowAppsSearchBar(show)
        }
    }

    private fun setShowSearchBarPlaceholder(show: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(showSearchBarPlaceholder = show) }
            settingsRepository.setShowAppsSearchBarPlaceholder(show)
        }
    }

    private fun setShowSearchBarSettings(show: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(showSearchBarSettings = show) }
            settingsRepository.setShowAppsSearchBarSettings(show)
        }
    }

    private fun setSearchBarRadius(radius: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(searchBarRadius = radius) }
            settingsRepository.setAppsSearchBarRadius(radius)
        }
    }
}