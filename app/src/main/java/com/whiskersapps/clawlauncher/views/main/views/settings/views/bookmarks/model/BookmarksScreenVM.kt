package com.whiskersapps.clawlauncher.views.main.views.settings.views.bookmarks.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whiskersapps.clawlauncher.launcher.bookmarks.BookmarksRepo
import com.whiskersapps.clawlauncher.shared.model.Bookmark
import com.whiskersapps.clawlauncher.shared.model.BookmarkGroup
import com.whiskersapps.clawlauncher.views.main.views.settings.views.bookmarks.intent.BookmarksScreenAction
import io.realm.kotlin.ext.toRealmList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId

class BookmarksScreenVM(
    private val bookmarksRepository: BookmarksRepo
) : ViewModel() {

    private val _state = MutableStateFlow(BookmarksScreenState())
    val state = _state.asStateFlow()


    fun onAction(action: BookmarksScreenAction) {
        when (action) {
            BookmarksScreenAction.NavigateBack -> {}

            is BookmarksScreenAction.OpenAddDialog -> openAddBookmarkDialog(action.page)

            BookmarksScreenAction.CloseAddBookmarkDialog -> closeAddBookmarkDialog()

            is BookmarksScreenAction.UpdateAddBookmarkDialogFields -> updateAddBookmarkDialogFields(
                action.name,
                action.url
            )

            BookmarksScreenAction.AddBookmark -> addBookmark()

            is BookmarksScreenAction.UpdateSelectedTab -> {}

            is BookmarksScreenAction.OpenEditBookmarkDialog -> openEditBookmarkDialog(action.bookmark)

            BookmarksScreenAction.CloseEditBookmarkDialog -> closeEditBookmarkDialog()

            is BookmarksScreenAction.OpenEditGroupDialog -> openEditGroupDialog(action.group)

            BookmarksScreenAction.DeleteBookmark -> deleteBookmark()

            BookmarksScreenAction.EditBookmark -> editBookmark()

            is BookmarksScreenAction.UpdateEditBookmarkDialogFields -> updateEditBookmarkDialogFields(
                action.dialog
            )

            BookmarksScreenAction.CloseAddGroupDialog -> closeAddGroupDialog()

            BookmarksScreenAction.AddGroup -> addGroup()

            is BookmarksScreenAction.ChangeAddGroupBookmarkSelection -> changeAddGroupBookmarkSelection(
                action.id,
                action.selected
            )

            is BookmarksScreenAction.UpdateAddGroupDialogFields -> updateAddGroupDialogFields(action.name)

            is BookmarksScreenAction.ChangeEditGroupBookmarkSelection -> changeEditGroupBookmarkSelection(
                action.id,
                action.selected
            )

            BookmarksScreenAction.CloseEditGroupDialog -> closeEditGroupDialog()

            BookmarksScreenAction.SaveGroupEdit -> saveGroupEdit()

            is BookmarksScreenAction.UpdateEditGroupDialogFields -> editGroupDialogFields(action.name)

            BookmarksScreenAction.DeleteGroup -> deleteGroup()
        }
    }

    private fun changeEditGroupBookmarkSelection(id: ObjectId, selected: Boolean) {
        val bookmarks = state.value.editGroupDialog.bookmarks.map { bookmarkGroup ->
            if (bookmarkGroup.bookmark._id == id) bookmarkGroup.copy(selected = selected) else bookmarkGroup
        }

        _state.update { it.copy(editGroupDialog = it.editGroupDialog.copy(bookmarks = bookmarks)) }
    }

    private fun closeEditGroupDialog() {
        _state.update {
            it.copy(
                editGroupDialog = BookmarksScreenState.EditGroupDialog(),
                showEditGroupDialog = false
            )
        }
    }

    private fun saveGroupEdit() {
        val selectedBookmarks =
            state.value.editGroupDialog.bookmarks.filter { it.selected }.map { it.bookmark._id }

        viewModelScope.launch(Dispatchers.IO) {
            bookmarksRepository.updateBookmarkGroup(
                id = state.value.editGroupDialog.id,
                name = state.value.editGroupDialog.name,
                bookmarks = selectedBookmarks
            )

            closeEditGroupDialog()
        }
    }

    private fun editGroupDialogFields(name: String) {
        _state.update { it.copy(editGroupDialog = it.editGroupDialog.copy(name = name)) }
    }

    private fun updateAddGroupDialogFields(name: String) {
        _state.update { it.copy(addGroupDialog = it.addGroupDialog.copy(name = name)) }
    }

    private fun changeAddGroupBookmarkSelection(id: ObjectId, selected: Boolean) {
        val bookmarks = state.value.addGroupDialog.bookmarks.map { bookmarkGroup ->
            if (bookmarkGroup.bookmark._id == id) bookmarkGroup.copy(selected = selected) else bookmarkGroup
        }

        _state.update { it.copy(addGroupDialog = it.addGroupDialog.copy(bookmarks = bookmarks)) }
    }

    private fun addGroup() {
        val selectedBookmarks =
            state.value.addGroupDialog.bookmarks.filter { it.selected }.map { it.bookmark._id }

        val group = BookmarkGroup().apply {
            name = state.value.addGroupDialog.name
            bookmarks = selectedBookmarks.toRealmList()
        }

        viewModelScope.launch(Dispatchers.IO) {
            bookmarksRepository.addGroup(group)

            closeAddGroupDialog()
        }
    }

    private fun closeAddGroupDialog() {
        _state.update {
            it.copy(
                addGroupDialog = BookmarksScreenState.AddGroupDialog(),
                showAddGroupDialog = false
            )
        }
    }

    private fun openEditGroupDialog(group: BookmarkGroup) {

        val bookmarks = state.value.bookmarks.map {
            BookmarksScreenState.GroupBookmark(
                bookmark = it,
                selected = group.bookmarks.contains(it._id)
            )
        }

        _state.update {
            it.copy(
                editGroupDialog = BookmarksScreenState.EditGroupDialog(
                    id = group._id,
                    name = group.name,
                    bookmarks = bookmarks
                ),
                showEditGroupDialog = true
            )
        }
    }

    private fun updateEditBookmarkDialogFields(dialog: BookmarksScreenState.EditBookmarkDialog) {
        _state.update { it.copy(editBookmarkDialog = dialog) }
    }

    private fun editBookmark() {
        state.value.editBookmarkDialog.also {
            bookmarksRepository.updateBookmark(it.bookmark._id, it.name, it.url)
            closeEditBookmarkDialog()
        }
    }

    private fun deleteBookmark() {
        bookmarksRepository.deleteBookmark(state.value.editBookmarkDialog.bookmark._id)
        closeEditBookmarkDialog()
    }

    private fun closeEditBookmarkDialog() {
        _state.update { it.copy(showEditBookmarkDialog = false) }
    }

    private fun openEditBookmarkDialog(bookmark: Bookmark) {
        _state.update {
            it.copy(
                showEditBookmarkDialog = true,
                editBookmarkDialog = it.editBookmarkDialog.copy(
                    bookmark = bookmark,
                    name = bookmark.name,
                    url = bookmark.url
                )
            )
        }
    }

    private fun openAddBookmarkDialog(page: Int) {
        when (page) {
            0 -> _state.update { it.copy(showAddBookmarkDialog = true) }
            1 -> {

                val groupBookmarks =
                    state.value.bookmarks.map { BookmarksScreenState.GroupBookmark(bookmark = it) }

                _state.update {
                    it.copy(
                        showAddGroupDialog = true,
                        addGroupDialog = it.addGroupDialog.copy(bookmarks = groupBookmarks)
                    )
                }
            }
        }
    }

    private fun closeAddBookmarkDialog() {
        _state.update {
            it.copy(
                showAddBookmarkDialog = false,
                addBookmarkDialog = it.addBookmarkDialog.copy(name = "", url = "")
            )
        }
    }

    private fun updateAddBookmarkDialogFields(name: String, url: String) {
        _state.update {
            it.copy(addBookmarkDialog = it.addBookmarkDialog.copy(name = name, url = url))
        }
    }

    private fun addBookmark() {
        val bookmark = Bookmark().apply {
            name = state.value.addBookmarkDialog.name
            url = state.value.addBookmarkDialog.url
        }

        bookmarksRepository.addBookmark(bookmark)
        closeAddBookmarkDialog()
    }

    private fun deleteGroup() {
        bookmarksRepository.deleteBookmarkGroup(state.value.editGroupDialog.id)
        closeEditGroupDialog()
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            bookmarksRepository.data.collect { data ->
                _state.update {
                    it.copy(
                        loading = false,
                        bookmarks = data.bookmarks,
                        groups = data.groups
                    )
                }
            }
        }
    }
}