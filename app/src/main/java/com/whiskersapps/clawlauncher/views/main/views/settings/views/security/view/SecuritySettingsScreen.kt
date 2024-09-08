package com.whiskersapps.clawlauncher.views.main.views.settings.views.security.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.whiskersapps.clawlauncher.R
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
            title = stringResource(R.string.SecuritySettingsScreen_hidden_apps),
            value = stringResource(R.string.SecuritySettingsScreen_hidden_apps_description),
            onClick = { onAction(SecuritySettingsScreenAction.OpenHiddenAppsDialog) }
        )

        SimpleSetting(
            title = stringResource(R.string.SecuritySettingsScreen_secure_apps),
            value = stringResource(R.string.SecuritySettingsScreen_secure_apps_description),
            onClick = { onAction(SecuritySettingsScreenAction.OpenSecureAppsDialog) }
        )

        HiddenAppsDialog(onAction = {onAction(it)}, state = state)

        SecureAppsDialog(onAction = {onAction(it)}, state = state)
    }
}