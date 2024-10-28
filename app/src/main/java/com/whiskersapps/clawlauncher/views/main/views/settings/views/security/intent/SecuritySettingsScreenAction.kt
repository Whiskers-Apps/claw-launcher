package com.whiskersapps.clawlauncher.views.main.views.settings.views.security.intent

import androidx.fragment.app.FragmentActivity

sealed class SecuritySettingsScreenAction{
    data object NavigateBack: SecuritySettingsScreenAction()
    data object OpenHiddenAppsDialog: SecuritySettingsScreenAction()
    data object CloseHiddenAppsDialog: SecuritySettingsScreenAction()
    data class OpenSecureAppsDialog(val fragmentActivity: FragmentActivity): SecuritySettingsScreenAction()
    data object CloseSecureAppsDialog: SecuritySettingsScreenAction()
    data class ToggleHiddenApp(val packageName: String): SecuritySettingsScreenAction()
    data class ToggleSecureApp(val packageName: String): SecuritySettingsScreenAction()
    data object SaveHiddenApps: SecuritySettingsScreenAction()
    data object SaveSecureApps: SecuritySettingsScreenAction()
}
