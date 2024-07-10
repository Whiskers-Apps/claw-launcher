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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AppsScreenVM @Inject constructor(
    settingsRepository: SettingsRepository,
    private val appsRepository: AppsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<AppsScreenUiState?>(null)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.Main) {

            settingsRepository.settingsFlow.collect { settings ->
                _uiState.update {
                    AppsScreenUiState(
                        appShortcuts = appsRepository.apps.value,
                        layout = settings.layout,
                        viewType = "list",
                        phoneCols = 4,
                        phoneRows = 5,
                        phoneLandscapeCols = 6,
                        phoneLandscapeRows = 3,
                        tabletCols = 6,
                        tabletRows = 4,
                        tabletLandscapeCols = 6,
                        tabletLandscapeRows = 3,
                        showSearchBar = true,
                        searchBarPosition = "bottom",
                        blurRadius = 14.dp,
                        searchText = ""
                    )
                }
            }
        }

        viewModelScope.launch(Dispatchers.Main){
            appsRepository.apps.collect{ apps ->
                _uiState.update { it?.copy(appShortcuts = apps) }
                println("squirtle")
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
        uiState.value!!.appShortcuts.isNotEmpty().let {
            openApp(uiState.value!!.appShortcuts[0].packageName)
        }
    }
}