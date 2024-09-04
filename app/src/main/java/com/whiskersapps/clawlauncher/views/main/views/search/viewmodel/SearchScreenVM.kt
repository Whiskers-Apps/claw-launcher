package com.whiskersapps.clawlauncher.views.main.views.search.viewmodel

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whiskersapps.clawlauncher.shared.data.AppsRepository
import com.whiskersapps.clawlauncher.shared.data.BookmarksRepository
import com.whiskersapps.clawlauncher.shared.data.SearchEnginesRepository
import com.whiskersapps.clawlauncher.shared.data.SettingsRepository
import com.whiskersapps.clawlauncher.shared.model.AppShortcut
import com.whiskersapps.clawlauncher.shared.model.Bookmark
import com.whiskersapps.clawlauncher.shared.model.BookmarkGroup
import com.whiskersapps.clawlauncher.shared.model.SearchEngine
import com.whiskersapps.clawlauncher.shared.utils.requestFingerprint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
            val loadingSettings: Boolean = true,
            val loadingBookmarks: Boolean = true,
            val loadingSearchEngines: Boolean = true,
            val securedApps: List<String> = emptyList(),
            val layout: String = "",
            val searchText: String = "",
            val appShortcuts: List<AppShortcut> = emptyList(),
            val bookmarks: List<Bookmark> = emptyList(),
            val groups: List<BookmarkGroup> = emptyList(),
            val focusSearchBar: Boolean = false,
            val searchEngine: SearchEngine? = null,
            val cols: Int = 0,
            val landscapeCols: Int = 0,
            val unfoldedCols: Int = 0,
            val unfoldedLandscapeCols: Int = 0
        )
    }

    private val _state = MutableStateFlow(SearchScreenState())
    val state = _state.asStateFlow()

    init {

        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.settings.collect { settings ->
                _state.update {
                    it.copy(
                        loading = it.loadingSearchEngines || it.loadingBookmarks,
                        loadingSettings = false,
                        securedApps = settings.secureApps,
                        cols = settings.portraitCols,
                        landscapeCols = settings.landscapeCols,
                        unfoldedCols = settings.unfoldedPortraitCols,
                        unfoldedLandscapeCols = settings.unfoldedLandscapeCols
                    )
                }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            searchEnginesRepository.data.collect { data ->
                _state.update {
                    it.copy(
                        loading = it.loadingSettings || it.loadingBookmarks,
                        loadingSearchEngines = false,
                        searchEngine = data.defaultSearchEngine
                    )
                }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            bookmarksRepository.data.collect { data ->
                _state.update {
                    it.copy(
                        loading = it.loadingSettings || it.loadingSearchEngines,
                        loadingBookmarks = false,
                        bookmarks = data.bookmarks,
                        groups = data.groups
                    )
                }
            }
        }
    }

    fun updateSearchText(text: String) {

        val newApps = if (text.isEmpty()) ArrayList() else appsRepository.getSearchedApps(text)

        val newBookmarks =
            if (text.isEmpty()) emptyList() else bookmarksRepository.getSearchedBookmarks(text)

        val newGroups =
            if (text.isEmpty()) emptyList() else bookmarksRepository.getSearchedGroups(text)

        _state.update {
            it.copy(
                searchText = text,

                appShortcuts = if (newApps.size >= 8)
                    newApps.subList(0, 8)
                else newApps,

                bookmarks = if (newBookmarks.size >= 8)
                    newBookmarks.subList(0, 8)
                else newBookmarks,

                groups = if (newGroups.size >= 8)
                    newGroups.subList(0, 8)
                else newGroups
            )
        }
    }

    fun openApp(packageName: String, fragmentActivity: FragmentActivity) {
        if (state.value.securedApps.contains(packageName)) {
            requestFingerprint(
                fragmentActivity = fragmentActivity,
                title = "Open App",
                message = "Unlock to open the app",
                onSuccess = {
                    appsRepository.openApp(packageName)
                }
            )
        } else {
            appsRepository.openApp(packageName)
        }

        clearSearch()
    }

    fun openShortcut(packageName: String, shortcut: AppShortcut.Shortcut){
        viewModelScope.launch(Dispatchers.IO){
            appsRepository.openShortcut(packageName, shortcut)
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

    fun runAction(fragmentActivity: FragmentActivity) {
        if (state.value.appShortcuts.isNotEmpty()) {
            openApp(state.value.appShortcuts[0].packageName, fragmentActivity)
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

    fun openGroup(group: BookmarkGroup) {
        viewModelScope.launch {
            val urls = bookmarksRepository.data.value.bookmarks
                .filter { group.bookmarks.contains(it._id) }
                .map { it.url }

            for (url in urls) {
                openUrl(url)
                delay(200)
            }

            clearSearch()
        }
    }

    private fun clearSearch() {
        _state.update {
            it.copy(searchText = "", bookmarks = emptyList(), groups = emptyList())
        }
    }
}