package com.whiskersapps.clawlauncher.views.main.views.settings.views.about.intent

sealed class AboutScreenAction {
    data object NavigateBack: AboutScreenAction()
    data object OpenRepoUrl: AboutScreenAction()
}