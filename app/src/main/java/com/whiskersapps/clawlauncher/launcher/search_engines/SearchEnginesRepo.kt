package com.whiskersapps.clawlauncher.launcher.search_engines

import com.whiskersapps.clawlauncher.settings.di.SettingsRepo
import com.whiskersapps.clawlauncher.shared.model.SearchEngine
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId

class SearchEnginesRepo(
    private val realm: Realm,
    private val settingsRepo: SettingsRepo
) {

    companion object {
        data class Data(
            val loading: Boolean = true,
            val searchEngines: List<SearchEngine> = emptyList(),
            val defaultSearchEngine: SearchEngine? = null
        )
    }

    private val _data = MutableStateFlow(Data())
    val data = _data.asStateFlow()


    suspend fun addSearchEngine(searchEngine: SearchEngine) {
        realm.write { copyToRealm(searchEngine) }
    }

    fun updateSearchEngine(id: ObjectId, name: String, query: String) {
        realm.query<SearchEngine>("_id == $0", id).first().find()?.also { searchEngine ->
            realm.writeBlocking {
                findLatest(searchEngine)?.also {
                    it.name = name
                    it.query = query
                }
            }
        }
    }

    suspend fun makeDefaultEngine(engineId: ObjectId) {
        settingsRepo.updateDefaultSearchEngine(engineId.toHexString())
        _data.update { it.copy(defaultSearchEngine = it.searchEngines.find { engine -> engine._id == engineId }) }
    }

    suspend fun clearDefaultEngine() {
        settingsRepo.updateDefaultSearchEngine("")
    }

    fun deleteSearchEngine(id: ObjectId) {
        realm.writeBlocking {
            this.query<SearchEngine>("_id == $0", id).find().first().also {
                delete(it)
            }
        }
    }

    /// Creates the default search engines
    suspend fun initEngines() {
        if (data.value.searchEngines.isEmpty()) {
            val google = SearchEngine().apply {
                name = "Google"
                query = "https://www.google.com/search?q=%s"
            }

            val duckDuckGo = SearchEngine().apply {
                name = "DuckDuckGo"
                query = "https://duckduckgo.com/?q=%s"
            }

            val brave = SearchEngine().apply {
                name = "Brave"
                query = "https://search.brave.com/search?q=%s"
            }

            val startPage = SearchEngine().apply {
                name = "Start Page"
                query = "https://startpage.com/do/dsearch?query=%s"
            }

            val qwant = SearchEngine().apply {
                name = "Qwant"
                query = "https://www.qwant.com/?q=%s"
            }

            realm.writeBlocking {
                copyToRealm(google)
                copyToRealm(duckDuckGo)
                copyToRealm(brave)
                copyToRealm(startPage)
                copyToRealm(qwant)
            }

            makeDefaultEngine(duckDuckGo._id)
        }
    }

    init {
        CoroutineScope(Dispatchers.IO).launch {
            realm.query<SearchEngine>().asFlow()
                .map { it.list }
                .collect { searchEngines ->

                    val settings = settingsRepo.settings.first()

                    val defaultEngine = if (settings.defaultSearchEngine.isEmpty()) {
                        null
                    } else {
                        val id = ObjectId(settings.defaultSearchEngine)
                        searchEngines.find { it._id == id }
                    }

                    _data.update {
                        Data(
                            loading = false,
                            searchEngines = searchEngines,
                            defaultSearchEngine = defaultEngine
                        )
                    }
                }
        }
    }
}