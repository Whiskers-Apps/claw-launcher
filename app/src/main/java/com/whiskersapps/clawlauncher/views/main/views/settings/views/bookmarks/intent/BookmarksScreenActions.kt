package com.whiskersapps.clawlauncher.views.main.views.settings.views.bookmarks.intent

import com.whiskersapps.clawlauncher.shared.model.Bookmark
import com.whiskersapps.clawlauncher.shared.model.BookmarkGroup
import com.whiskersapps.clawlauncher.views.main.views.settings.views.bookmarks.model.BookmarksScreenState
import org.mongodb.kbson.ObjectId

sealed class BookmarksScreenAction {
    data object NavigateBack : BookmarksScreenAction()

    data class UpdateSelectedTab(val tab: Int) : BookmarksScreenAction()

    data class OpenAddDialog(val page: Int) : BookmarksScreenAction()

    data class OpenEditBookmarkDialog(val bookmark: Bookmark) : BookmarksScreenAction()

    data class OpenEditGroupDialog(val group: BookmarkGroup) : BookmarksScreenAction()

    data object CloseAddBookmarkDialog : BookmarksScreenAction()

    data object CloseAddGroupDialog : BookmarksScreenAction()

    data object CloseEditBookmarkDialog : BookmarksScreenAction()

    data class UpdateAddBookmarkDialogFields(val name: String, val url: String) :
        BookmarksScreenAction()

    data class UpdateAddGroupDialogFields(val name: String) : BookmarksScreenAction()

    data class UpdateEditBookmarkDialogFields(val dialog: BookmarksScreenState.EditBookmarkDialog) :
        BookmarksScreenAction()

    data object AddBookmark : BookmarksScreenAction()

    data object AddGroup : BookmarksScreenAction()

    data object EditBookmark : BookmarksScreenAction()

    data object DeleteBookmark : BookmarksScreenAction()

    data class ChangeAddGroupBookmarkSelection(val id: ObjectId, val selected: Boolean) :
        BookmarksScreenAction()

    data object CloseEditGroupDialog : BookmarksScreenAction()

    data class UpdateEditGroupDialogFields(val name: String) : BookmarksScreenAction()

    data class ChangeEditGroupBookmarkSelection(val id: ObjectId, val selected: Boolean) :
        BookmarksScreenAction()

    data object SaveGroupEdit : BookmarksScreenAction()

    data object DeleteGroup : BookmarksScreenAction()
}