package com.whiskersapps.clawlauncher.views.main.views.settings.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.whiskersapps.clawlauncher.R
import com.whiskersapps.clawlauncher.shared.model.Routes
import com.whiskersapps.clawlauncher.shared.view.composables.ContentColumn
import com.whiskersapps.clawlauncher.shared.view.composables.NavBar
import com.whiskersapps.clawlauncher.shared.view.theme.Typography
import com.whiskersapps.clawlauncher.views.main.views.settings.intent.SettingsScreenAction
import com.whiskersapps.clawlauncher.views.main.views.settings.model.SettingsScreenVM

@Composable
fun SettingsScreenRoot(
    navController: NavController,
    vm: SettingsScreenVM = hiltViewModel()
) {

    SettingsScreen(
        onAction = { action ->
            when (action) {
                SettingsScreenAction.NavigateBack -> navController.navigateUp()
                SettingsScreenAction.NavigateToAppsSettings -> navController.navigate(Routes.Main.Settings.APPS)
                SettingsScreenAction.NavigateToBookmarksSettings -> navController.navigate(Routes.Main.Settings.BOOKMARKS)
                SettingsScreenAction.NavigateToHomeSettings -> navController.navigate(Routes.Main.Settings.HOME)
                SettingsScreenAction.NavigateToSearchEnginesSettings -> navController.navigate(
                    Routes.Main.Settings.SEARCH_ENGINES
                )

                SettingsScreenAction.NavigateToStyleSettings -> navController.navigate(Routes.Main.Settings.STYLE)
                SettingsScreenAction.NavigateToAbout -> navController.navigate(Routes.Main.Settings.ABOUT)
                SettingsScreenAction.NavigateToSecuritySettings -> navController.navigate(Routes.Main.Settings.SECURITY)
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
        if (!state.isDefaultLauncher) {
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .clickable { onAction(SettingsScreenAction.SetDefaultLauncher) }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    modifier = Modifier.size(32.dp),
                    painter = painterResource(id = R.drawable.warning),
                    contentDescription = "warning icon",
                    tint = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f, fill = true)
                ) {
                    Text(
                        text = "Default Launcher",
                        style = Typography.titleSmall,
                        color = MaterialTheme.colorScheme.onBackground,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = "Click to set as default launcher",
                        style = Typography.labelMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

        MainSetting(
            icon = R.drawable.palette,
            title = "Style",
            description = "Themes",
            onClick = { onAction(SettingsScreenAction.NavigateToStyleSettings) }
        )

        MainSetting(
            icon = R.drawable.home,
            title = "Home",
            description = "Home screen settings",
            onClick = { onAction(SettingsScreenAction.NavigateToHomeSettings) }
        )

        MainSetting(
            icon = R.drawable.apps,
            title = "Apps",
            description = "Apps screen settings",
            onClick = { onAction(SettingsScreenAction.NavigateToAppsSettings) }
        )

        MainSetting(
            icon = R.drawable.bookmark,
            title = "Bookmarks",
            description = "Add website urls to quickly open them",
            onClick = { onAction(SettingsScreenAction.NavigateToBookmarksSettings) }
        )

        MainSetting(
            icon = R.drawable.loupe,
            title = "Search Engines",
            description = "Manage search engines when searching",
            onClick = { onAction(SettingsScreenAction.NavigateToSearchEnginesSettings) }
        )

        MainSetting(
            icon = R.drawable.lock,
            title = "Security",
            description = "Hide or protect apps",
            onClick = { onAction(SettingsScreenAction.NavigateToSecuritySettings) }
        )

        MainSetting(
            icon = R.drawable.info,
            title = "About",
            description = "App info",
            onClick = { onAction(SettingsScreenAction.NavigateToAbout) }
        )
    }
}

