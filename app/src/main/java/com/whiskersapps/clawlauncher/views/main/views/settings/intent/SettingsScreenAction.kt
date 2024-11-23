package com.whiskersapps.clawlauncher.views.main.views.settings.intent

sealed class SettingsScreenAction {
    data object SetDefaultLauncher: SettingsScreenAction()
    data object NavigateBack : SettingsScreenAction()
    data object NavigateToStyleSettings: SettingsScreenAction()
    data object NavigateToHomeSettings: SettingsScreenAction()
    data object NavigateToAppsSettings: SettingsScreenAction()
    data object NavigateToBookmarksSettings: SettingsScreenAction()
    data object NavigateToSearchEnginesSettings: SettingsScreenAction()
    data object NavigateToSecuritySettings: SettingsScreenAction()
    data object NavigateToAbout: SettingsScreenAction()
    data object NavigateToLockScreenSettings: SettingsScreenAction()
}