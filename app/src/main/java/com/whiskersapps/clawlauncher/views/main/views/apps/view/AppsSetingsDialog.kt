package com.whiskersapps.clawlauncher.views.main.views.apps.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.lighttigerxiv.layout_scaffold.isTablet
import com.whiskersapps.clawlauncher.R
import com.whiskersapps.clawlauncher.shared.view.theme.Typography
import com.whiskersapps.clawlauncher.views.main.views.apps.viewmodel.AppsScreenUiState
import com.whiskersapps.clawlauncher.views.main.views.apps.viewmodel.AppsScreenVM

@Composable
fun AppsSettingsDialog(
    vm: AppsScreenVM,
    uiState: AppsScreenUiState
) {

    val isTablet = isTablet()

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
                    Icon(
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { vm.updateShowSettingsDialog(false) },
                        painter = painterResource(id = R.drawable.close),
                        contentDescription = "close icon",
                        tint = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Background Opacity",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = Typography.bodyMedium
                    )

                    Slider(
                        value = uiState.opacity,
                        onValueChange = { vm.updateOpacity(it) },
                        steps = 10,
                        valueRange = 0f..1f
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    if (isTablet) {
                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Tablet Columns",
                            color = MaterialTheme.colorScheme.onBackground,
                            style = Typography.bodyMedium
                        )

                        Slider(
                            value = uiState.tabletCols.toFloat(),
                            onValueChange = { vm.updateTabletCols(it.toInt()) },
                            steps = 4,
                            valueRange = 6f..10f
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Tablet Landscape Columns",
                            color = MaterialTheme.colorScheme.onBackground,
                            style = Typography.bodyMedium
                        )

                        Slider(
                            value = uiState.tabletLandscapeCols.toFloat(),
                            onValueChange = { vm.updateTabletLandscapeCols(it.toInt()) },
                            steps = 8,
                            valueRange = 8f..12f
                        )
                    } else {
                        Text(
                            text = "Phone Columns",
                            color = MaterialTheme.colorScheme.onBackground,
                            style = Typography.bodyMedium
                        )

                        Slider(
                            value = uiState.phoneCols.toFloat(),
                            onValueChange = { vm.updatePhoneCols(it.toInt()) },
                            steps = 3,
                            valueRange = 3f..5f
                        )


                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Phone Landscape Columns",
                            color = MaterialTheme.colorScheme.onBackground,
                            style = Typography.bodyMedium
                        )

                        Slider(
                            value = uiState.phoneLandscapeCols.toFloat(),
                            onValueChange = { vm.updatePhoneLandscapeCols(it.toInt()) },
                            steps = 8,
                            valueRange = 4f..12f
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Show as grid",
                            color = MaterialTheme.colorScheme.onBackground,
                            style = Typography.bodyMedium
                        )

                        Switch(
                            checked = uiState.viewType == "grid",
                            onCheckedChange = { vm.updateViewType(if (it) "grid" else "list") }
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Icon Padding",
                            color = MaterialTheme.colorScheme.onBackground,
                            style = Typography.bodyMedium
                        )

                        Slider(
                            value = uiState.iconPadding.value,
                            onValueChange = { vm.updateIconPadding(it.toInt()) },
                            steps = 20,
                            valueRange = 4f..24f
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "SearchBar Opacity",
                            color = MaterialTheme.colorScheme.onBackground,
                            style = Typography.bodyMedium
                        )

                        Slider(
                            value = uiState.searchBarOpacity,
                            onValueChange = { vm.updateSearchBarOpacity(it) },
                            steps = 10,
                            valueRange = 0f..1f
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Show search bar",
                            color = MaterialTheme.colorScheme.onBackground,
                            style = Typography.bodyMedium
                        )

                        Switch(
                            checked = uiState.showSearchBar,
                            onCheckedChange = { vm.updateShowSearchBar(it) }
                        )

                    }
                }
            }
        }
    }
}