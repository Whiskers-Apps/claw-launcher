package com.whiskersapps.clawlauncher.views.main.views.home.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.whiskersapps.clawlauncher.R
import com.whiskersapps.clawlauncher.shared.view.composables.SliderSetting
import com.whiskersapps.clawlauncher.shared.view.composables.SwitchSetting
import com.whiskersapps.clawlauncher.shared.view.theme.Typography
import com.whiskersapps.clawlauncher.views.main.views.home.viewmodel.HomeScreenUiState
import com.whiskersapps.clawlauncher.views.main.views.home.viewmodel.HomeScreenVM

@Composable
fun HomeSettingsDialog(
    vm: HomeScreenVM,
    uiState: HomeScreenUiState
) {
    if (uiState.showSettingsDialog) {
        Dialog(
            onDismissRequest = { vm.updateShowSettingsDialog(false) },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {

            Surface(
                Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .padding(24.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            painter = painterResource(id = R.drawable.home),
                            contentDescription = "home icon",
                            tint = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Home Settings",
                            style = Typography.titleSmall,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        SwitchSetting(
                            title = "Search Bar",
                            description = "Show the search bar",
                            value = uiState.showSearchBar,
                            onValueChange = { vm.updateShowSearchBar(it) }
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        SwitchSetting(
                            title = "Placeholder",
                            description = "Shows the placeholder on the search bar",
                            value = uiState.showPlaceholder,
                            onValueChange = { vm.updateShowSearchBarPlaceholder(it) }
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        SwitchSetting(
                            title = "Settings",
                            description = "Shows the settings icon on the search bar",
                            value = uiState.showSettings,
                            onValueChange = { vm.updateShowSettings(it) }
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        SliderSetting(
                            title = "Search Bar Opacity",
                            description = "The search bar opacity",
                            min = 0f,
                            max = 1f,
                            steps = 10,
                            value = uiState.searchBarOpacity,
                            onValueChange = {vm.updateSearchBarOpacity(it)}
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        SliderSetting(
                            title = "Search Bar Radius",
                            description = "The search bar roundness. (-1) is fully round",
                            min = -1f,
                            max = 32f,
                            steps = 33,
                            value = uiState.searchBarRadius?.value ?: -1f,
                            onValueChange = {vm.updateSearchBarRadius(it)}
                        )
                    }
                }
            }
        }
    }
}