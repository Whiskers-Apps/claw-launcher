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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whiskersapps.clawlauncher.R
import com.whiskersapps.clawlauncher.shared.intent.settings.AppsSettingsAction
import com.whiskersapps.clawlauncher.shared.view.composables.SliderSetting
import com.whiskersapps.clawlauncher.shared.view.composables.SwitchSetting
import com.whiskersapps.clawlauncher.shared.view.composables.sidePadding
import com.whiskersapps.clawlauncher.shared.view.theme.Typography

@Composable
fun AppsSettings(
    isFoldable: Boolean,
    disableAppsScreen: Boolean,
    viewType: String,
    phoneCols: Int,
    phoneLandscapeCols: Int,
    unfoldedCols: Int,
    unfoldedLandscapeCols: Int,
    searchBarPosition: String,
    showSearchBar: Boolean,
    showSearchBarPlaceholder: Boolean,
    showSearchBarSettings: Boolean,
    searchBarRadius: Int,
    onAction: (AppsSettingsAction) -> Unit
) {

    SwitchSetting(
        title = stringResource(R.string.AppsSettings_disable_apps_screen),
        description = stringResource(R.string.AppsSettings_disable_apps_screen_description),
        value = disableAppsScreen,
        onValueChange = { disable -> onAction(AppsSettingsAction.SetDisableAppsScreen(disable)) }
    )

    Column(modifier = Modifier.sidePadding()) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.AppsSettings_view),
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
                            text = stringResource(R.string.AppsSettings_grid),
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
                        text = stringResource(R.string.AppsSettings_list),
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }

    if (viewType == "grid") {
        Spacer(modifier = Modifier.height(16.dp))

        SliderSetting(
            title = stringResource(R.string.AppsSettings_columns),
            description = stringResource(R.string.AppsSettings_columns_description),
            min = 3f,
            max = 10f,
            steps = 8,
            value = phoneCols.toFloat(),
            onValueChange = { onAction(AppsSettingsAction.SetPhoneCols(it)) }
        )

        SliderSetting(
            title = stringResource(R.string.AppsSettings_landscape_columns),
            description = stringResource(R.string.AppsSettings_landscape_columns_description),
            min = 3f,
            max = 10f,
            steps = 8,
            value = phoneLandscapeCols.toFloat(),
            onValueChange = {
                onAction(AppsSettingsAction.SetPhoneLandscapeCols(it))
            }
        )

        if (isFoldable) {
            SliderSetting(
                title = stringResource(R.string.AppsSettings_unfolded_columns),
                description = stringResource(R.string.AppsSettings_columns_description),
                min = 3f,
                max = 10f,
                steps = 8,
                value = unfoldedCols.toFloat(),
                onValueChange = { onAction(AppsSettingsAction.SetUnfoldedCols(it)) }
            )

            SliderSetting(
                title = stringResource(R.string.AppsSettings_unfolded_landscape_columns),
                description = stringResource(R.string.AppsSettings_landscape_columns_description),
                min = 3f,
                max = 10f,
                steps = 8,
                value = unfoldedLandscapeCols.toFloat(),
                onValueChange = {
                    onAction(AppsSettingsAction.SetUnfoldedLandscapeCols(it))
                }
            )
        }
    } else {
        Spacer(modifier = Modifier.height(16.dp))
    }

    Column(modifier = Modifier.sidePadding()) {

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.AppsSettings_search_bar_position),
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
                        text = stringResource(R.string.AppsSettings_bottom),
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
                        text = stringResource(R.string.AppsSettings_top),
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }

    SwitchSetting(
        title = stringResource(R.string.AppsSettings_search_bar),
        description = stringResource(R.string.AppsSettings_search_bar_description),
        value = showSearchBar,
        onValueChange = {
            onAction(AppsSettingsAction.SetShowSearchBar(it))
        }
    )

    SwitchSetting(
        title = stringResource(R.string.AppsSettings_search_bar_placeholder),
        description = stringResource(R.string.AppsSettings_search_bar_placeholder_description),
        value = showSearchBarPlaceholder,
        enabled = showSearchBar,
        onValueChange = {
            onAction(AppsSettingsAction.SetShowSearchBarPlaceholder(it))
        }
    )

    SwitchSetting(
        title = stringResource(R.string.AppsSettings_search_bar_settings),
        description = stringResource(R.string.AppsSettings_search_bar_settings_description),
        value = showSearchBarSettings,
        enabled = showSearchBar,
        onValueChange = {
            onAction(AppsSettingsAction.SetShowSearchBarSettings(it))
        }
    )

    SliderSetting(
        title = stringResource(R.string.AppsSettings_search_bar_radius),
        description = stringResource(R.string.AppsSettings_search_bar_radius_description),
        min = 0f,
        max = 50f,
        steps = 50,
        value = searchBarRadius.toFloat(),
        enabled = showSearchBar,
        onValueChange = {
            onAction(AppsSettingsAction.SetSearchBarRadius(it))
        }
    )
}