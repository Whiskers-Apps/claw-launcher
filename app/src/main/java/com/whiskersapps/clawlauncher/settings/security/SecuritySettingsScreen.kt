package com.whiskersapps.clawlauncher.settings.security

import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import com.whiskersapps.clawlauncher.R
import com.whiskersapps.clawlauncher.shared.view.composables.ContentColumn
import com.whiskersapps.clawlauncher.shared.view.composables.NavBar
import com.whiskersapps.clawlauncher.shared.view.composables.SimpleSetting
import org.koin.androidx.compose.koinViewModel

@Composable
fun SecuritySettingsScreenRoot(
    navController: NavController,
    vm: SecuritySettingsScreenVM = koinViewModel()
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

    val fragmentActivity = LocalActivity.current as FragmentActivity

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
            onClick = { onAction(SecuritySettingsScreenAction.OpenSecureAppsDialog(fragmentActivity)) }
        )

        HiddenAppsDialog(onAction = { onAction(it) }, state = state)

        SecureAppsDialog(onAction = { onAction(it) }, state = state)
    }
}