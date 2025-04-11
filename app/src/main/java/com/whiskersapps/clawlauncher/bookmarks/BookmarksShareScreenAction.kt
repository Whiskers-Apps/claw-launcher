package com.whiskersapps.clawlauncher.bookmarks

import android.app.Activity

sealed interface BookmarksShareScreenAction {
    data object NavigateBack : BookmarksShareScreenAction

    data object AddBookmark : BookmarksShareScreenAction

    data class SetName(val name: String) : BookmarksShareScreenAction
    data class SetURL(val url: String) : BookmarksShareScreenAction
}