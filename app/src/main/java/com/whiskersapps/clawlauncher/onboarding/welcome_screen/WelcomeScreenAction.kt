package com.whiskersapps.clawlauncher.onboarding.welcome_screen

sealed class WelcomeScreenAction {
    data object NavigateNext : WelcomeScreenAction()
}