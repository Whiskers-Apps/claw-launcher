package com.whiskersapps.clawlauncher.views.setup.search_engines.intent

import com.whiskersapps.clawlauncher.shared.model.SearchEngine

sealed class SearchEnginesSetupScreenAction {
   data object Finish: SearchEnginesSetupScreenAction()
   data object NavigateBack: SearchEnginesSetupScreenAction()
   data class SetDefaultEngine(val searchEngine: SearchEngine): SearchEnginesSetupScreenAction()
}