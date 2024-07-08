package com.lighttigerxiv.clawlauncher.features.home.viewmodel

import androidx.lifecycle.ViewModel
import com.lighttigerxiv.clawlauncher.shared.data.AppsRepository
import com.lighttigerxiv.clawlauncher.shared.model.AppShortcut
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class HomeScreenUIState(
    val appShortcuts: List<AppShortcut>
)

@HiltViewModel
class HomeScreenVM @Inject constructor(
    private val appsRepository: AppsRepository
): ViewModel() {

    private val _uiState = MutableStateFlow<HomeScreenUIState?>(null)
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update { HomeScreenUIState(
            appShortcuts = appsRepository.apps.value
        ) }
    }

    fun openApp(packageName: String){
        appsRepository.openApp(packageName)
    }
}