package com.whiskersapps.clawlauncher.views.main.views.settings.views.bookmarks.viewmodel

import com.whiskersapps.clawlauncher.shared.model.Bookmark
import com.whiskersapps.clawlauncher.shared.model.BookmarkGroup
import org.mongodb.kbson.ObjectId

data class BookmarksScreenState(
    val loading: Boolean = true,
    val bookmarks: List<Bookmark> = emptyList(),
    val groups: List<BookmarkGroup> = emptyList(),
    val showAddBookmarkDialog: Boolean = false,
    val showEditBookmarkDialog: Boolean = false,
    val addBookmarkDialog: AddBookmarkDialog = AddBookmarkDialog(),
    val editBookmarkDialog: EditBookmarkDialog = EditBookmarkDialog()
) {
    data class AddBookmarkDialog(
        val name: String = "",
        val url: String = ""
    )

    data class EditBookmarkDialog(
        val bookmark: Bookmark = Bookmark(),
        val name: String = "",
        val url: String = ""
    )
}
