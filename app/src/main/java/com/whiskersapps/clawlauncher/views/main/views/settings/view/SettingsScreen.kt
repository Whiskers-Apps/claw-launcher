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
import com.whiskersapps.clawlauncher.shared.view.composables.NavBar

@Composable
fun SettingsScreen(
    navigateBack: () -> Unit,
    navigateToStyleSettings: () -> Unit,
    navigateToHomeSettings: () -> Unit,
    navigateToAppsSettings: () -> Unit,
    navigateToSearchSettings: () -> Unit,
    navigateToBookmarksSettings: () -> Unit,
    navigateToSearchEnginesSettings: () -> Unit,
    navigateToAboutSettings: () -> Unit
) {
    Surface(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding()
            .verticalScroll(rememberScrollState())
    ) {
        Column {

            NavBar(navigateBack = { navigateBack() })

            MainSetting(
                title = "Style",
                description = "Layout, themes and icons",
                onClick = { navigateToStyleSettings() }
            )

            MainSetting(
                title = "Home",
                description = "Home screen settings",
                onClick = { navigateToHomeSettings() }
            )

            MainSetting(
                title = "Apps",
                description = "Apps screen settings",
                onClick = { navigateToAppsSettings() }
            )

            MainSetting(
                title = "Search",
                description = "Search screen settings",
                onClick = { navigateToSearchSettings() }
            )

            MainSetting(
                title = "Bookmarks",
                description = "Add website urls to quickly open them",
                onClick = { navigateToBookmarksSettings() }
            )

            MainSetting(
                title = "Search Engines",
                description = "Manage search engines when searching",
                onClick = { navigateToSearchEnginesSettings() }
            )

            MainSetting(
                title = "About",
                description = "App info",
                onClick = { navigateToAboutSettings() }
            )
        }
    }
}

