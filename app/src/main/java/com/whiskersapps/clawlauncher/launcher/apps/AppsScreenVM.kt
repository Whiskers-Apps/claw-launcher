package com.whiskersapps.clawlauncher.launcher.apps

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whiskersapps.clawlauncher.foldable.FoldableRepo
import com.whiskersapps.clawlauncher.launcher.apps.di.AppsRepo
import com.whiskersapps.clawlauncher.settings.di.SettingsRepo
import com.whiskersapps.clawlauncher.shared.model.App
import com.whiskersapps.clawlauncher.shared.utils.requestFingerprint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppsScreenVM(
    private val settingsRepo: SettingsRepo,
    private val appsRepo: AppsRepo,
    private val foldableRepo: FoldableRepo
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
                        showSearchBar = settings.showAppsSearchBar,
                        searchBarPosition = settings.appsSearchBarPosition,
                        showSearchBarPlaceholder = settings.showAppsSearchBarPlaceholder,
                        searchBarRadius = settings.appsSearchBarRadius,
                        splitList = settings.splitListView,
                    )
                }
            }
        }

        viewModelScope.launch(Dispatchers.Main) {
            foldableRepo.grid.collect { grid ->
                _state.update {
                    it.copy(
                        portraitCols = grid.portrait,
                        landscapeCols = grid.landscape
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

            is AppsScreenAction.OpenShortcut -> openShortcut(action.packageName, action.shortcut)
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

    private fun clearSearch() {
        _state.update {
            it.copy(
                searchText = "",
                apps = appsRepo.apps.value
            )
        }
    }

}