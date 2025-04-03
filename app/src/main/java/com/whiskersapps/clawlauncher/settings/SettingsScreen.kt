package com.whiskersapps.clawlauncher.settings

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.whiskersapps.clawlauncher.R
import com.whiskersapps.clawlauncher.shared.model.Routes
import com.whiskersapps.clawlauncher.shared.view.composables.ContentColumn
import com.whiskersapps.clawlauncher.shared.view.composables.NavBar
import com.whiskersapps.clawlauncher.settings.composables.SectionPosition
import com.whiskersapps.clawlauncher.settings.composables.SettingsSection
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreenRoot(
    navController: NavController,
    vm: SettingsScreenVM = koinViewModel()
) {

    val context = LocalContext.current

    SettingsScreen(
        onAction = { action ->
            when (action) {
                SettingsScreenAction.NavigateBack -> {
                    (context as Activity).finish()
                }

                SettingsScreenAction.NavigateToAppsSettings -> navController.navigate(Routes.Settings.APPS)
                SettingsScreenAction.NavigateToBookmarksSettings -> navController.navigate(Routes.Settings.BOOKMARKS)
                SettingsScreenAction.NavigateToHomeSettings -> navController.navigate(Routes.Settings.HOME)
                SettingsScreenAction.NavigateToSearchEnginesSettings -> navController.navigate(
                    Routes.Settings.SEARCH_ENGINES
                )

                SettingsScreenAction.NavigateToStyleSettings -> navController.navigate(Routes.Settings.STYLE)
                SettingsScreenAction.NavigateToAbout -> navController.navigate(Routes.Settings.ABOUT)
                SettingsScreenAction.NavigateToSecuritySettings -> navController.navigate(Routes.Settings.SECURITY)
                SettingsScreenAction.NavigateToLockScreenSettings -> navController.navigate("${Routes.Settings.LOCK}/false")
                else -> vm.onAction(action)
            }
        },
        vm = vm
    )
}

@Composable
fun SettingsScreen(
    onAction: (SettingsScreenAction) -> Unit,
    vm: SettingsScreenVM
) {

    val state = vm.state.collectAsState().value

    ContentColumn(
        useSystemBarsPadding = true,
        navigationBar = {
            NavBar(navigateBack = { onAction(SettingsScreenAction.NavigateBack) })
        },
        loading = state.loading
    ) {
        Column(
            modifier = Modifier
                .padding(start = 24.dp, end = 24.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            if (!state.isDefaultLauncher) {
                SettingsSection(
                    icon = R.drawable.info,
                    title = stringResource(R.string.SettingsScreen_default_launcher),
                    description = stringResource(R.string.SettingsScreen_default_launcher_description),
                    position = SectionPosition.SINGLE,
                    onClick = {
                        onAction(SettingsScreenAction.SetDefaultLauncher)
                    }
                )
            }

            Spacer(modifier = Modifier.height(22.dp))

            SettingsSection(
                icon = R.drawable.palette,
                title = stringResource(R.string.SettingsScreen_style),
                description = stringResource(R.string.SettingsScreen_style_description),
                position = SectionPosition.TOP,
                onClick = {
                    onAction(SettingsScreenAction.NavigateToStyleSettings)
                }
            )

            SettingsSection(
                icon = R.drawable.home,
                title = stringResource(R.string.SettingsScreen_home),
                description = stringResource(R.string.SettingsScreen_home_description),
                onClick = {
                    onAction(SettingsScreenAction.NavigateToHomeSettings)
                }
            )

            SettingsSection(
                icon = R.drawable.apps,
                title = stringResource(R.string.SettingsScreen_apps),
                description = stringResource(R.string.SettingsScreen_apps_description),
                position = SectionPosition.BOTTOM,
                onClick = {
                    onAction(SettingsScreenAction.NavigateToAppsSettings)
                }
            )

            Spacer(modifier = Modifier.height(22.dp))

            SettingsSection(
                icon = R.drawable.bookmark,
                title = stringResource(R.string.SettingsScreen_bookmarks),
                description = stringResource(R.string.SettingsScreen_bookmarks_description),
                position = SectionPosition.TOP,
                onClick = {
                    onAction(SettingsScreenAction.NavigateToBookmarksSettings)
                }
            )

            SettingsSection(
                icon = R.drawable.loupe,
                title = stringResource(R.string.SettingsScreen_search_engines),
                description = stringResource(R.string.SettingsScreen_search_engines_description),
                position = SectionPosition.BOTTOM,
                onClick = {
                    onAction(SettingsScreenAction.NavigateToSearchEnginesSettings)
                }
            )

            Spacer(modifier = Modifier.height(22.dp))

            SettingsSection(
                icon = R.drawable.fingerprint,
                title = stringResource(R.string.SettingsScreen_security),
                description = stringResource(R.string.SettingsScreen_security_description),
                position = SectionPosition.TOP,
                onClick = {
                    onAction(SettingsScreenAction.NavigateToSecuritySettings)
                }
            )

            SettingsSection(
                icon = R.drawable.lock,
                title = stringResource(R.string.SettingsScreen_lock_screen),
                description = stringResource(R.string.SettingsScreen_lock_screen_description),
                position = SectionPosition.BOTTOM,
                onClick = {
                    onAction(SettingsScreenAction.NavigateToLockScreenSettings)
                }
            )

            Spacer(modifier = Modifier.height(22.dp))

            SettingsSection(
                icon = R.drawable.info,
                title = stringResource(R.string.SettingsScreen_about),
                description = stringResource(R.string.SettingsScreen_about_description),
                position = SectionPosition.SINGLE,
                onClick = {
                    onAction(SettingsScreenAction.NavigateToAbout)
                }
            )
        }
    }
}

