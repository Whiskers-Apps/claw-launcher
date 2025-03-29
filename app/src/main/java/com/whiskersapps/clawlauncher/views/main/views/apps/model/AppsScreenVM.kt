package com.whiskersapps.clawlauncher.views.main.views.apps.model

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whiskersapps.clawlauncher.launcher.apps.AppsRepo
import com.whiskersapps.clawlauncher.settings.SettingsRepo
import com.whiskersapps.clawlauncher.shared.model.App
import com.whiskersapps.clawlauncher.shared.utils.requestFingerprint
import com.whiskersapps.clawlauncher.views.main.views.apps.intent.AppsScreenAction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppsScreenVM(
    private val settingsRepo: SettingsRepo,
    private val appsRepo: AppsRepo
) : ViewModel() {

    private val _state = MutableStateFlow(AppsScreenState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepo.settings.collect { settings ->
                _state.update {
                    it.copy(
                        loadingSettings = false,
                        loading = it.loadingApps,
                        securedApps = settings.secureApps,
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
                        splitList = settings.splitListView,
                    )
                }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
//            settingsRepo.gridColsCount.collect { gridColsCount ->
//                _state.update { it.copy(gridColsCount = gridColsCount) }
//            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            appsRepo.apps.collect {
                _state.update {
                    it.copy(
                        loading = it.loadingSettings,
                        loadingApps = false,
                        apps = appsRepo.getSearchedApps(state.value.searchText)
                    )
                }
            }
        }
    }

    fun onAction(action: AppsScreenAction) {
        when (action) {
            AppsScreenAction.NavigateToHome -> {}

            is AppsScreenAction.SetSearchText -> setSearchText(action.text)

            is AppsScreenAction.OpenFirstApp -> openFirstApp(action.fragmentActivity)

            is AppsScreenAction.OpenApp -> openApp(action.packageName, action.fragmentActivity)

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

            is AppsScreenAction.OpenShortcut -> openShortcut(action.packageName, action.shortcut)

            is AppsScreenAction.SetDisableAppsScreen -> setDisableAppsScreen(action.disable)

            is AppsScreenAction.SetSplitList -> setSplitList(action.split)
        }
    }

    private fun setSearchText(text: String) {
        _state.update {
            it.copy(searchText = text)
        }

        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(apps = appsRepo.getSearchedApps(state.value.searchText))
            }
        }
    }

    private fun openApp(packageName: String, fragmentActivity: FragmentActivity) {

        if (state.value.securedApps.contains(packageName)) {
            requestFingerprint(
                fragmentActivity = fragmentActivity,
                title = "Open App",
                message = "Unlock to open the app",
                onSuccess = {
                    appsRepo.openApp(packageName)
                }
            )
        } else {
            appsRepo.openApp(packageName)
        }

        clearSearch()
    }

    private fun openShortcut(packageName: String, shortcut: App.Shortcut) {
        viewModelScope.launch(Dispatchers.IO) {
            appsRepo.openShortcut(packageName, shortcut)
            clearSearch()
        }
    }

    private fun openFirstApp(fragmentActivity: FragmentActivity) {
        if (state.value.apps.isNotEmpty()) {
            openApp(state.value.apps[0].packageName, fragmentActivity)
        }
    }

    private fun setShowSettingsDialog(show: Boolean) {
        _state.update { it.copy(showSettingsDialog = show) }
    }

    private fun openAppInfo(packageName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            appsRepo.openAppInfo(packageName)
            clearSearch()
        }
    }

    private fun requestUninstall(packageName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            appsRepo.requestUninstall(packageName)
            clearSearch()
        }
    }

    private fun setViewType(type: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(viewType = type) }
            settingsRepo.setAppsViewType(type)
        }
    }

    private fun setCols(cols: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(cols = cols) }
            settingsRepo.setPortraitCols(cols)
        }
    }

    private fun setLandscapeCols(cols: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(landscapeCols = cols) }
            settingsRepo.setLandscapeCols(cols)
        }
    }

    private fun setUnfoldedCols(cols: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(unfoldedCols = cols) }
            settingsRepo.setUnfoldedCols(cols)
        }
    }

    private fun setUnfoldedLandscapeCols(cols: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(unfoldedLandscapeCols = cols) }
            settingsRepo.setUnfoldedLandscapeCols(cols)
        }
    }

    private fun setSearchBarPosition(position: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(searchBarPosition = position) }
            settingsRepo.setAppsSearchBarPosition(position)
        }
    }

    private fun setShowSearchBar(show: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(showSearchBar = show) }
            settingsRepo.setShowAppsSearchBar(show)
        }
    }

    private fun setShowSearchBarPlaceholder(show: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(showSearchBarPlaceholder = show) }
            settingsRepo.setShowAppsSearchBarPlaceholder(show)
        }
    }

    private fun setShowSearchBarSettings(show: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(showSearchBarSettings = show) }
            settingsRepo.setShowAppsSearchBarSettings(show)
        }
    }

    private fun setSearchBarRadius(radius: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(searchBarRadius = radius) }
            settingsRepo.setAppsSearchBarRadius(radius)
        }
    }

    private fun clearSearch() {
        _state.update {
            it.copy(
                searchText = "",
                apps = appsRepo.apps.value
            )
        }
    }

    private fun setDisableAppsScreen(disable: Boolean) {
        setShowSettingsDialog(false)

        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(disableAppsScreen = disable) }
            settingsRepo.setDisableAppsScreen(disable)
        }
    }

    private fun setSplitList(split: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(splitList = split)
            }
            settingsRepo.setSplitList(split)
        }
    }
}