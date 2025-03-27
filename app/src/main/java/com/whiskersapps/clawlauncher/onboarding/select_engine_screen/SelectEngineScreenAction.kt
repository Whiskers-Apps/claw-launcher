package com.whiskersapps.clawlauncher.onboarding.select_engine_screen

import com.whiskersapps.clawlauncher.shared.model.SearchEngine

sealed class SelectEngineScreenAction {
    data object Finish : SelectEngineScreenAction()
    data object NavigateBack : SelectEngineScreenAction()
    data class SetDefaultEngine(val searchEngine: SearchEngine) : SelectEngineScreenAction()
}