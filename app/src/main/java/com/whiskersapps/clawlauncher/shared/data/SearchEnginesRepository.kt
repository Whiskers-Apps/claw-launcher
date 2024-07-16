package com.whiskersapps.clawlauncher.shared.data

import com.whiskersapps.clawlauncher.shared.model.SearchEngine
import io.realm.kotlin.Realm
import io.realm.kotlin.delete
import io.realm.kotlin.ext.query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.mongodb.kbson.ObjectId

class SearchEnginesRepository(
    private val realm: Realm,
    private val settingsRepository: SettingsRepository
) {

    companion object {
        data class Data(
            val searchEngines: List<SearchEngine> = emptyList(),
            val defaultSearchEngine: SearchEngine? = null
        )
    }

    private val _data = MutableStateFlow(Data())
    val data = _data.asStateFlow()


    suspend fun addSearchEngine(searchEngine: SearchEngine) {
        realm.write { copyToRealm(searchEngine) }
    }

    suspend fun updateSearchEngine(id: ObjectId, name: String, query: String) {
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
        settingsRepository.updateDefaultSearchEngine(engineId.toHexString())
    }

    suspend fun clearDefaultEngine() {
        settingsRepository.updateDefaultSearchEngine("")
    }

    fun deleteSearchEngine(id: ObjectId) {
        realm.writeBlocking {
            this.query<SearchEngine>("_id == $0", id).find().first().also {
                delete(it)
            }
        }
    }

    init {
        CoroutineScope(Dispatchers.IO).launch {
            realm.query<SearchEngine>().asFlow()
                .map { it.list }
                .collect { newSearchEngines ->

                    val settings = settingsRepository.settingsFlow.first()

                    val defaultEngine = if (settings.defaultSearchEngine.isEmpty()) {
                        null
                    } else {
                        val id = ObjectId(settings.defaultSearchEngine)
                        newSearchEngines.find { it._id == id }
                    }

                    _data.update {
                        Data(
                            searchEngines = newSearchEngines,
                            defaultSearchEngine = defaultEngine
                        )
                    }
                }
        }
    }
}