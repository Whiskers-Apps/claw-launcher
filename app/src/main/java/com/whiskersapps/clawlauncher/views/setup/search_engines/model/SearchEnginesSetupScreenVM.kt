package com.whiskersapps.clawlauncher.views.setup.search_engines.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whiskersapps.clawlauncher.shared.data.SearchEnginesRepository
import com.whiskersapps.clawlauncher.shared.data.SettingsRepository
import com.whiskersapps.clawlauncher.shared.model.SearchEngine
import com.whiskersapps.clawlauncher.views.setup.search_engines.intent.SearchEnginesSetupScreenAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchEnginesSetupScreenVM @Inject constructor(
    private val searchEnginesRepository: SearchEnginesRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SearchEnginesSetupScreenState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            searchEnginesRepository.data.collect { data ->
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

    fun onAction(action: SearchEnginesSetupScreenAction) {
        when (action) {
            SearchEnginesSetupScreenAction.Finish -> finishSetup()
            SearchEnginesSetupScreenAction.NavigateBack -> {}
            is SearchEnginesSetupScreenAction.SetDefaultEngine -> setDefaultEngine(action.searchEngine)
        }
    }

    private fun finishSetup(){
        viewModelScope.launch(Dispatchers.IO){
            settingsRepository.setSetupCompleted(true)
        }
    }

    private fun setDefaultEngine(searchEngine: SearchEngine){

        _state.update { it.copy(selectedEngine = searchEngine) }

        viewModelScope.launch(Dispatchers.IO){
            searchEnginesRepository.makeDefaultEngine(searchEngine._id)
        }
    }
}