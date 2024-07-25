package com.whiskersapps.clawlauncher.shared.view.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whiskersapps.clawlauncher.shared.intent.settings.AppsSettingsAction
import com.whiskersapps.clawlauncher.shared.view.composables.SliderSetting
import com.whiskersapps.clawlauncher.shared.view.composables.SwitchSetting
import com.whiskersapps.clawlauncher.shared.view.theme.Typography

@Composable
fun AppsSettings(
    isFoldable: Boolean,
    viewType: String,
    phoneCols: Int,
    phoneLandscapeCols: Int,
    unfoldedCols: Int,
    unfoldedLandscapeCols: Int,
    backgroundOpacity: Float,
    searchBarPosition: String,
    showSearchBar: Boolean,
    showSearchBarPlaceholder: Boolean,
    showSearchBarSettings: Boolean,
    searchBarOpacity: Float,
    searchBarRadius: Float?,
    onAction: (AppsSettingsAction) -> Unit
) {

    Text(
        modifier = Modifier.fillMaxWidth(),
        text = "View",
        style = Typography.bodyMedium,
        color = MaterialTheme.colorScheme.onBackground
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .clickable {
                    onAction(AppsSettingsAction.SetViewType("grid"))
                }
                .padding(8.dp)
        ) {
            Column {

                LazyVerticalGrid(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.background),
                    columns = GridCells.Fixed(4),
                    userScrollEnabled = false
                ) {
                    items(items = arrayOfNulls<Int>(20)) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .padding(4.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = viewType == "grid",
                        onClick = {
                            onAction(AppsSettingsAction.SetViewType("grid"))
                        }
                    )

                    Text(
                        text = "Grid",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 12.sp
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .clickable {
                    onAction(AppsSettingsAction.SetViewType("list"))
                }
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .padding(4.dp)
            ) {
                for (i in 0..4) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = viewType == "list",
                    onClick = {
                        onAction(AppsSettingsAction.SetViewType("list"))
                    }
                )

                Text(
                    text = "List",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 12.sp
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    Spacer(modifier = Modifier.height(16.dp))

    SliderSetting(
        title = "Columns",
        description = "The amount of columns to show when in Portrait",
        min = 3f,
        max = 8f,
        steps = 6,
        value = phoneCols.toFloat(),
        onValueChange = { onAction(AppsSettingsAction.SetPhoneCols(it)) }
    )
    Spacer(modifier = Modifier.height(16.dp))

    SliderSetting(
        title = "Landscape Columns",
        description = "The amount of columns to show when in Landscape",
        min = 3f,
        max = 8f,
        steps = 6,
        value = phoneLandscapeCols.toFloat(),
        onValueChange = {
            onAction(AppsSettingsAction.SetPhoneLandscapeCols(it))
        }
    )

    if (isFoldable) {
        SliderSetting(
            title = "Unfolded Columns",
            description = "The amount of columns to show when in Portrait and the device is unfolded",
            min = 3f,
            max = 8f,
            steps = 6,
            value = unfoldedCols.toFloat(),
            onValueChange = { onAction(AppsSettingsAction.SetUnfoldedCols(it)) }
        )
        Spacer(modifier = Modifier.height(16.dp))

        SliderSetting(
            title = "Unfolded Landscape Columns",
            description = "The amount of columns to show when in Landscape and the device is unfolded",
            min = 3f,
            max = 8f,
            steps = 6,
            value = unfoldedLandscapeCols.toFloat(),
            onValueChange = {
                onAction(AppsSettingsAction.SetUnfoldedLandscapeCols(it))
            }
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    SliderSetting(
        title = "Background Opacity",
        description = "The background opacity",
        min = 0f,
        max = 1f,
        steps = 10,
        value = backgroundOpacity,
        onValueChange = { onAction(AppsSettingsAction.SetBackgroundOpacity(it)) }
    )

    Spacer(modifier = Modifier.height(16.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .clickable {
                    onAction(AppsSettingsAction.SetSearchBarPosition("bottom"))
                }
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .padding(4.dp)
            ) {

                Spacer(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f, fill = true)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = searchBarPosition == "bottom",
                    onClick = {
                        onAction(AppsSettingsAction.SetSearchBarPosition("bottom"))
                    }
                )

                Text(
                    text = "Bottom",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 12.sp
                )
            }
        }

        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .clickable {
                    onAction(AppsSettingsAction.SetSearchBarPosition("top"))
                }
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .padding(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = searchBarPosition == "top",
                    onClick = {
                        onAction(AppsSettingsAction.SetSearchBarPosition("top"))
                    }
                )

                Text(
                    text = "Top",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 12.sp
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    SwitchSetting(
        title = "Search Bar",
        description = "Shows the search bar",
        value = showSearchBar,
        onValueChange = {
            onAction(AppsSettingsAction.SetShowSearchBar(it))
        }
    )

    Spacer(modifier = Modifier.height(16.dp))

    SwitchSetting(
        title = "Search Bar Placeholder",
        description = "Shows the search bar placeholder",
        value = showSearchBarPlaceholder,
        onValueChange = {
            onAction(AppsSettingsAction.SetShowSearchBarPlaceholder(it))
        }
    )

    Spacer(modifier = Modifier.height(16.dp))

    SwitchSetting(
        title = "Search Bar Settings",
        description = "Shows the search bar settings icon",
        value = showSearchBarSettings,
        onValueChange = {
            onAction(AppsSettingsAction.SetShowSearchBarSettings(it))
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
            onAction(AppsSettingsAction.SetSearchBarOpacity(it))
        }
    )

    SliderSetting(
        title = "Search Bar Radius",
        description = "The search bar roundness. (-1) is fully round",
        min = -1f,
        max = 32f,
        steps = 33,
        value = searchBarRadius ?: -1f,
        onValueChange = {
            onAction(AppsSettingsAction.SetSearchBarRadius(it))
        }
    )
}