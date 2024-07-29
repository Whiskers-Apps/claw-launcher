package com.whiskersapps.clawlauncher.views.main.views.settings.view.views.style.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.whiskersapps.clawlauncher.shared.view.composables.ContentColumn
import com.whiskersapps.clawlauncher.shared.view.composables.NavBar
import com.whiskersapps.clawlauncher.shared.view.composables.SimpleSetting
import com.whiskersapps.clawlauncher.views.main.views.settings.view.views.style.intent.StyleSettingsScreenAction
import com.whiskersapps.clawlauncher.views.main.views.settings.view.views.style.model.StyleSettingsScreenVM

@Composable
fun StyleSettingsScreenRoot(
    navController: NavController,
    vm: StyleSettingsScreenVM = hiltViewModel()
) {
    StyleSettingsScreen(
        onAction = { action ->
            when (action) {
                StyleSettingsScreenAction.NavigateBack -> navController.navigateUp()
                else -> vm.onAction(action)
            }
        },
        vm = vm
    )
}

@Composable
fun StyleSettingsScreen(
    onAction: (StyleSettingsScreenAction) -> Unit,
    vm: StyleSettingsScreenVM
) {

    val state = vm.state.collectAsState().value

    ContentColumn(
        loading = state.loading,
        navigationBar = {
            NavBar(navigateBack = { onAction(StyleSettingsScreenAction.NavigateBack) })
        }
    ) {
        SimpleSetting(
            title = "Icon Pack",
            value = state.currentIconPackName,
            onClick = { onAction(StyleSettingsScreenAction.OpenIconPackDialog) }
        )

        SimpleSetting(
            title = "Dark Mode",
            value = state.settings.darkMode,
            onClick = { onAction(StyleSettingsScreenAction.OpenDarkModeDialog) }
        )

        if (state.showIconPackDialog) {
            IconPackDialog(
                onDismiss = { onAction(StyleSettingsScreenAction.CloseIconPackDialog) },
                iconPacks = state.iconPacks,
                setIconPack = { iconPack -> onAction(StyleSettingsScreenAction.SetIconPack(iconPack)) }
            )
        }

        if (state.showDarkModeDialog) {
            DarkModeDialog(
                onDismiss = { onAction(StyleSettingsScreenAction.CloseDarkModeDialog) },
                save = { darkMode -> onAction(StyleSettingsScreenAction.SetDarkMode(darkMode)) },
                defaultValue = state.settings.darkMode
            )
        }
    }
}