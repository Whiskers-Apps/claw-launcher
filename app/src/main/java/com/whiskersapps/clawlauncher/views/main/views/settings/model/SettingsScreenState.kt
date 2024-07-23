package com.whiskersapps.clawlauncher.views.main.views.settings.model

import com.whiskersapps.clawlauncher.shared.model.Bookmark
import com.whiskersapps.clawlauncher.shared.model.BookmarkGroup
import com.whiskersapps.clawlauncher.shared.model.SearchEngine
import com.whiskersapps.clawlauncher.shared.model.Settings

data class SettingsScreenState(
    val loading: Boolean = true,
    val settings: Settings = Settings(),
    val searchEngines: List<SearchEngine> = emptyList(),
    val bookmarks: List<Bookmark> = emptyList(),
    val bookmarkGroups: List<BookmarkGroup> = emptyList(),
    val isDefaultLauncher: Boolean = false
)