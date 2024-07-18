package com.whiskersapps.clawlauncher.views.main.views.settings.view.views.bookmarks.model

import com.whiskersapps.clawlauncher.shared.model.Bookmark
import com.whiskersapps.clawlauncher.shared.model.BookmarkGroup
import org.mongodb.kbson.ObjectId

data class BookmarksScreenState(
    val loading: Boolean = true,
    val bookmarks: List<Bookmark> = emptyList(),
    val groups: List<BookmarkGroup> = emptyList(),
    val showAddBookmarkDialog: Boolean = false,
    val showEditBookmarkDialog: Boolean = false,
    val showAddGroupDialog: Boolean = false,
    val showEditGroupDialog: Boolean = false,
    val addBookmarkDialog: AddBookmarkDialog = AddBookmarkDialog(),
    val addGroupDialog: AddGroupDialog = AddGroupDialog(),
    val editGroupDialog: EditGroupDialog = EditGroupDialog(),
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

    data class AddGroupDialog(
        val name: String = "",
        val bookmarks: List<GroupBookmark> = emptyList()
    )

    data class EditGroupDialog(
        val id: ObjectId = ObjectId(),
        val name: String = "",
        val bookmarks: List<GroupBookmark> = emptyList()
    )

    data class GroupBookmark(
        val selected: Boolean = false,
        val bookmark: Bookmark = Bookmark()
    )
}
