package com.whiskersapps.clawlauncher.views.main.views.search.viewmodel

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whiskersapps.clawlauncher.shared.data.AppsRepository
import com.whiskersapps.clawlauncher.shared.data.SearchEnginesRepository
import com.whiskersapps.clawlauncher.shared.data.SettingsRepository
import com.whiskersapps.clawlauncher.shared.model.AppShortcut
import com.whiskersapps.clawlauncher.shared.model.SearchEngine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenVM @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val appsRepository: AppsRepository,
    private val searchEnginesRepository: SearchEnginesRepository,
    private val app: Application
) : ViewModel() {

    companion object {
        data class UiState(
            val loading: Boolean = true,
            val searchText: String = "",
            val appShortcuts: List<AppShortcut> = emptyList(),
            val focusSearchBar: Boolean = false,
            val searchEngine: SearchEngine? = null
        )
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.Main) {
            settingsRepository.settingsFlow.collect {}
        }

        viewModelScope.launch(Dispatchers.Main) {
            searchEnginesRepository.data.collect { data ->
                _uiState.update {
                    it.copy(
                        loading = false,
                        searchEngine = data.defaultSearchEngine
                    )
                }
            }
        }
    }

    fun updateSearchText(text: String) {

        val newApps = if (text.isEmpty()) ArrayList() else appsRepository.getSearchedApps(text)

        _uiState.update {
            it.copy(
                searchText = text,
                appShortcuts = if (newApps.size >= 8) newApps.subList(0, 8) else newApps
            )
        }
    }

    fun openApp(packageName: String) {
        appsRepository.openApp(packageName)

        _uiState.update {
            it.copy(
                searchText = "",
                appShortcuts = ArrayList()
            )
        }
    }

    fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }

        try {
            app.startActivity(intent)
        } catch (e: Exception) {
            println("Error opening search engine url: $e")
        }
    }

    fun getSearchEngineUrl(): String {
        return uiState.value.searchEngine!!.query.replace("%s", uiState.value.searchText)
    }

    fun runAction() {

        if (uiState.value.appShortcuts.isNotEmpty()) {
            openApp(uiState.value.appShortcuts[0].packageName)
        } else if (uiState.value.searchEngine != null) {
            openUrl(getSearchEngineUrl())
        }
    }

    fun updateFocusSearchBar(focus: Boolean) {
        _uiState.update { it.copy(focusSearchBar = focus) }
    }


    fun openAppInfo(packageName: String) {
        appsRepository.openAppInfo(packageName)
    }

    fun requestUninstall(packageName: String) {
        appsRepository.requestUninstall(packageName)
    }
}