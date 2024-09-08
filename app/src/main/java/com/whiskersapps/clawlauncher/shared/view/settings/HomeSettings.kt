package com.whiskersapps.clawlauncher.shared.view.settings

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.whiskersapps.clawlauncher.R
import com.whiskersapps.clawlauncher.shared.intent.settings.HomeSettingsAction
import com.whiskersapps.clawlauncher.shared.view.composables.SliderSetting
import com.whiskersapps.clawlauncher.shared.view.composables.SwitchSetting

@Composable
fun HomeSettings(
    showSearchBar: Boolean,
    showPlaceholder: Boolean,
    showSettings: Boolean,
    searchBarRadius: Float,
    onAction: (HomeSettingsAction) -> Unit,
) {
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
