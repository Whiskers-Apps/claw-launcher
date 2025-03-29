package com.whiskersapps.clawlauncher.views.main.views.settings.views.style.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.whiskersapps.clawlauncher.R
import com.whiskersapps.clawlauncher.shared.view.composables.ContentColumn
import com.whiskersapps.clawlauncher.shared.view.composables.NavBar
import com.whiskersapps.clawlauncher.shared.view.composables.SimpleSetting
import com.whiskersapps.clawlauncher.shared.view.theme.getThemeDisplayName
import com.whiskersapps.clawlauncher.views.main.views.settings.views.style.intent.StyleSettingsScreenAction
import com.whiskersapps.clawlauncher.views.main.views.settings.views.style.model.StyleSettingsScreenVM
import org.koin.androidx.compose.koinViewModel

@Composable
fun StyleSettingsScreenRoot(
    navController: NavController,
    vm: StyleSettingsScreenVM = koinViewModel()
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
        useSystemBarsPadding = true,
        loading = state.loading,
        navigationBar = {
            NavBar(navigateBack = { onAction(StyleSettingsScreenAction.NavigateBack) })
        }
    ) {

        SimpleSetting(
            title = stringResource(R.string.StyleSettings_dark_mode),
            value = getDarkModeDisplayName(state.settings.darkMode),
            onClick = { onAction(StyleSettingsScreenAction.OpenDarkModeDialog) }
        )

        SimpleSetting(
            title = stringResource(R.string.StyleSettings_light_theme),
            value = getThemeDisplayName(state.settings.theme),
            onClick = { onAction(StyleSettingsScreenAction.OpenThemeDialog) }
        )

        SimpleSetting(
            title = stringResource(R.string.StyleSettings_dark_theme),
            value = getThemeDisplayName(state.settings.darkTheme),
            onClick = { onAction(StyleSettingsScreenAction.OpenDarkThemeDialog) }
        )

        DarkModeDialog(
            show = state.showDarkModeDialog,
            onDismiss = { onAction(StyleSettingsScreenAction.CloseDarkModeDialog) },
            save = { darkMode -> onAction(StyleSettingsScreenAction.SetDarkMode(darkMode)) },
            defaultValue = state.settings.darkMode
        )

        ThemeDialog(
            show = state.showThemeDialog,
            onDismiss = { onAction(StyleSettingsScreenAction.CloseThemeDialog) },
            state = state,
            onAction = { onAction(it) }
        )

        ThemeDialog(
            showDarkThemes = true,
            show = state.showDarkThemeDialog,
            onDismiss = { onAction(StyleSettingsScreenAction.CloseDarkThemeDialog) },
            state = state,
            onAction = { onAction(it) }
        )
    }
}

@Composable
fun getDarkModeDisplayName(theme: String): String {
    return when (theme) {
        "system" -> stringResource(R.string.StyleSettings_system)
        "light" -> stringResource(R.string.StyleSettings_light)
        else -> stringResource(R.string.StyleSettings_dark)
    }
}