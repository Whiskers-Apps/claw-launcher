package com.whiskersapps.clawlauncher.onboarding.select_engine_screen

import com.whiskersapps.clawlauncher.shared.model.SearchEngine

data class SelectEngineScreenState(
    val loading: Boolean = true,
    val searchEngines: List<SearchEngine> = emptyList(),
    val selectedEngine: SearchEngine? = null
)