package com.whiskersapps.clawlauncher.shared.view.settings

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.whiskersapps.clawlauncher.shared.intent.settings.HomeSettingsAction
import com.whiskersapps.clawlauncher.shared.view.composables.SliderSetting
import com.whiskersapps.clawlauncher.shared.view.composables.SwitchSetting

@Composable
fun HomeSettings(
    showSearchBar: Boolean,
    showPlaceholder: Boolean,
    showSettings: Boolean,
    searchBarOpacity: Float,
    searchBarRadius: Float?,
    onAction: (HomeSettingsAction) -> Unit,
) {
    SwitchSetting(
        title = "Search Bar",
        description = "Show the search bar",
        value = showSearchBar,
        onValueChange = {
            onAction(HomeSettingsAction.SetShowSearchBar(it))
        }
    )

    Spacer(modifier = Modifier.height(16.dp))

    SwitchSetting(
        title = "Placeholder",
        description = "Shows the placeholder on the search bar",
        value = showPlaceholder,
        onValueChange = {
            onAction(HomeSettingsAction.SetShowSearchBarPlaceholder(it))
        }
    )

    Spacer(modifier = Modifier.height(16.dp))

    SwitchSetting(
        title = "Settings",
        description = "Shows the settings icon on the search bar",
        value = showSettings,
        onValueChange = {
            onAction(HomeSettingsAction.SetShowSettings(it))
        }
    )

    Spacer(modifier = Modifier.height(16.dp))

    SliderSetting(
        title = "Search Bar Opacity",
        description = "The search bar opacity",
        min = 0f,
        max = 1f,
        steps = 10,
        value = searchBarOpacity,
        onValueChange = {
            onAction(HomeSettingsAction.SetSearchBarOpacity(it))
        }
    )

    Spacer(modifier = Modifier.height(16.dp))

    SliderSetting(
        title = "Search Bar Radius",
        description = "The search bar roundness. (-1) is fully round",
        min = -1f,
        max = 32f,
        steps = 33,
        value = searchBarRadius ?: -1f,
        onValueChange = {
            onAction(HomeSettingsAction.SetSearchBarRadius(it))
        }
    )
}
