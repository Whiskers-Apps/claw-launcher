package com.whiskersapps.clawlauncher.views.main.views.settings.view

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.whiskersapps.clawlauncher.R
import com.whiskersapps.clawlauncher.shared.model.Routes
import com.whiskersapps.clawlauncher.shared.view.composables.ContentColumn
import com.whiskersapps.clawlauncher.shared.view.composables.NavBar
import com.whiskersapps.clawlauncher.shared.view.composables.sidePadding
import com.whiskersapps.clawlauncher.shared.view.theme.Typography
import com.whiskersapps.clawlauncher.views.main.views.settings.intent.SettingsScreenAction
import com.whiskersapps.clawlauncher.views.main.views.settings.model.SettingsScreenVM
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
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            if (!state.isDefaultLauncher) {
                Row(
                    modifier = Modifier
                        .sidePadding()
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
                            text = stringResource(R.string.SettingsScreen_default_launcher),
                            style = Typography.titleSmall,
                            color = MaterialTheme.colorScheme.onBackground,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(
                            text = stringResource(R.string.SettingsScreen_default_launcher_description),
                            style = Typography.labelMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

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

