package com.whiskersapps.clawlauncher.views.main.views.settings.views.search_engines.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whiskersapps.clawlauncher.shared.data.SearchEnginesRepository
import com.whiskersapps.clawlauncher.shared.data.SettingsRepository
import com.whiskersapps.clawlauncher.shared.model.SearchEngine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

@HiltViewModel
class SearchEnginesScreenVM @Inject constructor(
    private val searchEnginesRepository: SearchEnginesRepository
) : ViewModel() {

    companion object {
        data class UiState(
            val loading: Boolean = true,
            val searchEngines: List<SearchEngine> = emptyList(),
            val defaultSearchEngineId: ObjectId? = ObjectId(),
            val defaultSearchEngine: SearchEngine? = null,
            val addEngineDialog: AddEngineDialog = AddEngineDialog(),
            val editEngineDialog: EditEngineDialog = EditEngineDialog()
        ) {
            data class AddEngineDialog(
                val show: Boolean = false,
                val name: String = "",
                val query: String = ""
            )

            data class EditEngineDialog(
                val id: ObjectId = ObjectId(),
                val show: Boolean = false,
                val name: String = "",
                val query: String = "",
                val defaultEngine: Boolean = false
            )
        }
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {

        viewModelScope.launch(Dispatchers.Main) {
            searchEnginesRepository.data.collect { data ->
                _uiState.update {
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

    fun updateShowAddSearchEngineDialog(show: Boolean) {
        val dialog = UiState.AddEngineDialog(
            show = show,
            name = "",
            query = ""
        )

        _uiState.update { it.copy(addEngineDialog = dialog) }
    }

    fun updateAddSearchEngineName(text: String) {
        _uiState.update { it.copy(addEngineDialog = it.addEngineDialog.copy(name = text)) }
    }

    fun updateAddSearchEngineQuery(text: String) {
        _uiState.update { it.copy(addEngineDialog = it.addEngineDialog.copy(query = text)) }
    }

    fun addSearchEngine() {
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

            _uiState.update { it.copy(addEngineDialog = it.addEngineDialog.copy(show = false)) }
        }
    }

    fun showEditDialog(searchEngine: SearchEngine) {
        _uiState.update {
            it.copy(
                editEngineDialog = UiState.EditEngineDialog(
                    id = searchEngine._id,
                    show = true,
                    name = searchEngine.name,
                    query = searchEngine.query,
                    defaultEngine = searchEngine._id == uiState.value.defaultSearchEngineId
                )
            )
        }
    }

    fun closeEditDialog() {
        _uiState.update {
            it.copy(
                editEngineDialog = UiState.EditEngineDialog()
            )
        }
    }

    fun updateEditSearchEngineName(name: String) {
        _uiState.update { it.copy(editEngineDialog = it.editEngineDialog.copy(name = name)) }
    }

    fun updateEditSearchEngineQuery(name: String) {
        _uiState.update { it.copy(editEngineDialog = it.editEngineDialog.copy(query = name)) }
    }

    fun updateEditSearchEngineDefault(default: Boolean) {
        _uiState.update { it.copy(editEngineDialog = it.editEngineDialog.copy(defaultEngine = default)) }
    }

    fun saveEditSearchEngine() {
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

    fun deleteSearchEngine() {
        viewModelScope.launch(Dispatchers.IO){
            searchEnginesRepository.deleteSearchEngine(uiState.value.editEngineDialog.id)

            closeEditDialog()
        }
    }
}
