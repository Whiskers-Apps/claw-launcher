package com.whiskersapps.clawlauncher.views.setup.layout.intent

import androidx.navigation.NavController

sealed class LayoutScreenAction {
    data object NavigateBack : LayoutScreenAction()
    data object Finish: LayoutScreenAction()
    data object SetBubblyLayout: LayoutScreenAction()
    data object SetMinimalLayout: LayoutScreenAction()
}