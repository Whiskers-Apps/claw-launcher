package com.whiskersapps.clawlauncher.settings.about

sealed interface AboutScreenAction {
    data object NavigateBack : AboutScreenAction
    data object OpenRepoUrl : AboutScreenAction
    data object OpenDonateLink : AboutScreenAction
}