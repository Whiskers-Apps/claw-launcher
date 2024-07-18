package com.whiskersapps.clawlauncher.views.setup.layout.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.whiskersapps.clawlauncher.shared.data.BookmarksRepository
import com.whiskersapps.clawlauncher.shared.data.SearchEnginesRepository
import com.whiskersapps.clawlauncher.shared.data.SettingsRepository
import com.whiskersapps.clawlauncher.shared.model.Routes
import com.whiskersapps.clawlauncher.views.setup.layout.intent.LayoutScreenAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LayoutScreenVM @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val searchEnginesRepository: SearchEnginesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<LayoutScreenUiState?>(null)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.Main) {
            settingsRepository.settingsFlow.collect { settings ->
                _uiState.update { LayoutScreenUiState(layout = settings.layout) }
            }
        }
    }

    fun onAction(action: LayoutScreenAction) {
        when(action){
            LayoutScreenAction.NavigateBack -> {}
            is LayoutScreenAction.Finish -> {}
            LayoutScreenAction.SetMinimalLayout -> updateLayout("minimal")
            LayoutScreenAction.SetBubblyLayout -> updateLayout("bubbly")
        }
    }

    private fun updateLayout(layout: String) {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.updateLayout(layout)
        }
    }

    fun finishSetup(navController: NavController) {
        viewModelScope.launch(Dispatchers.IO){
            searchEnginesRepository.initEngines()
            settingsRepository.updateSetupCompleted(true)

            navController.navigate(Routes.Main.ROUTE) {
                popUpTo(Routes.Main.ROUTE) {
                    inclusive = true
                }
            }
        }
    }
}