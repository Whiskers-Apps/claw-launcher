package com.whiskersapps.clawlauncher.views.main.views.search.viewmodel

import com.whiskersapps.clawlauncher.shared.model.AppShortcut

data class SearchScreenUiState(
    val searchText: String,
    val appShortcuts: List<AppShortcut>
)
