package com.whiskersapps.clawlauncher.views.main.views.search.viewmodel

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whiskersapps.clawlauncher.shared.data.AppsRepository
import com.whiskersapps.clawlauncher.shared.data.BookmarksRepository
import com.whiskersapps.clawlauncher.shared.data.SearchEnginesRepository
import com.whiskersapps.clawlauncher.shared.data.SettingsRepository
import com.whiskersapps.clawlauncher.shared.model.AppShortcut
import com.whiskersapps.clawlauncher.shared.model.Bookmark
import com.whiskersapps.clawlauncher.shared.model.SearchEngine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenVM @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val appsRepository: AppsRepository,
    private val bookmarksRepository: BookmarksRepository,
    private val searchEnginesRepository: SearchEnginesRepository,
    private val app: Application
) : ViewModel() {

    companion object {
        data class SearchScreenState(
            val loading: Boolean = true,
            val loadingBookmarks: Boolean = true,
            val loadingSearchEngines: Boolean = true,
            val searchText: String = "",
            val appShortcuts: List<AppShortcut> = emptyList(),
            val bookmarks: List<Bookmark> = emptyList(),
            val focusSearchBar: Boolean = false,
            val searchEngine: SearchEngine? = null
        )
    }

    private val _state = MutableStateFlow(SearchScreenState())
    val state = _state.asStateFlow()

    init {

        viewModelScope.launch(Dispatchers.Main) {
            settingsRepository.settingsFlow.collect {}
        }

        viewModelScope.launch(Dispatchers.Main) {
            searchEnginesRepository.data.collect { data ->
                _state.update {
                    it.copy(
                        loading = it.loadingBookmarks,
                        loadingSearchEngines = false,
                        searchEngine = data.defaultSearchEngine
                    )
                }
            }
        }

        viewModelScope.launch(Dispatchers.Main) {
            bookmarksRepository.data.collect { data ->
                _state.update {
                    it.copy(
                        loading = it.loadingSearchEngines,
                        loadingBookmarks = false,
                        bookmarks = data.bookmarks
                    )
                }
            }
        }
    }

    fun updateSearchText(text: String) {

        val newApps = if (text.isEmpty()) ArrayList() else appsRepository.getSearchedApps(text)
        val newBookmarks = if(text.isEmpty()) emptyList() else bookmarksRepository.getSearchedBookmarks(text)

        _state.update {
            it.copy(
                searchText = text,
                appShortcuts = if (newApps.size >= 8) newApps.subList(0, 8) else newApps,
                bookmarks = if(newBookmarks.size >= 8) newBookmarks.subList(0, 8) else newBookmarks
            )
        }
    }

    fun openApp(packageName: String) {
        appsRepository.openApp(packageName)

        _state.update {
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
        val query = state.value.searchEngine!!.query

        return if (query.contains("%s"))
            query.replace("%s", state.value.searchText)
        else
            "$query${state.value.searchText}"
    }

    fun runAction() {

        if (state.value.appShortcuts.isNotEmpty()) {
            openApp(state.value.appShortcuts[0].packageName)
        } else if (state.value.bookmarks.isNotEmpty()) {
            openUrl(state.value.bookmarks[0].url)
        } else if (state.value.searchEngine != null) {
            openUrl(getSearchEngineUrl())
        }
    }

    fun updateFocusSearchBar(focus: Boolean) {
        _state.update { it.copy(focusSearchBar = focus) }
    }


    fun openAppInfo(packageName: String) {
        appsRepository.openAppInfo(packageName)
    }

    fun requestUninstall(packageName: String) {
        appsRepository.requestUninstall(packageName)
    }
}