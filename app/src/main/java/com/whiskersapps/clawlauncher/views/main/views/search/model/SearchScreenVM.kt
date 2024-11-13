package com.whiskersapps.clawlauncher.views.main.views.search.model

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
import com.whiskersapps.clawlauncher.shared.data.SettingsRepository.Companion.GridColsCount
import com.whiskersapps.clawlauncher.shared.model.AppShortcut
import com.whiskersapps.clawlauncher.shared.model.AppShortcut.*
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
            val unfoldedLandscapeCols: Int = 0,
            val gridColsCount: GridColsCount = GridColsCount()
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
                        unfoldedLandscapeCols = settings.unfoldedLandscapeCols,
                        gridColsCount = settingsRepository.gridColsCount.value
                    )
                }
            }
        }

        viewModelScope.launch(Dispatchers.IO){
            settingsRepository.gridColsCount.collect{ gridColsCount ->
                _state.update { it.copy(gridColsCount = gridColsCount) }
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

    fun onAction(action: SearchScreenAction) {
        when (action) {
            is SearchScreenAction.OnSearchInput -> onSearchInput(action.text)

            is SearchScreenAction.OnOpenApp -> onOpenApp(
                action.packageName,
                action.fragmentActivity
            )

            is SearchScreenAction.OnOpenShortcut -> onOpenShortcut(
                action.appPackageName,
                action.shortcut
            )

            is SearchScreenAction.OnOpenUrl -> onOpenUrl(action.url)

            is SearchScreenAction.OnRunAction -> onRunAction(action.fragmentActivity)

            is SearchScreenAction.OnSetFocusSearchBar -> onSetFocusSearchBar(action.focus)

            is SearchScreenAction.OnOpenAppInfo -> onOpenAppInfo(action.packageName)

            is SearchScreenAction.OnRequestUninstall -> onRequestUninstall(action.packageName)

            is SearchScreenAction.OnOpenGroup -> onOpenGroup(action.group)

            SearchScreenAction.OnClearSearch -> clearSearch()

            SearchScreenAction.OnCloseSheet -> {}
        }
    }

    private fun onSearchInput(text: String) {
        _state.update { it.copy(searchText = text) }

        viewModelScope.launch(Dispatchers.IO) {
            val newApps = if (text.isEmpty()) ArrayList() else appsRepository.getSearchedApps(text)

            val newBookmarks =
                if (text.isEmpty()) emptyList() else bookmarksRepository.getSearchedBookmarks(text)

            val newGroups =
                if (text.isEmpty()) emptyList() else bookmarksRepository.getSearchedGroups(text)

            _state.update {
                it.copy(
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
    }

    private fun onOpenApp(packageName: String, fragmentActivity: FragmentActivity) {
        viewModelScope.launch(Dispatchers.IO) {
            if (state.value.securedApps.contains(packageName)) {
                requestFingerprint(
                    fragmentActivity = fragmentActivity,
                    title = "Open App",
                    message = "Unlock to open the app",
                    onSuccess = {
                        appsRepository.openApp(packageName)
                        clearSearch()
                    }
                )
            } else {
                appsRepository.openApp(packageName)
                clearSearch()
            }
        }
    }

    private fun onOpenShortcut(packageName: String, shortcut: Shortcut) {
        viewModelScope.launch(Dispatchers.IO) {
            appsRepository.openShortcut(packageName, shortcut)
            clearSearch()
        }
    }

    private fun onOpenUrl(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }

            try {
                app.startActivity(intent)
            } catch (e: Exception) {
                println("Error opening search engine url: $e")
            }

            clearSearch()
        }
    }

    fun getSearchEngineUrl(): String {
        val query = state.value.searchEngine!!.query

        return if (query.contains("%s"))
            query.replace("%s", state.value.searchText)
        else
            "$query${state.value.searchText}"
    }

    private fun onRunAction(fragmentActivity: FragmentActivity) {
        viewModelScope.launch(Dispatchers.IO) {
            if (state.value.appShortcuts.isNotEmpty()) {
                onOpenApp(state.value.appShortcuts[0].packageName, fragmentActivity)
            } else if (state.value.bookmarks.isNotEmpty()) {
                onOpenUrl(state.value.bookmarks[0].url)
            } else if (state.value.searchEngine != null) {
                onOpenUrl(getSearchEngineUrl())
            }
        }
    }

    private fun onSetFocusSearchBar(focus: Boolean) {
        _state.update { it.copy(focusSearchBar = focus) }
    }

    private fun onOpenAppInfo(packageName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            appsRepository.openAppInfo(packageName)
            clearSearch()
        }
    }

    private fun onRequestUninstall(packageName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            appsRepository.requestUninstall(packageName)
            clearSearch()
        }
    }

    private fun onOpenGroup(group: BookmarkGroup) {
        viewModelScope.launch(Dispatchers.IO) {
            val urls = bookmarksRepository.data.value.bookmarks
                .filter { group.bookmarks.contains(it._id) }
                .map { it.url }

            for (url in urls) {
                onOpenUrl(url)
                delay(200)
            }

            clearSearch()
        }
    }

    private fun clearSearch() {
        viewModelScope.launch(Dispatchers.Main) {
            _state.update {
                it.copy(
                    searchText = "",
                    appShortcuts = emptyList(),
                    bookmarks = emptyList(),
                    groups = emptyList()
                )
            }
        }
    }
}