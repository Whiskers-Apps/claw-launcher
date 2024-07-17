package com.whiskersapps.clawlauncher.views.main.views.settings.viewmodel

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whiskersapps.clawlauncher.shared.data.SearchEnginesRepository
import com.whiskersapps.clawlauncher.shared.data.SettingsRepository
import com.whiskersapps.clawlauncher.shared.model.Bookmark
import com.whiskersapps.clawlauncher.shared.model.BookmarkGroup
import com.whiskersapps.clawlauncher.shared.model.SearchEngine
import com.whiskersapps.clawlauncher.shared.model.Settings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsScreenVM @Inject constructor(
    private val app: Application,
    private val settingsRepository: SettingsRepository,
) : ViewModel() {

    companion object {
        data class UiState(
            val loading: Boolean = true,
            val settings: Settings = Settings(),
            val searchEngines: List<SearchEngine> = emptyList(),
            val bookmarks: List<Bookmark> = emptyList(),
            val bookmarkGroups: List<BookmarkGroup> = emptyList()
        )
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()


    init {
        viewModelScope.launch(Dispatchers.Main) {
            settingsRepository.settingsFlow.collect { settings ->
                _uiState.update { it.copy(loading = false, settings = settings) }
            }
        }
    }
}