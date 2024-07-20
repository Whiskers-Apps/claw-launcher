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
        when (action) {
            AppsScreenAction.NavigateToHome -> {}
            is AppsScreenAction.UpdateSearchText -> updateSearchText(action.text)
            AppsScreenAction.OpenFirstApp -> openFirstApp()
            is AppsScreenAction.OpenApp -> openApp(action.packageName)
            is AppsScreenAction.OpenAppInfo -> openAppInfo(action.packageName)
            is AppsScreenAction.RequestUninstall -> requestUninstall(action.packageName)
            AppsScreenAction.OpenSettingsDialog -> updateShowSettingsDialog(true)
            AppsScreenAction.CloseSettingsDialog -> updateShowSettingsDialog(false)
            AppsScreenAction.CloseKeyboard -> {}
            is AppsScreenAction.UpdateViewType -> updateViewType(action.type)
            is AppsScreenAction.UpdatePhoneCols -> updatePhoneCols(action.cols.toInt())
            is AppsScreenAction.UpdatePhoneLandscapeCols -> updatePhoneLandscapeCols(action.cols.toInt())
            is AppsScreenAction.UpdateBackgroundOpacity -> updateBackgroundOpacity(action.opacity)
            is AppsScreenAction.UpdateTabletCols -> updateTabletCols(action.cols.toInt())
            is AppsScreenAction.UpdateTabletLandscapeCols -> updateTabletLandscapeCols(action.cols.toInt())
            is AppsScreenAction.UpdateSearchBarPosition -> updateSearchBarPosition(action.position)
            is AppsScreenAction.UpdateShowSearchBar -> updateShowSearchBar(action.show)
            is AppsScreenAction.UpdateShowSearchBarPlaceholder -> updateShowSearchBarPlaceholder(action.show)
            is AppsScreenAction.UpdateShowSearchBarSettings -> updateShowSearchBarSettings(action.show)
            is AppsScreenAction.UpdateSearchBarOpacity -> updateSearchBarOpacity(action.opacity)
            is AppsScreenAction.UpdateSearchBarRadius -> updateSearchBarRadius(action.radius.toInt())
        }
    }

    private fun updateShowSearchBarSettings(show: Boolean) {
        viewModelScope.launch {
            settingsRepository.updateShowAppsSearchBarSettings(show)
        }
    }

    private fun updateSearchText(text: String) {
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

    private fun updateShowSettingsDialog(show: Boolean) {
        _uiState.update { it?.copy(showSettingsDialog = show) }
    }

    private fun updateBackgroundOpacity(opacity: Float) {
        viewModelScope.launch(Dispatchers.Main) {
            settingsRepository.updateAppsOpacity(opacity)
        }
    }

    private fun updatePhoneCols(cols: Int) {
        viewModelScope.launch {
            settingsRepository.updatePhoneCols(cols)
        }
    }

    private fun updatePhoneLandscapeCols(cols: Int) {
        viewModelScope.launch {
            settingsRepository.updatePhoneLandscapeCols(cols)
        }
    }

    private fun updateTabletCols(cols: Int) {
        viewModelScope.launch {
            settingsRepository.updateTabletCols(cols)
        }
    }

    private fun updateTabletLandscapeCols(cols: Int) {
        viewModelScope.launch {
            settingsRepository.updateTabletLandscapeCols(cols)
        }
    }

    private fun updateSearchBarPosition(position: String){
        viewModelScope.launch {
            settingsRepository.updateAppsSearchBarPosition(position)
        }
    }

    private fun updateShowSearchBarPlaceholder(show: Boolean) {
        viewModelScope.launch {
            settingsRepository.updateShowAppsSearchBarPlaceholder(show)
        }
    }

    private fun updateViewType(viewType: String) {
        viewModelScope.launch {
            settingsRepository.updateAppsViewType(viewType)
        }
    }

    private fun updateSearchBarOpacity(it: Float) {
        viewModelScope.launch {
            settingsRepository.updateAppsSearchBarOpacity(it)
        }
    }

    private fun updateSearchBarRadius(radius: Int){
        viewModelScope.launch {
            settingsRepository.updateAppsSearchBarRadius(radius)
        }
    }

    private fun updateShowSearchBar(show: Boolean) {
        viewModelScope.launch {
            settingsRepository.updateShowAppsSearchBar(show)
        }
    }

    private fun openAppInfo(packageName: String) {
        appsRepository.openAppInfo(packageName)
    }

    private fun requestUninstall(packageName: String) {
        appsRepository.requestUninstall(packageName)
    }
}