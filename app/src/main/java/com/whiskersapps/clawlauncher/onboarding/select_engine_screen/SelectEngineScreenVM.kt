package com.whiskersapps.clawlauncher.onboarding.select_engine_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whiskersapps.clawlauncher.search_engines.SearchEnginesRepo
import com.whiskersapps.clawlauncher.settings.di.SettingsRepo
import com.whiskersapps.clawlauncher.shared.model.SearchEngine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SelectEngineScreenVM constructor(
    private val searchEnginesRepo: SearchEnginesRepo,
    private val settingsRepo: SettingsRepo
) : ViewModel() {

    private val _state = MutableStateFlow(SelectEngineScreenState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            searchEnginesRepo.data.collect { data ->
                _state.update {
                    it.copy(
                        loading = false,
                        searchEngines = data.searchEngines,
                        selectedEngine = data.defaultSearchEngine
                    )
                }
            }
        }
    }

    fun onAction(action: SelectEngineScreenAction) {
        when (action) {
            SelectEngineScreenAction.Finish -> finishSetup()
            SelectEngineScreenAction.NavigateBack -> {}
            is SelectEngineScreenAction.SetDefaultEngine -> setDefaultEngine(action.searchEngine)
        }
    }

    private fun finishSetup() {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepo.setSetupCompleted(true)
        }
    }

    private fun setDefaultEngine(searchEngine: SearchEngine) {

        _state.update { it.copy(selectedEngine = searchEngine) }

        viewModelScope.launch(Dispatchers.IO) {
            searchEnginesRepo.makeDefaultEngine(searchEngine._id)
        }
    }
}