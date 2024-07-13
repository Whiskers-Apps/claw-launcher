package com.whiskersapps.clawlauncher.views.main.views.search.viewmodel

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
class SearchScreenVM @Inject constructor(
    settingsRepository: SettingsRepository,
    private val appsRepository: AppsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<SearchScreenUiState?>(null)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.Main) {
            settingsRepository.settingsFlow.collect { settings ->
                _uiState.update {
                    SearchScreenUiState(
                        searchText = "",
                        appShortcuts = ArrayList(),
                        focusSearchBar = false
                    )
                }
            }
        }
    }

    fun updateSearchText(text: String) {

        val newApps = if (text.isEmpty()) ArrayList() else appsRepository.getSearchedApps(text)

        _uiState.update {
            it?.copy(
                searchText = text,
                appShortcuts = if (newApps.size >= 8) newApps.subList(0, 8) else newApps
            )
        }
    }

    fun openApp(packageName: String) {
        appsRepository.openApp(packageName)

        _uiState.update {
            it?.copy(
                searchText = "",
                appShortcuts = ArrayList()
            )
        }
    }

    fun openFirstApp() {

        if(uiState.value!!.appShortcuts.isNotEmpty()){
            openApp(uiState.value!!.appShortcuts[0].packageName)
        }

        // if bookmarks

        // if search engines
    }

    fun updateFocusSearchBar(focus: Boolean) {
        _uiState.update { it?.copy(focusSearchBar = focus) }
    }


    fun openAppInfo(packageName: String) {
        appsRepository.openAppInfo(packageName)
    }

    fun requestUninstall(packageName: String) {
        appsRepository.requestUninstall(packageName)
    }
}