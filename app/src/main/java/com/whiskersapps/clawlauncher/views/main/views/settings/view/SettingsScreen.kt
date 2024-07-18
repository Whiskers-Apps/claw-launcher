package com.whiskersapps.clawlauncher.views.main.views.settings.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.whiskersapps.clawlauncher.shared.model.Routes
import com.whiskersapps.clawlauncher.shared.view.composables.NavBar
import com.whiskersapps.clawlauncher.views.main.views.settings.intent.SettingsScreenAction
import com.whiskersapps.clawlauncher.views.main.views.settings.model.SettingsScreenVM

@Composable
fun SettingsScreenRoot(
    navController: NavController,
    //vm: SettingsScreenVM = hiltViewModel()
) {

    SettingsScreen(onAction = { action ->
        when (action) {
            SettingsScreenAction.NavigateBack -> navController.navigateUp()
            SettingsScreenAction.NavigateToAppsSettings -> navController.navigate(Routes.Main.Settings.APPS)
            SettingsScreenAction.NavigateToBookmarksSettings -> navController.navigate(Routes.Main.Settings.BOOKMARKS)
            SettingsScreenAction.NavigateToHomeSettings -> navController.navigate(Routes.Main.Settings.HOME)
            SettingsScreenAction.NavigateToSearchEnginesSettings -> navController.navigate(Routes.Main.Settings.SEARCH_ENGINES)
            SettingsScreenAction.NavigateToSearchSettings -> navController.navigate(Routes.Main.Settings.SEARCH)
            SettingsScreenAction.NavigateToStyleSettings -> navController.navigate(Routes.Main.Settings.STYLE)
            SettingsScreenAction.NavigateToAbout -> navController.navigate(Routes.Main.Settings.ABOUT)
        }
    })
}

@Composable
fun SettingsScreen(
    onAction: (SettingsScreenAction) -> Unit
) {
    Surface(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding()
            .verticalScroll(rememberScrollState())
    ) {
        Column {

            NavBar(navigateBack = { onAction(SettingsScreenAction.NavigateBack) })

            MainSetting(
                title = "Style",
                description = "Layout, themes and icons",
                onClick = { onAction(SettingsScreenAction.NavigateToStyleSettings) }
            )

            MainSetting(
                title = "Home",
                description = "Home screen settings",
                onClick = { onAction(SettingsScreenAction.NavigateToHomeSettings) }
            )

            MainSetting(
                title = "Apps",
                description = "Apps screen settings",
                onClick = { onAction(SettingsScreenAction.NavigateToAppsSettings) }
            )

            MainSetting(
                title = "Search",
                description = "Search screen settings",
                onClick = { onAction(SettingsScreenAction.NavigateToSearchSettings) }
            )

            MainSetting(
                title = "Bookmarks",
                description = "Add website urls to quickly open them",
                onClick = { onAction(SettingsScreenAction.NavigateToBookmarksSettings) }
            )

            MainSetting(
                title = "Search Engines",
                description = "Manage search engines when searching",
                onClick = { onAction(SettingsScreenAction.NavigateToSearchEnginesSettings) }
            )

            MainSetting(
                title = "About",
                description = "App info",
                onClick = { onAction(SettingsScreenAction.NavigateToAbout) }
            )
        }
    }
}

