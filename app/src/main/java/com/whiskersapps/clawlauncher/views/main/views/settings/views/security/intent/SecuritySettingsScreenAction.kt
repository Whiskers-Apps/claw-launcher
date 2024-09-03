package com.whiskersapps.clawlauncher.views.main.views.settings.views.security.intent

sealed class SecuritySettingsScreenAction{
    data object NavigateBack: SecuritySettingsScreenAction()
    data object OpenHiddenAppsDialog: SecuritySettingsScreenAction()
    data object CloseHiddenAppsDialog: SecuritySettingsScreenAction()
    data object OpenSecureAppsDialog: SecuritySettingsScreenAction()
    data object CloseSecureAppsDialog: SecuritySettingsScreenAction()
    data class ToggleHiddenApp(val packageName: String): SecuritySettingsScreenAction()
    data class ToggleSecureApp(val packageName: String): SecuritySettingsScreenAction()
    data object SaveHiddenApps: SecuritySettingsScreenAction()
    data object SaveSecureApps: SecuritySettingsScreenAction()
}
