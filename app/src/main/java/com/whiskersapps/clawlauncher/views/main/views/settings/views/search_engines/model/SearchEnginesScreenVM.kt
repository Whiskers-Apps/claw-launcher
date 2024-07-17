package com.whiskersapps.clawlauncher.views.main.views.settings.views.search_engines.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whiskersapps.clawlauncher.shared.data.SearchEnginesRepository
import com.whiskersapps.clawlauncher.shared.model.SearchEngine
import com.whiskersapps.clawlauncher.views.main.views.settings.views.search_engines.intent.SearchEnginesScreenAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchEnginesScreenVM @Inject constructor(
    private val searchEnginesRepository: SearchEnginesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SearchEnginesScreenState())
    val uiState = _state.asStateFlow()

    init {

        viewModelScope.launch(Dispatchers.Main) {
            searchEnginesRepository.data.collect { data ->
                _state.update {
                    it.copy(
                        loading = false,
                        searchEngines = data.searchEngines,
                        defaultSearchEngine = data.defaultSearchEngine,
                        defaultSearchEngineId = data.defaultSearchEngine?._id
                    )
                }
            }
        }
    }

    fun onAction(action: SearchEnginesScreenAction) {
        when (action) {
            SearchEnginesScreenAction.NavigateBack -> {}
            SearchEnginesScreenAction.CloseAddEngineDialog -> closeAddEngineDialog()
            SearchEnginesScreenAction.ShowAddEngineDialog -> showAddEngineDialog()
            is SearchEnginesScreenAction.UpdateAddEngineDialogFields -> updateAddEngineDialogFields(
                action.name, action.query
            )

            SearchEnginesScreenAction.AddEngine -> addEngine()
            is SearchEnginesScreenAction.ShowEditEngineDialog -> showEditEngineDialog(action.engine)
            SearchEnginesScreenAction.CloseEditEngineDialog -> closeEditDialog()
            SearchEnginesScreenAction.DeleteEngine -> deleteEngine()
            SearchEnginesScreenAction.SaveEditEngine -> saveEditEngine()
            is SearchEnginesScreenAction.UpdateEditEngineDialogFields -> updateEditEngineDialogFields(
                action.name,
                action.query,
                action.default
            )
        }
    }

    private fun closeAddEngineDialog() {
        _state.update { it.copy(addEngineDialog = SearchEnginesScreenState.AddEngineDialog()) }
    }

    private fun showAddEngineDialog() {
        _state.update { it.copy(addEngineDialog = it.addEngineDialog.copy(show = true)) }
    }

    private fun updateAddEngineDialogFields(name: String, query: String) {
        _state.update {
            it.copy(
                addEngineDialog = it.addEngineDialog.copy(name = name, query = query)
            )
        }
    }

    private fun addEngine() {
        viewModelScope.launch(Dispatchers.IO) {
            val searchEngine = SearchEngine().apply {
                name = uiState.value.addEngineDialog.name
                query = uiState.value.addEngineDialog.query
            }

            searchEnginesRepository.addSearchEngine(searchEngine)

            if (uiState.value.editEngineDialog.defaultEngine) {
                searchEnginesRepository.makeDefaultEngine(searchEngine._id)
            } else {
                searchEnginesRepository.clearDefaultEngine()
            }

            _state.update { it.copy(addEngineDialog = it.addEngineDialog.copy(show = false)) }
        }
    }

    private fun showEditEngineDialog(searchEngine: SearchEngine) {
        _state.update {
            it.copy(
                editEngineDialog = SearchEnginesScreenState.EditEngineDialog(
                    id = searchEngine._id,
                    show = true,
                    name = searchEngine.name,
                    query = searchEngine.query,
                    defaultEngine = searchEngine._id == uiState.value.defaultSearchEngineId
                )
            )
        }
    }

    private fun closeEditDialog() {
        _state.update {
            it.copy(
                editEngineDialog = SearchEnginesScreenState.EditEngineDialog()
            )
        }
    }

    private fun updateEditEngineDialogFields(name: String, query: String, default: Boolean) {
        _state.update {
            it.copy(
                editEngineDialog = it.editEngineDialog.copy(
                    name = name,
                    query = query,
                    defaultEngine = default
                )
            )
        }
    }

    private fun saveEditEngine() {
        viewModelScope.launch(Dispatchers.IO) {

            val defaultEngineId = uiState.value.defaultSearchEngineId
            val engineId = uiState.value.editEngineDialog.id
            val editIsDefault = uiState.value.editEngineDialog.defaultEngine

            if (editIsDefault && defaultEngineId != engineId) {
                searchEnginesRepository.makeDefaultEngine(engineId)
            }

            if (!editIsDefault && defaultEngineId == engineId) {
                searchEnginesRepository.clearDefaultEngine()
            }

            searchEnginesRepository.updateSearchEngine(
                uiState.value.editEngineDialog.id,
                uiState.value.editEngineDialog.name,
                uiState.value.editEngineDialog.query
            )

            closeEditDialog()
        }
    }

    private fun deleteEngine() {
        viewModelScope.launch(Dispatchers.IO) {
            searchEnginesRepository.deleteSearchEngine(uiState.value.editEngineDialog.id)

            closeEditDialog()
        }
    }
}
