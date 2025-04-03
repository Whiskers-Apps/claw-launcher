package com.whiskersapps.clawlauncher.settings.apps

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.whiskersapps.clawlauncher.R
import com.whiskersapps.clawlauncher.shared.view.composables.ContentColumn
import com.whiskersapps.clawlauncher.shared.view.composables.NavBar
import com.whiskersapps.clawlauncher.shared.view.composables.SliderSetting
import com.whiskersapps.clawlauncher.shared.view.composables.SwitchSetting
import com.whiskersapps.clawlauncher.shared.view.composables.settingPadding
import com.whiskersapps.clawlauncher.shared.view.theme.REGULAR_LABEL_STYLE
import com.whiskersapps.clawlauncher.shared.view.theme.SMALL_LABEL_STYLE
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppsSettingsScreenRoot(
    navController: NavController,
    vm: AppsSettingsScreenVM = koinViewModel()
) {

    AppsSettingsScreen(
        onAction = { action ->
            when (action) {
                AppsSettingsScreenAction.NavigateBack -> navController.navigateUp()
                else -> vm.onAction(action)
            }
        },
        vm = vm
    )
}

@Composable
fun AppsSettingsScreen(
    onAction: (AppsSettingsScreenAction) -> Unit,
    vm: AppsSettingsScreenVM,
    state: AppsSettingsScreenState = vm.state.collectAsState().value
) {
    ContentColumn(
        useSystemBarsPadding = true,
        navigationBar = {
            NavBar(navigateBack = { onAction(AppsSettingsScreenAction.NavigateBack) })
        },
        loading = state.loading
    ) {

        SwitchSetting(
            title = stringResource(R.string.AppsSettings_disable_apps_screen),
            description = stringResource(R.string.AppsSettings_disable_apps_screen_description),
            value = state.disableAppsScreen,
            onValueChange = { disable ->
                onAction(
                    AppsSettingsScreenAction.SetDisableAppsScreen(
                        disable
                    )
                )
            }
        )



        Column(modifier = Modifier.settingPadding()) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.AppsSettings_view),
                style = REGULAR_LABEL_STYLE,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.AppsSettings_view_description),
                style = SMALL_LABEL_STYLE,
                color = MaterialTheme.colorScheme.onBackground
            )

            MultiChoiceSegmentedButtonRow {
                listOf("grid", "list").forEachIndexed { index, choice ->
                    SegmentedButton(
                        checked = state.viewType == choice,
                        onCheckedChange = {
                            onAction(AppsSettingsScreenAction.SetViewType(choice))
                        },
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = 2
                        ),
                        label = {
                            Text(
                                text = when (choice) {
                                    "grid" -> "Grid"
                                    else -> "List"
                                }
                            )
                        },
                        colors = SegmentedButtonDefaults.colors(
                            activeContainerColor = MaterialTheme.colorScheme.primary,
                            activeContentColor = MaterialTheme.colorScheme.onPrimary,
                            activeBorderColor = Color.Transparent
                        )
                    )
                }
            }
        }

        if (state.viewType == "grid") {
            Spacer(modifier = Modifier.height(16.dp))

            SliderSetting(
                title = stringResource(R.string.AppsSettings_columns),
                description = stringResource(R.string.AppsSettings_columns_description),
                min = 3f,
                max = 10f,
                steps = 8,
                value = state.portraitColumns,
                onValueChange = { onAction(AppsSettingsScreenAction.SetPortraitColumns(it)) }
            )

            SliderSetting(
                title = stringResource(R.string.AppsSettings_landscape_columns),
                description = stringResource(R.string.AppsSettings_landscape_columns_description),
                min = 3f,
                max = 10f,
                steps = 8,
                value = state.landscapeColumns,
                onValueChange = {
                    onAction(AppsSettingsScreenAction.SetLandscapeColumns(it))
                }
            )

            if (state.isFoldable) {
                SliderSetting(
                    title = stringResource(R.string.AppsSettings_unfolded_columns),
                    description = stringResource(R.string.AppsSettings_columns_description),
                    min = 3f,
                    max = 10f,
                    steps = 8,
                    value = state.unfoldedPortraitColumns,
                    onValueChange = {
                        onAction(
                            AppsSettingsScreenAction.SetUnfoldedPortraitColumns(
                                it
                            )
                        )
                    }
                )

                SliderSetting(
                    title = stringResource(R.string.AppsSettings_unfolded_landscape_columns),
                    description = stringResource(R.string.AppsSettings_landscape_columns_description),
                    min = 3f,
                    max = 10f,
                    steps = 8,
                    value = state.unfoldedLandscapeColumns,
                    onValueChange = {
                        onAction(AppsSettingsScreenAction.SetUnfoldedLandscapeColumns(it))
                    }
                )
            }
        } else {
            Spacer(modifier = Modifier.height(16.dp))

            SwitchSetting(
                title = stringResource(R.string.AppsSettings_split_list),
                description = stringResource(R.string.AppsSettings_split_list_description),
                value = state.splitList,
                onValueChange = {
                    onAction(AppsSettingsScreenAction.SetSplitList(it))
                }
            )
        }

        Column(modifier = Modifier.settingPadding()) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.AppsSettings_search_bar_position),
                style = REGULAR_LABEL_STYLE,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.AppsSettings_search_bar_position_description),
                style = SMALL_LABEL_STYLE,
                color = MaterialTheme.colorScheme.onBackground
            )

            MultiChoiceSegmentedButtonRow {
                listOf("bottom", "top").forEachIndexed { index, choice ->
                    SegmentedButton(
                        checked = state.searchBarPosition == choice,
                        onCheckedChange = {
                            onAction(AppsSettingsScreenAction.SetSearchBarPosition(choice))
                        },
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = 2
                        ),
                        label = {
                            Text(
                                text = when (choice) {
                                    "bottom" -> "Bottom"
                                    else -> "Top"
                                }
                            )
                        },
                        colors = SegmentedButtonDefaults.colors(
                            activeContainerColor = MaterialTheme.colorScheme.primary,
                            activeContentColor = MaterialTheme.colorScheme.onPrimary,
                            activeBorderColor = Color.Transparent
                        )
                    )
                }
            }
        }



        SwitchSetting(
            title = stringResource(R.string.AppsSettings_search_bar),
            description = stringResource(R.string.AppsSettings_search_bar_description),
            value = state.showSearchBar,
            onValueChange = {
                onAction(AppsSettingsScreenAction.SetShowSearchBar(it))
            }
        )

        SwitchSetting(
            title = stringResource(R.string.AppsSettings_search_bar_placeholder),
            description = stringResource(R.string.AppsSettings_search_bar_placeholder_description),
            value = state.showSearchBarPlaceholder,
            enabled = state.showSearchBar,
            onValueChange = {
                onAction(AppsSettingsScreenAction.SetShowSearchBarPlaceholder(it))
            }
        )

        SliderSetting(
            title = stringResource(R.string.AppsSettings_search_bar_radius),
            description = stringResource(R.string.AppsSettings_search_bar_radius_description),
            min = 0f,
            max = 50f,
            steps = 50,
            value = state.searchBarRadius,
            enabled = state.showSearchBar,
            onValueChange = {
                onAction(AppsSettingsScreenAction.SetSearchBarRadius(it))
            }
        )
    }
}