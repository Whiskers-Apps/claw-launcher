package com.whiskersapps.clawlauncher.views.main.views.settings.views.search_engines.intent

import com.whiskersapps.clawlauncher.shared.model.SearchEngine

sealed class SearchEnginesScreenAction {
    data object NavigateBack: SearchEnginesScreenAction()
    data object ShowAddEngineDialog: SearchEnginesScreenAction()
    data object CloseAddEngineDialog: SearchEnginesScreenAction()
    data class UpdateAddEngineDialogFields(val name: String, val query: String): SearchEnginesScreenAction()
    data object AddEngine: SearchEnginesScreenAction()
    data class ShowEditEngineDialog(val engine: SearchEngine): SearchEnginesScreenAction()
    data object CloseEditEngineDialog: SearchEnginesScreenAction()
    data class UpdateEditEngineDialogFields(val name: String, val query: String, val default: Boolean): SearchEnginesScreenAction()
    data object DeleteEngine: SearchEnginesScreenAction()
    data object SaveEditEngine: SearchEnginesScreenAction()
}