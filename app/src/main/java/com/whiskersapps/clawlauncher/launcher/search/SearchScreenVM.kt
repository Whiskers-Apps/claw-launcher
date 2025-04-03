package com.whiskersapps.clawlauncher.launcher.search

import android.app.Application
import android.content.Intent
import androidx.core.net.toUri
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whiskersapps.clawlauncher.foldable.FoldableRepo
import com.whiskersapps.clawlauncher.launcher.apps.di.AppsRepo
import com.whiskersapps.clawlauncher.launcher.bookmarks.BookmarksRepo
import com.whiskersapps.clawlauncher.search_engines.SearchEnginesRepo
import com.whiskersapps.clawlauncher.settings.di.SettingsRepo
import com.whiskersapps.clawlauncher.shared.model.App
import com.whiskersapps.clawlauncher.shared.model.Bookmark
import com.whiskersapps.clawlauncher.shared.model.BookmarkGroup
import com.whiskersapps.clawlauncher.shared.model.SearchEngine
import com.whiskersapps.clawlauncher.shared.model.Settings
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.DEFAULT_DARK_MODE
import com.whiskersapps.clawlauncher.shared.utils.requestFingerprint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchScreenVM(
    private val settingsRepo: SettingsRepo,
    private val appsRepo: AppsRepo,
    private val bookmarksRepository: BookmarksRepo,
    private val searchEnginesRepo: SearchEnginesRepo,
    private val foldableRepo: FoldableRepo,
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
            val apps: List<App> = emptyList(),
            val bookmarks: List<Bookmark> = emptyList(),
            val groups: List<BookmarkGroup> = emptyList(),
            val focusSearchBar: Boolean = false,
            val searchEngine: SearchEngine? = null,
            val portraitColors: Int = 0,
            val landscapeCols: Int = 0,
            val showResults: Boolean = false,
            val darkMode: String = DEFAULT_DARK_MODE
        )
    }

    private val _state = MutableStateFlow(SearchScreenState())
    val state = _state.asStateFlow()

    init {

        viewModelScope.launch(Dispatchers.IO) {
            settingsRepo.settings.collect { settings ->
                _state.update {
                    it.copy(
                        loading = it.loadingSearchEngines || it.loadingBookmarks,
                        loadingSettings = false,
                        securedApps = settings.secureApps,
                        portraitColors = settings.portraitCols,
                        landscapeCols = settings.landscapeCols,
                    )
                }
            }
        }

        viewModelScope.launch(Dispatchers.Main) {
            foldableRepo.grid.collect { grid ->
                _state.update {
                    it.copy(
                        portraitColors = grid.portrait,
                        landscapeCols = grid.landscape
                    )
                }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
//            settingsRepo.gridColsCount.collect { gridColsCount ->
//                _state.update { it.copy(gridColsCount = gridColsCount) }
//            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            searchEnginesRepo.data.collect { data ->
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

        viewModelScope.launch {

            val newApps = if (text.isEmpty()) ArrayList() else appsRepo.getSearchedApps(text)

            val newBookmarks =
                if (text.isEmpty()) emptyList() else bookmarksRepository.getSearchedBookmarks(text)

            val newGroups =
                if (text.isEmpty()) emptyList() else bookmarksRepository.getSearchedGroups(text)

            _state.update {
                it.copy(
                    apps = if (newApps.size >= 8)
                        newApps.subList(0, 8)
                    else newApps,

                    bookmarks = if (newBookmarks.size >= 8)
                        newBookmarks.subList(0, 8)
                    else newBookmarks,

                    groups = if (newGroups.size >= 8)
                        newGroups.subList(0, 8)
                    else newGroups,

                    showResults = text.trim().isNotEmpty()
                )
            }
        }
    }

    private fun onOpenApp(packageName: String, fragmentActivity: FragmentActivity) {
        viewModelScope.launch {
            if (state.value.securedApps.contains(packageName)) {
                requestFingerprint(
                    fragmentActivity = fragmentActivity,
                    title = "Open App",
                    message = "Unlock to open the app",
                    onSuccess = {
                        appsRepo.openApp(packageName)
                        clearSearch()
                    }
                )
            } else {
                appsRepo.openApp(packageName)
                clearSearch()
            }
        }
    }

    private fun onOpenShortcut(packageName: String, shortcut: App.Shortcut) {
        viewModelScope.launch {
            appsRepo.openShortcut(packageName, shortcut)
            clearSearch()
        }
    }

    private fun onOpenUrl(url: String) {
        viewModelScope.launch {
            val intent = Intent(Intent.ACTION_VIEW, url.toUri()).apply {
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
        if (state.value.showResults) {
            viewModelScope.launch {
                if (state.value.apps.isNotEmpty()) {
                    onOpenApp(state.value.apps[0].packageName, fragmentActivity)
                } else if (state.value.bookmarks.isNotEmpty()) {
                    onOpenUrl(state.value.bookmarks[0].url)
                } else if (state.value.searchEngine != null) {
                    onOpenUrl(getSearchEngineUrl())
                }
            }
        }
    }

    private fun onSetFocusSearchBar(focus: Boolean) {
        _state.update { it.copy(focusSearchBar = focus) }
    }

    private fun onOpenAppInfo(packageName: String) {
        viewModelScope.launch {
            appsRepo.openAppInfo(packageName)
            clearSearch()
        }
    }

    private fun onRequestUninstall(packageName: String) {
        viewModelScope.launch {
            appsRepo.requestUninstall(packageName)
            clearSearch()
        }
    }

    private fun onOpenGroup(group: BookmarkGroup) {
        viewModelScope.launch {
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
                    apps = emptyList(),
                    bookmarks = emptyList(),
                    groups = emptyList()
                )
            }
        }
    }
}