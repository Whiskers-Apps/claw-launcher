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
import androidx.compose.ui.unit.dp
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
        description = "Shows a placeholder",
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
        title = "Search Bar Radius",
        description = "The search bar roundness",
        min = 0f,
        max = 50f,
        steps = 50,
        value = searchBarRadius,
        onValueChange = {
            onAction(HomeSettingsAction.SetSearchBarRadius(it))
        },
        onValueChangeFinished = {
            onAction(HomeSettingsAction.SaveSearchBarRadius(it))
        }
    )
}
