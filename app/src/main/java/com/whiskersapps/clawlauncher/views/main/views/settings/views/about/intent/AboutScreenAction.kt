package com.whiskersapps.clawlauncher.views.main.views.settings.views.about.intent

sealed class AboutScreenAction {
    data object NavigateBack: com.whiskersapps.clawlauncher.views.main.views.settings.views.about.intent.AboutScreenAction()
    data object OpenRepoUrl: com.whiskersapps.clawlauncher.views.main.views.settings.views.about.intent.AboutScreenAction()
}