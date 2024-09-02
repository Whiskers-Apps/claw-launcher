package com.whiskersapps.clawlauncher.views.main.views.settings.views.security.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.whiskersapps.clawlauncher.shared.view.composables.ContentColumn
import com.whiskersapps.clawlauncher.shared.view.composables.NavBar
import com.whiskersapps.clawlauncher.shared.view.composables.SimpleSetting
import com.whiskersapps.clawlauncher.views.main.views.settings.views.security.intent.SecuritySettingsScreenAction
import com.whiskersapps.clawlauncher.views.main.views.settings.views.security.model.SecuritySettingsScreenVM
import com.whiskersapps.clawlauncher.views.main.views.settings.views.security.model.SecuritySettingsScreenState

@Composable
fun SecuritySettingsScreenRoot(
    navController: NavController,
    vm: SecuritySettingsScreenVM = hiltViewModel()
) {
    SecuritySettingsScreen(
        onAction = { action ->
            when (action) {
                SecuritySettingsScreenAction.NavigateBack -> navController.navigateUp()
                else -> vm.onAction(action)
            }
        },
        state = vm.state.collectAsState().value
    )
}

@Composable
fun SecuritySettingsScreen(
    onAction: (SecuritySettingsScreenAction) -> Unit,
    state: SecuritySettingsScreenState
) {
    ContentColumn(useSystemBarsPadding = true) {
        NavBar(navigateBack = { onAction(SecuritySettingsScreenAction.NavigateBack) })

        SimpleSetting(
            title = "Hidden Apps",
            value = "Click to hidde apps",
            onClick = { onAction(SecuritySettingsScreenAction.OpenHiddenAppsDialog) }
        )

        SimpleSetting(
            title = "Secure Apps",
            value = "Click to secure apps",
            onClick = { onAction(SecuritySettingsScreenAction.OpenSecureAppsDialog) }
        )

        HiddenAppsDialog(onAction = {onAction(it)}, state = state)

        SecureAppsDialog(onAction = {onAction(it)}, state = state)
    }
}