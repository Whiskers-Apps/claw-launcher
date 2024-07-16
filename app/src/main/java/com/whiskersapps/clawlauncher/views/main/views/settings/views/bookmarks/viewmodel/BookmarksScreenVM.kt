package com.whiskersapps.clawlauncher.views.main.views.settings.views.bookmarks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whiskersapps.clawlauncher.shared.data.BookmarksRepository
import com.whiskersapps.clawlauncher.shared.model.Bookmark
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

@HiltViewModel
class BookmarksScreenVM @Inject constructor(
    private val bookmarksRepository: BookmarksRepository
) : ViewModel() {

    private val _state = MutableStateFlow(BookmarksScreenState())
    val state = _state.asStateFlow()


    fun onAction(action: BookmarksScreenAction) {
        when (action) {
            BookmarksScreenAction.NavigateBack -> {}

            BookmarksScreenAction.OpenAddBookmarkDialog -> openAddBookmarkDialog()

            BookmarksScreenAction.CloseAddBookmarkDialog -> closeAddBookmarkDialog()

            is BookmarksScreenAction.UpdateAddBookmarkDialogFields -> updateAddBookmarkDialogFields(
                action.name,
                action.url
            )

            BookmarksScreenAction.AddBookmark -> addBookmark()

            is BookmarksScreenAction.UpdateSelectedTab -> {}

            is BookmarksScreenAction.OpenEditBookmarkDialog -> openEditBookmarkDialog(action.bookmark)

            BookmarksScreenAction.CloseEditBookmarkDialog -> closeEditBookmarkDialog()

            BookmarksScreenAction.DeleteBookmark -> deleteBookmark()

            BookmarksScreenAction.EditBookmark -> editBookmark()

            is BookmarksScreenAction.UpdateEditBookmarkDialogFields -> updateEditBookmarkDialogFields(
                action.dialog
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

    private fun openAddBookmarkDialog() {
        _state.update { it.copy(showAddBookmarkDialog = true) }
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

    init {
        viewModelScope.launch(Dispatchers.Main) {
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