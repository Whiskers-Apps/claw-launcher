package com.whiskersapps.clawlauncher.views.main.views.settings.views.security.model

import com.whiskersapps.clawlauncher.shared.model.Settings

data class SecuritySettingsScreenState(
    val loading: Boolean = true,
    val settings: Settings = Settings(),
    val apps: List<com.whiskersapps.clawlauncher.shared.model.App> = emptyList(),
    val hiddenAppsDialog: HiddenAppsDialog = HiddenAppsDialog(),
    val secureAppsDialog: SecureAppsDialog = SecureAppsDialog()
) {
    data class HiddenAppsDialog(
        val show: Boolean = false,
        val selectedApps: List<String> = emptyList(),
    )

    data class SecureAppsDialog(
        val show: Boolean = false,
        val selectedApps: List<String> = emptyList(),
    )
}
