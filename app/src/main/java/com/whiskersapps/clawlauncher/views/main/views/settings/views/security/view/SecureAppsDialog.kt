package com.whiskersapps.clawlauncher.views.main.views.settings.views.security.view

import androidx.compose.runtime.Composable
import com.whiskersapps.clawlauncher.shared.view.composables.Dialog
import com.whiskersapps.clawlauncher.shared.view.composables.NavBar
import com.whiskersapps.clawlauncher.views.main.views.settings.views.security.intent.SecuritySettingsScreenAction
import com.whiskersapps.clawlauncher.views.main.views.settings.views.security.model.SecuritySettingsScreenState

@Composable
fun SecureAppsDialog(
    onAction: (SecuritySettingsScreenAction) -> Unit,
    state: SecuritySettingsScreenState
) {
    Dialog(
        show = state.secureAppsDialog.show,
        onDismiss = { onAction(SecuritySettingsScreenAction.CloseSecureAppsDialog) },
        fullScreen = true
    ) {
        NavBar(
            navigateBack = { onAction(SecuritySettingsScreenAction.CloseSecureAppsDialog) },
            useCloseIcon = true
        )
    }
}