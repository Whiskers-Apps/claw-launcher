package com.whiskersapps.clawlauncher.settings.style

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.whiskersapps.clawlauncher.R
import com.whiskersapps.clawlauncher.shared.view.composables.ContentColumn
import com.whiskersapps.clawlauncher.shared.view.composables.NavBar
import com.whiskersapps.clawlauncher.shared.view.composables.SimpleSetting
import com.whiskersapps.clawlauncher.shared.view.theme.getThemeDisplayName
import com.whiskersapps.clawlauncher.settings.style.composables.DarkModeDialog
import com.whiskersapps.clawlauncher.settings.style.composables.IconPackDialog
import com.whiskersapps.clawlauncher.settings.style.composables.ThemeDialog
import org.koin.androidx.compose.koinViewModel
import com.whiskersapps.clawlauncher.settings.style.StyleSettingsScreenIntent as Intent
import com.whiskersapps.clawlauncher.settings.style.StyleSettingsScreenState as State
import com.whiskersapps.clawlauncher.settings.style.StyleSettingsScreenVM as ViewModel

@Composable
fun StyleSettingsScreenRoot(
    navController: NavController,
    vm: ViewModel = koinViewModel()
) {
    StyleSettingsScreen(
        state = vm.state.collectAsState().value
    ) { intent ->
        when (intent) {
            Intent.BackClicked -> navController.navigateUp()
            else -> vm.onAction(intent)
        }
    }
}

@Composable
fun StyleSettingsScreen(
    state: State,
    onIntent: (Intent) -> Unit,
) {
    if (state.showIconPackDialog) {
        IconPackDialog(
            onDismiss = {
                onIntent(Intent.IconPackDialogClosed)
            },
            iconPacks = state.iconPacks,
            onIconPackSelected = { iconPack ->
                onIntent(Intent.IconPackSelected(iconPack))
            }
        )
    }

    ContentColumn(
        useSystemBarsPadding = true,
        loading = state.loading,
        navigationBar = {
            NavBar(navigateBack = { onIntent(Intent.BackClicked) })
        }
    ) {

        SimpleSetting(
            title = stringResource(R.string.StyleSettings_dark_mode),
            value = getDarkModeDisplayName(state.darkMode),
            onClick = { onIntent(Intent.OpenDarkModeDialog) }
        )

        SimpleSetting(
            title = stringResource(R.string.StyleSettings_light_theme),
            value = getThemeDisplayName(state.theme),
            onClick = { onIntent(Intent.OpenThemeDialog) }
        )

        SimpleSetting(
            title = stringResource(R.string.StyleSettings_dark_theme),
            value = getThemeDisplayName(state.darkTheme),
            onClick = { onIntent(Intent.OpenDarkThemeDialog) }
        )

        SimpleSetting(
            title = "Icon Pack",
            value = state.iconPack,
            onClick = {
                onIntent(Intent.IconPackClicked)
            }
        )

        DarkModeDialog(
            show = state.showDarkModeDialog,
            onDismiss = { onIntent(Intent.CloseDarkModeDialog) },
            save = { darkMode -> onIntent(Intent.SetDarkMode(darkMode)) },
            defaultValue = state.darkMode
        )

        ThemeDialog(
            show = state.showThemeDialog,
            onDismiss = { onIntent(Intent.CloseThemeDialog) },
            state = state,
            onAction = { onIntent(it) }
        )

        ThemeDialog(
            showDarkThemes = true,
            show = state.showDarkThemeDialog,
            onDismiss = { onIntent(Intent.CloseDarkThemeDialog) },
            state = state,
            onAction = { onIntent(it) }
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