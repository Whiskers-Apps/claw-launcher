package com.whiskersapps.clawlauncher.views.main.views.apps.model

import androidx.compose.ui.unit.dp
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

    private val _uiState = MutableStateFlow<AppsScreenState?>(null)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.Main) {
            settingsRepository.settingsFlow.collect { settings ->

                if (uiState.value == null) {
                    _uiState.update {
                        AppsScreenState(
                            appShortcuts = appsRepository.apps.value,
                            layout = settings.layout,
                            iconPadding = (settings.iconPadding).dp,
                            viewType = settings.appsViewType,
                            opacity = settings.appsOpacity,
                            phoneCols = settings.phoneCols,
                            phoneLandscapeCols = settings.phoneLandscapeCols,
                            tabletCols = settings.tabletCols,
                            tabletLandscapeCols = settings.tabletLandscapeCols,
                            showSearchBar = settings.showAppsSearchBar,
                            searchBarPosition = settings.appsSearchBarPosition,
                            showSearchBarPlaceholder = settings.showAppsSearchBarPlaceholder,
                            showSearchBarSettings = settings.showAppsSearchBarSettings,
                            searchBarOpacity = settings.appsSearchBarOpacity,
                            searchBarRadius = if (settings.appsSearchBarRadius != -1) settings.appsSearchBarRadius.dp else null,
                            searchText = "",
                            showSettingsDialog = false
                        )
                    }
                } else {
                    _uiState.update {
                        it?.copy(
                            layout = settings.layout,
                            viewType = settings.appsViewType,
                            iconPadding = (settings.iconPadding).dp,
                            opacity = settings.appsOpacity,
                            phoneCols = settings.phoneCols,
                            phoneLandscapeCols = settings.phoneLandscapeCols,
                            tabletCols = settings.tabletCols,
                            tabletLandscapeCols = settings.tabletLandscapeCols,
                            showSearchBar = settings.showAppsSearchBar,
                            searchBarPosition = settings.appsSearchBarPosition,
                            showSearchBarPlaceholder = settings.showAppsSearchBarPlaceholder,
                            showSearchBarSettings = settings.showAppsSearchBarSettings,
                            searchBarOpacity = settings.appsSearchBarOpacity,
                            searchBarRadius = if (settings.appsSearchBarRadius != -1) settings.appsSearchBarRadius.dp else null,
                        )
                    }
                }
            }
        }

        viewModelScope.launch(Dispatchers.Main) {
            appsRepository.apps.collect { apps ->
                _uiState.update { it?.copy(appShortcuts = apps) }
            }
        }
    }

    fun onAction(action: AppsScreenAction) {
        viewModelScope.launch(Dispatchers.Main) {
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
                is AppsScreenAction.SetPhoneCols -> setPhoneCols(action.cols.toInt())
                is AppsScreenAction.SetPhoneLandscapeCols -> setPhoneLandscapeCols(action.cols.toInt())
                is AppsScreenAction.SetBackgroundOpacity -> setBackgroundOpacity(action.opacity)
                is AppsScreenAction.SetTabletCols -> setTabletCols(action.cols.toInt())
                is AppsScreenAction.SetTabletLandscapeCols -> setTabletLandscapeCols(action.cols.toInt())
                is AppsScreenAction.SetSearchBarPosition -> setSearchBarPosition(action.position)
                is AppsScreenAction.SetShowSearchBar -> setShowSearchBar(action.show)
                is AppsScreenAction.SetShowSearchBarPlaceholder -> setShowSearchBarPlaceholder(
                    action.show
                )

                is AppsScreenAction.SetShowSearchBarSettings -> setShowSearchBarSettings(action.show)
                is AppsScreenAction.SetSearchBarOpacity -> setSearchBarOpacity(action.opacity)
                is AppsScreenAction.SetSearchBarRadius -> setSearchBarRadius(action.radius.toInt())
            }
        }
    }

    private fun setShowSearchBarSettings(show: Boolean) {
        viewModelScope.launch {
            settingsRepository.setShowAppsSearchBarSettings(show)
        }
    }

    private fun setSearchText(text: String) {
        _uiState.update {
            it?.copy(
                searchText = text,
                appShortcuts = appsRepository.getSearchedApps(text)
            )
        }
    }

    private fun openApp(packageName: String) {
        appsRepository.openApp(packageName)

        _uiState.update {
            it?.copy(
                searchText = "",
                appShortcuts = appsRepository.apps.value
            )
        }
    }

    private fun openFirstApp() {
        if (uiState.value!!.appShortcuts.isNotEmpty()) {
            openApp(uiState.value!!.appShortcuts[0].packageName)
        }
    }

    private fun setShowSettingsDialog(show: Boolean) {
        _uiState.update { it?.copy(showSettingsDialog = show) }
    }

    private fun setBackgroundOpacity(opacity: Float) {
        viewModelScope.launch(Dispatchers.Main) {
            settingsRepository.setAppsOpacity(opacity)
        }
    }

    private fun setPhoneCols(cols: Int) {
        viewModelScope.launch {
            settingsRepository.setPhoneCols(cols)
        }
    }

    private fun setPhoneLandscapeCols(cols: Int) {
        viewModelScope.launch {
            settingsRepository.setPhoneLandscapeCols(cols)
        }
    }

    private fun setTabletCols(cols: Int) {
        viewModelScope.launch {
            settingsRepository.setTabletCols(cols)
        }
    }

    private fun setTabletLandscapeCols(cols: Int) {
        viewModelScope.launch {
            settingsRepository.setTabletLandscapeCols(cols)
        }
    }

    private fun setSearchBarPosition(position: String) {
        viewModelScope.launch {
            settingsRepository.setAppsSearchBarPosition(position)
        }
    }

    private fun setShowSearchBarPlaceholder(show: Boolean) {
        viewModelScope.launch {
            settingsRepository.setShowAppsSearchBarPlaceholder(show)
        }
    }

    private fun setViewType(viewType: String) {
        viewModelScope.launch {
            settingsRepository.setAppsViewType(viewType)
        }
    }

    private fun setSearchBarOpacity(it: Float) {
        viewModelScope.launch {
            settingsRepository.setAppsSearchBarOpacity(it)
        }
    }

    private fun setSearchBarRadius(radius: Int) {
        viewModelScope.launch {
            settingsRepository.setAppsSearchBarRadius(radius)
        }
    }

    private fun setShowSearchBar(show: Boolean) {
        viewModelScope.launch {
            settingsRepository.setShowAppsSearchBar(show)
        }
    }

    private fun openAppInfo(packageName: String) {
        appsRepository.openAppInfo(packageName)
    }

    private fun requestUninstall(packageName: String) {
        appsRepository.requestUninstall(packageName)
    }
}