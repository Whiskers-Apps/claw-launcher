package com.whiskersapps.clawlauncher.views.setup.welcome.intent

sealed class WelcomeScreenAction {
    data object NavigateNext: WelcomeScreenAction()
}