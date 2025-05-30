package com.whiskersapps.clawlauncher.settings.search_engines

import com.whiskersapps.clawlauncher.shared.model.SearchEngine
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId

data class SearchEnginesScreenState(
    val loading: Boolean = true,
    val searchEngines: List<SearchEngine> = emptyList(),
    val defaultSearchEngineId: ObjectId? = BsonObjectId(),
    val defaultSearchEngine: SearchEngine? = null,
    val addEngineDialog: AddEngineDialog = AddEngineDialog(),
    val editEngineDialog: EditEngineDialog = EditEngineDialog()
) {
    data class AddEngineDialog(
        val show: Boolean = false,
        val name: String = "",
        val query: String = ""
    )

    data class EditEngineDialog(
        val id: ObjectId = BsonObjectId(),
        val show: Boolean = false,
        val name: String = "",
        val query: String = "",
        val defaultEngine: Boolean = false
    )
}