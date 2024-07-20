package com.whiskersapps.clawlauncher.views.main.views.apps.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.lighttigerxiv.layout_scaffold.isTablet
import com.whiskersapps.clawlauncher.R
import com.whiskersapps.clawlauncher.shared.view.composables.SliderSetting
import com.whiskersapps.clawlauncher.shared.view.composables.SwitchSetting
import com.whiskersapps.clawlauncher.shared.view.theme.Typography
import com.whiskersapps.clawlauncher.views.main.views.apps.intent.AppsScreenAction
import com.whiskersapps.clawlauncher.views.main.views.apps.model.AppsScreenState
import com.whiskersapps.clawlauncher.views.main.views.home.intent.HomeScreenAction

@Composable
fun AppsSettingsDialog(
    onAction: (AppsScreenAction) -> Unit,
    state: AppsScreenState
) {

    val isTablet = isTablet()

    if (state.showSettingsDialog) {
        Dialog(
            onDismissRequest = {
                onAction(AppsScreenAction.CloseSettingsDialog)
            },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {

            Surface(
                Modifier
                    .widthIn(max = 800.dp)
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
                            painter = painterResource(id = R.drawable.apps),
                            contentDescription = "home icon",
                            tint = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Apps Settings",
                            style = Typography.titleSmall,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(32.dp))

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
                                        onAction(AppsScreenAction.UpdateViewType("grid"))
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
                                            selected = state.viewType == "grid",
                                            onClick = {
                                                onAction(AppsScreenAction.UpdateViewType("grid"))
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
                                        onAction(AppsScreenAction.UpdateViewType("list"))
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
                                    for(i in 0..4){
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
                                        selected = state.viewType == "list",
                                        onClick = {
                                            onAction(AppsScreenAction.UpdateViewType("list"))
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

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(140.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Column(
                                modifier = Modifier
                                    .width(100.dp)
                                    .fillMaxHeight()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(MaterialTheme.colorScheme.background)
                                    .padding(4.dp)
                            ) {
                                LazyVerticalGrid(
                                    modifier = Modifier.fillMaxWidth(),
                                    columns = GridCells.Fixed(state.phoneCols),
                                    userScrollEnabled = false
                                ) {
                                    items(items = arrayOfNulls<Int>(5 * state.phoneCols)) {
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
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            Column(
                                modifier = Modifier
                                    .width(200.dp)
                                    .fillMaxHeight()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(MaterialTheme.colorScheme.background)
                                    .padding(4.dp)
                            ) {
                                LazyVerticalGrid(
                                    modifier = Modifier.fillMaxWidth(),
                                    columns = GridCells.Fixed(state.phoneLandscapeCols),
                                    userScrollEnabled = false
                                ) {
                                    items(items = arrayOfNulls<Int>(5 * state.phoneLandscapeCols)) {
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
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        if (isTablet) {
                            SliderSetting(
                                title = "Columns",
                                description = "The amount of columns to show when in Portrait",
                                min = 3f,
                                max = 6f,
                                steps = 3,
                                value = state.tabletCols.toFloat(),
                                onValueChange = { onAction(AppsScreenAction.UpdateTabletCols(it)) }
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            SliderSetting(
                                title = "Landscape Columns",
                                description = "The amount of columns to show when in Landscape",
                                min = 6f,
                                max = 12f,
                                steps = 6,
                                value = state.tabletLandscapeCols.toFloat(),
                                onValueChange = {
                                    onAction(AppsScreenAction.UpdateTabletLandscapeCols(it))
                                }
                            )
                        } else {
                            SliderSetting(
                                title = "Columns",
                                description = "The amount of columns to show when in Portrait",
                                min = 3f,
                                max = 6f,
                                steps = 3,
                                value = state.phoneCols.toFloat(),
                                onValueChange = { onAction(AppsScreenAction.UpdatePhoneCols(it)) }
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            SliderSetting(
                                title = "Landscape Columns",
                                description = "The amount of columns to show when in Landscape",
                                min = 6f,
                                max = 12f,
                                steps = 6,
                                value = state.phoneLandscapeCols.toFloat(),
                                onValueChange = {
                                    onAction(AppsScreenAction.UpdatePhoneLandscapeCols(it))
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
                            value = state.opacity,
                            onValueChange = { onAction(AppsScreenAction.UpdateBackgroundOpacity(it)) }
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
                                        onAction(AppsScreenAction.UpdateSearchBarPosition("bottom"))
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
                                        selected = state.searchBarPosition == "bottom",
                                        onClick = {
                                            onAction(AppsScreenAction.UpdateSearchBarPosition("bottom"))
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
                                        onAction(AppsScreenAction.UpdateSearchBarPosition("top"))
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
                                        selected = state.searchBarPosition == "top",
                                        onClick = {
                                            onAction(AppsScreenAction.UpdateSearchBarPosition("top"))
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
                            value = state.showSearchBar,
                            onValueChange = {
                                onAction(AppsScreenAction.UpdateShowSearchBar(it))
                            }
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        SwitchSetting(
                            title = "Search Bar Placeholder",
                            description = "Shows the search bar placeholder",
                            value = state.showSearchBarPlaceholder,
                            onValueChange = {
                                onAction(AppsScreenAction.UpdateShowSearchBarPlaceholder(it))
                            }
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        SwitchSetting(
                            title = "Search Bar Settings",
                            description = "Shows the search bar settings icon",
                            value = state.showSearchBarSettings,
                            onValueChange = {
                                onAction(AppsScreenAction.UpdateShowSearchBarSettings(it))
                            }
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        SliderSetting(
                            title = "Search Bar Opacity",
                            description = "The search bar opacity",
                            min = 0f,
                            max = 1f,
                            steps = 10,
                            value = state.searchBarOpacity,
                            onValueChange = {
                                onAction(AppsScreenAction.UpdateSearchBarOpacity(it))
                            }
                        )

                        SliderSetting(
                            title = "Search Bar Radius",
                            description = "The search bar roundness. (-1) is fully round",
                            min = -1f,
                            max = 32f,
                            steps = 33,
                            value = state.searchBarRadius?.value ?: -1f,
                            onValueChange = {
                                onAction(AppsScreenAction.UpdateSearchBarRadius(it))
                            }
                        )
                    }
                }
            }
        }
    }
}