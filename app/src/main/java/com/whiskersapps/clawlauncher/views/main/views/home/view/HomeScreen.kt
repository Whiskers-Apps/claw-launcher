package com.whiskersapps.clawlauncher.views.main.views.home.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.whiskersapps.clawlauncher.R
import com.whiskersapps.clawlauncher.shared.view.theme.Typography
import com.whiskersapps.clawlauncher.views.main.views.home.intent.HomeScreenAction
import com.whiskersapps.clawlauncher.views.main.views.home.model.HomeScreenVM
import com.whiskersapps.clawlauncher.views.main.views.search.view.SearchBar
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenRoot(
    navigateToSettings: () -> Unit,
    sheetState: SheetState,
    vm: HomeScreenVM = hiltViewModel()
) {

    val scope = rememberCoroutineScope()

    HomeScreen(
        onAction = { action ->
            when (action) {
                HomeScreenAction.NavigateToSettings -> navigateToSettings()
                HomeScreenAction.OpenSearchSheet -> scope.launch { sheetState.expand() }
                else -> vm.onAction(action)
            }
        },
        vm = vm
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    onAction: (HomeScreenAction) -> Unit,
    vm: HomeScreenVM = hiltViewModel()
) {

    val state = vm.state.collectAsState().value
    val scope = rememberCoroutineScope()
    val textShadow = Shadow(
        Color.Black,
        offset = Offset(2f, 2f)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .systemBarsPadding()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = 900.dp)
                    .fillMaxSize()
            ) {

                if (!state.loading) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .pointerInput(Unit) {
                                detectVerticalDragGestures { _, dragAmount ->
                                    if (dragAmount < 0) {
                                        if (vm.state.value.swipeUpToSearch) {
                                            onAction(HomeScreenAction.OpenSearchSheet)
                                        }
                                    } else {
                                        onAction(HomeScreenAction.OpenNotificationPanel)
                                    }
                                }
                            }
                            .combinedClickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = { },
                                onLongClick = {
                                    onAction(HomeScreenAction.OpenMenuDialog)
                                },
                                onDoubleClick = {
                                    onAction(HomeScreenAction.OnLockScreen)
                                }
                            )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f, fill = true),
                            verticalArrangement = when (state.clockPlacement) {
                                "top" -> Arrangement.Top
                                "center" -> Arrangement.Center
                                else -> Arrangement.Bottom
                            }
                        ) {
                            Clock(
                                clock = state.clock,
                                date = state.date,
                                tint = state.tintClock,
                                onClick = {
                                    onAction(HomeScreenAction.OnOpenCalendar)
                                }
                            )
                        }

                        Column(
                            modifier = Modifier
                                .clickable { onAction(HomeScreenAction.OpenSearchSheet) }
                        ) {
                            if (state.showSearchBar) {
                                SearchBar(
                                    enabled = false,
                                    placeholder = if (state.showPlaceholder) stringResource(R.string.Search) else "",
                                    showMenu = state.showSearchBarSettings,
                                    onMenuClick = { onAction(HomeScreenAction.OpenSettingsDialog) },
                                    borderRadius = state.searchBarRadius.toInt(),
                                    backgroundColor = MaterialTheme.colorScheme.background
                                )
                            } else {
                                if (state.showPlaceholder) {
                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = if (state.swipeUpToSearch) stringResource(R.string.HomeScreen_placeholder) else stringResource(
                                            R.string.HomeScreen_click_here_to_search
                                        ),
                                        color = Color.White,
                                        fontSize = 18.sp,
                                        style = TextStyle(shadow = textShadow),
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }

                        if (state.showMenuDialog) {
                            Dialog(
                                onDismissRequest = { onAction(HomeScreenAction.CloseMenuDialog) },
                                properties = DialogProperties(usePlatformDefaultWidth = false)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clickable(
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = null
                                        ) {
                                            onAction(HomeScreenAction.CloseMenuDialog)
                                        },
                                    verticalArrangement = Arrangement.Bottom,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .widthIn(max = 900.dp)
                                            .padding(16.dp)
                                            .padding(bottom = 128.dp),
                                        verticalArrangement = Arrangement.Bottom,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .widthIn(900.dp)
                                                .fillMaxWidth()
                                                .clip(CircleShape)
                                                .background(MaterialTheme.colorScheme.background),
                                            horizontalArrangement = Arrangement.SpaceEvenly,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {

                                            Column(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .weight(1f, fill = true)
                                                    .clickable {
                                                        onAction(HomeScreenAction.CloseMenuDialog)
                                                        onAction(HomeScreenAction.ChangeWallpaper)
                                                    }
                                                    .padding(16.dp),
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = Arrangement.Center
                                            ) {

                                                Icon(
                                                    modifier = Modifier.size(42.dp),
                                                    painter = painterResource(id = R.drawable.landscape),
                                                    contentDescription = "landscape icon",
                                                    tint = MaterialTheme.colorScheme.onBackground
                                                )

                                                Spacer(modifier = Modifier.height(8.dp))

                                                Text(
                                                    text = stringResource(R.string.HomeScreen_change_wallpaper),
                                                    color = MaterialTheme.colorScheme.onBackground,
                                                    style = Typography.labelSmall,
                                                    textAlign = TextAlign.Center
                                                )
                                            }

                                            Column(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .weight(1f, fill = true)
                                                    .clickable {
                                                        scope.launch {
                                                            onAction(HomeScreenAction.CloseMenuDialog)
                                                            onAction(HomeScreenAction.NavigateToSettings)
                                                        }
                                                    }
                                                    .padding(16.dp),
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = Arrangement.Center
                                            ) {

                                                Icon(
                                                    modifier = Modifier.size(42.dp),
                                                    painter = painterResource(id = R.drawable.settings),
                                                    contentDescription = "settings icon",
                                                    tint = MaterialTheme.colorScheme.onBackground
                                                )

                                                Spacer(modifier = Modifier.height(8.dp))

                                                Text(
                                                    text = stringResource(R.string.HomeScreen_settings),
                                                    color = MaterialTheme.colorScheme.onBackground,
                                                    style = Typography.labelSmall,
                                                    textAlign = TextAlign.Center
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        ScreenLockDialog(
                            show = state.showScreenLockDialog,
                            serviceEnabled = state.accessibilityServiceEnabled,
                            batteryOptimized = state.batteryOptimized,
                            onDismiss = {
                                onAction(HomeScreenAction.OnCloseScreenLockDialog)
                            },
                            onOpenAppInfo = {
                                onAction(HomeScreenAction.OnOpenAppInfo)
                            },
                            onOpenAccessibilitySettings = {
                                onAction(HomeScreenAction.OnOpenAccessibilitySettings)
                            },
                            onOpenBatteryOptimizationSettings = {
                                onAction(HomeScreenAction.OnOpenBatteryOptimizationSettings)
                            },
                            onOk = {
                                onAction(HomeScreenAction.OnCloseScreenLockDialog)
                                onAction(HomeScreenAction.OnRefreshScreenLockPermissions)
                            }
                        )

                        HomeSettingsDialog(onAction = { onAction(it) }, state = state)
                    }
                }
            }
        }
    }
}