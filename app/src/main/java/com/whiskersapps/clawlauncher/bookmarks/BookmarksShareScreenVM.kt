package com.whiskersapps.clawlauncher.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whiskersapps.clawlauncher.bookmarks.di.BookmarksRepo
import com.whiskersapps.clawlauncher.settings.di.SettingsRepo
import com.whiskersapps.clawlauncher.shared.model.Bookmark
import com.whiskersapps.clawlauncher.shared.utils.isUrl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.whiskersapps.clawlauncher.bookmarks.BookmarksShareScreenAction as Action

class BookmarksShareScreenVM(
    private val settingsRepo: SettingsRepo,
    private val bookmarksRepo: BookmarksRepo
) : ViewModel() {
    private val _state = MutableStateFlow(BookmarksShareScreenState())
    val state = _state.asStateFlow()

    var onBookmarkAddedListener: () -> Unit = {}

    fun onAction(action: Action) {
        when (action) {
            is Action.SetName -> {
                setName(action.name)
            }

            is Action.SetURL -> {
                setURL(action.url)
            }

            is Action.AddBookmark -> {
                addBookmark()
            }

            else -> {}
        }
    }

    private fun setName(name: String) {
        _state.update { it.copy(name = name) }

        verifyAddButton()
    }

    private fun setURL(url: String) {
        _state.update { it.copy(url = url) }

        verifyAddButton()
    }

    private fun verifyAddButton() {
        viewModelScope.launch(Dispatchers.Main) {
            _state.update {
                val nameIsValid = it.name.trim().isNotEmpty()
                val urlIsValid = it.url.trim().isNotEmpty() && it.url.isUrl()

                it.copy(
                    enableAddButton = nameIsValid && urlIsValid
                )
            }
        }
    }

    private fun addBookmark() {
        viewModelScope.launch(Dispatchers.IO) {
            val bookmark = Bookmark().apply {
                name = state.value.name
                url = state.value.url
            }

            bookmarksRepo.addBookmark(bookmark)

            onBookmarkAddedListener()
        }
    }
}