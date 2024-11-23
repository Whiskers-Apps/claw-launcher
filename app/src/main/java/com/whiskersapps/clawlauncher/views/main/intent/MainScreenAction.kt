package com.whiskersapps.clawlauncher.views.main.intent

sealed class MainScreenAction{
    data object OnNavigateToSettings: MainScreenAction()
    data object OnNavigateToLockSettings: MainScreenAction()
}