package com.whiskersapps.clawlauncher.views.main.views.apps.viewmodel

import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whiskersapps.clawlauncher.shared.data.AppsRepository
import com.whiskersapps.clawlauncher.shared.data.SettingsRepository
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

    private val _uiState = MutableStateFlow<AppsScreenUiState?>(null)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.Main) {
            settingsRepository.settingsFlow.collect { settings ->

                if (uiState.value == null) {
                    _uiState.update {
                        AppsScreenUiState(
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
                            searchBarOpacity = settings.appsSearchBarOpacity,
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
                            searchBarOpacity = settings.appsSearchBarOpacity
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

    fun updateSearchText(text: String) {
        _uiState.update {
            it?.copy(
                searchText = text,
                appShortcuts = appsRepository.getSearchedApps(text)
            )
        }
    }

    fun openApp(packageName: String) {
        appsRepository.openApp(packageName)

        _uiState.update {
            it?.copy(
                searchText = "",
                appShortcuts = appsRepository.apps.value
            )
        }
    }

    fun openFirstApp() {
        if(uiState.value!!.appShortcuts.isNotEmpty()){
            openApp(uiState.value!!.appShortcuts[0].packageName)
        }
    }

    fun updateShowSettingsDialog(show: Boolean) {
        _uiState.update { it?.copy(showSettingsDialog = show) }
    }

    fun updateOpacity(opacity: Float) {
        viewModelScope.launch(Dispatchers.Main) {
            settingsRepository.updateAppsOpacity(opacity)
        }
    }

    fun updatePhoneCols(cols: Int) {
        viewModelScope.launch {
            settingsRepository.updatePhoneCols(cols)
        }
    }

    fun updatePhoneLandscapeCols(cols: Int) {
        viewModelScope.launch {
            settingsRepository.updatePhoneLandscapeCols(cols)
        }
    }

    fun updateTabletCols(cols: Int) {
        viewModelScope.launch {
            settingsRepository.updateTabletCols(cols)
        }
    }

    fun updateTabletLandscapeCols(cols: Int) {
        viewModelScope.launch {
            settingsRepository.updateTabletLandscapeCols(cols)
        }
    }

    fun updateViewType(viewType: String) {
        viewModelScope.launch {
            settingsRepository.updateAppsViewType(viewType)
        }
    }

    fun updateIconPadding(padding: Int) {
        viewModelScope.launch {
            settingsRepository.updateIconPadding(padding)
        }
    }

    fun updateSearchBarOpacity(it: Float) {
        viewModelScope.launch {
            settingsRepository.updateAppsSearchBarOpacity(it)
        }
    }

    fun updateShowSearchBar(show: Boolean) {
        viewModelScope.launch {
            settingsRepository.updateShowAppsSearchBar(show)
        }
    }

    fun openAppInfo(packageName: String) {
        appsRepository.openAppInfo(packageName)
    }

    fun requestUninstall(packageName: String) {
        appsRepository.requestUninstall(packageName)
    }
}