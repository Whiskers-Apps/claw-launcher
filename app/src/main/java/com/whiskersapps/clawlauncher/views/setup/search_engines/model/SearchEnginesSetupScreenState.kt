package com.whiskersapps.clawlauncher.views.setup.search_engines.model

import com.whiskersapps.clawlauncher.shared.model.SearchEngine
import org.mongodb.kbson.ObjectId

data class SearchEnginesSetupScreenState(
    val loading: Boolean = true,
    val searchEngines: List<SearchEngine> = emptyList(),
    val selectedEngine: SearchEngine? = null
)