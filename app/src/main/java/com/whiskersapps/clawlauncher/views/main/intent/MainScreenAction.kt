package com.whiskersapps.clawlauncher.views.main.intent

sealed class MainScreenAction{
    data object NavigateToSettings: MainScreenAction()
}