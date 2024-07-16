package com.whiskersapps.clawlauncher.views.main.views.settings.views.bookmarks.viewmodel

import com.whiskersapps.clawlauncher.shared.model.Bookmark

sealed class BookmarksScreenAction {
    data object NavigateBack : BookmarksScreenAction()

    data class UpdateSelectedTab(val tab: Int) : BookmarksScreenAction()

    data object OpenAddBookmarkDialog : BookmarksScreenAction()

    data class OpenEditBookmarkDialog(val bookmark: Bookmark) : BookmarksScreenAction()

    data object CloseAddBookmarkDialog : BookmarksScreenAction()

    data object CloseEditBookmarkDialog : BookmarksScreenAction()

    data class UpdateAddBookmarkDialogFields(val name: String, val url: String) :
        BookmarksScreenAction()

    data class UpdateEditBookmarkDialogFields(val dialog: BookmarksScreenState.EditBookmarkDialog) :
        BookmarksScreenAction()

    data object AddBookmark : BookmarksScreenAction()

    data object EditBookmark : BookmarksScreenAction()

    data object DeleteBookmark : BookmarksScreenAction()
}