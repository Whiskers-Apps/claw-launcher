package com.whiskersapps.clawlauncher.shared.view.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import com.whiskersapps.clawlauncher.R
import com.whiskersapps.clawlauncher.shared.intent.settings.HomeSettingsAction
import com.whiskersapps.clawlauncher.shared.view.composables.SliderSetting
import com.whiskersapps.clawlauncher.shared.view.composables.SwitchSetting
import com.whiskersapps.clawlauncher.shared.view.composables.sidePadding
import com.whiskersapps.clawlauncher.shared.view.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeSettings(
    tintClock: Boolean,
    swipeUpToSearch: Boolean,
    showSearchBar: Boolean,
    showPlaceholder: Boolean,
    showSettings: Boolean,
    searchBarRadius: Float,
    clockPlacement: String,
    onAction: (HomeSettingsAction) -> Unit,
) {

    SwitchSetting(
        title = stringResource(R.string.HomeSettings_tint_clock),
        description = stringResource(R.string.HomeSettings_tint_clock_description),
        value = tintClock,
        onValueChange = {
            onAction(HomeSettingsAction.SetTintIcon(it))
        }
    )

    Column(modifier = Modifier.sidePadding()) {
        Text(
            text = "Clock Placement",
            style = Typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = "Display the clock in a different position",
            style = Typography.labelMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        MultiChoiceSegmentedButtonRow {
            listOf("top", "center", "bottom").forEachIndexed { index, choice ->
                SegmentedButton(
                    checked = clockPlacement == choice.lowercase(),
                    onCheckedChange = {
                        onAction(HomeSettingsAction.SetClockPlacement(choice))
                    },
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = 3
                    ),
                    label = {
                        Text(
                            text = when (choice) {
                                "top" -> "Top"
                                "center" -> "Center"
                                else -> "Bottom"
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
        title = stringResource(R.string.HomeSettings_swipe_to_search),
        description = stringResource(R.string.HomeSettings_swipe_to_search_description),
        value = swipeUpToSearch,
        onValueChange = {
            onAction(HomeSettingsAction.SetSwipeUpToSearch(it))
        }
    )

    SwitchSetting(
        title = stringResource(R.string.HomeSettings_search_bar),
        description = stringResource(R.string.HomeSettings_search_bar_description),
        value = showSearchBar,
        onValueChange = {
            onAction(HomeSettingsAction.SetShowSearchBar(it))
        }
    )

    SwitchSetting(
        title = stringResource(R.string.HomeSettings_placeholder),
        description = stringResource(R.string.HomeSettings_placeholder_description),
        value = showPlaceholder,
        onValueChange = {
            onAction(HomeSettingsAction.SetShowSearchBarPlaceholder(it))
        }
    )

    SwitchSetting(
        title = stringResource(R.string.HomeSettings_settings),
        description = stringResource(R.string.HomeSettings_settings_description),
        value = showSettings,
        enabled = showSearchBar,
        onValueChange = {
            onAction(HomeSettingsAction.SetShowSettings(it))
        }
    )

    SliderSetting(
        title = stringResource(R.string.HomeSettings_search_bar_radius),
        description = stringResource(R.string.HomeSettings_search_bar_radius_description),
        min = 0f,
        max = 50f,
        steps = 50,
        value = searchBarRadius,
        enabled = showSearchBar,
        onValueChange = {
            onAction(HomeSettingsAction.SetSearchBarRadius(it))
        },
        onValueChangeFinished = {
            onAction(HomeSettingsAction.SaveSearchBarRadius(it))
        }
    )
}
