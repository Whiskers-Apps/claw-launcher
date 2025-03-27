package com.whiskersapps.clawlauncher.launcher.bookmarks

import com.whiskersapps.clawlauncher.shared.model.Bookmark
import com.whiskersapps.clawlauncher.shared.model.BookmarkGroup
import com.whiskersapps.lib.Sniffer
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.toRealmList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId

class BookmarksRepository(
    private val realm: Realm
) {

    companion object {
        data class Data(
            val bookmarks: List<Bookmark> = emptyList(),
            val groups: List<BookmarkGroup> = emptyList()
        )
    }

    private val _data = MutableStateFlow(Data())
    val data = _data.asStateFlow()

    fun addBookmark(bookmark: Bookmark) {
        realm.writeBlocking { copyToRealm(bookmark) }
    }

    fun updateBookmark(id: ObjectId, name: String, url: String) {
        realm.query<Bookmark>("_id == $0", id).first().find()?.also { bookmark ->
            realm.writeBlocking {
                findLatest(bookmark)?.also {
                    it.name = name
                    it.url = url
                }
            }
        }
    }

    fun deleteBookmark(id: ObjectId) {
        realm.writeBlocking {
            this.query<Bookmark>("_id == $0", id).find().first().also {
                delete(it)
            }
        }
    }

    fun addGroup(group: BookmarkGroup) {
        realm.writeBlocking { copyToRealm(group) }
    }

    fun updateBookmarkGroup(id: ObjectId, name: String, bookmarks: List<ObjectId>) {
        realm.query<BookmarkGroup>("_id == $0", id).first().find()?.also { group ->
            realm.writeBlocking {
                findLatest(group)?.also {
                    it.name = name
                    it.bookmarks = bookmarks.toRealmList()
                }
            }
        }
    }

    fun deleteBookmarkGroup(id: ObjectId) {
        realm.writeBlocking {
            this.query<BookmarkGroup>("_id == $0", id).find().first().also {
                delete(it)
            }
        }
    }

    fun getSearchedBookmarks(text: String): List<Bookmark> {
        val sniffer = Sniffer()
        return data.value.bookmarks.filter { sniffer.matches(it.name, text) }
    }

    fun getSearchedGroups(text: String): List<BookmarkGroup> {
        val sniffer = Sniffer()
        return data.value.groups.filter { sniffer.matches(it.name, text) }
    }

    init {
        CoroutineScope(Dispatchers.IO).launch {
            realm.query<Bookmark>().asFlow()
                .map { it.list }
                .collect { bookmarks ->
                    _data.update { it.copy(bookmarks = bookmarks.sortedBy { bookmark -> bookmark.name.lowercase() }) }
                }
        }

        CoroutineScope(Dispatchers.IO).launch {
            realm.query<BookmarkGroup>().asFlow()
                .map { it.list }
                .collect { groups ->
                    _data.update { it.copy(groups = groups.sortedBy { group -> group.name.lowercase() }) }
                }
        }
    }
}