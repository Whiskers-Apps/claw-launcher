package com.whiskersapps.clawlauncher.launcher.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whiskersapps.clawlauncher.R
import com.whiskersapps.clawlauncher.launcher.home.composables.Clock
import com.whiskersapps.clawlauncher.launcher.home.composables.LockScreenDialog
import com.whiskersapps.clawlauncher.launcher.home.composables.Menu
import com.whiskersapps.clawlauncher.views.main.views.home.intent.Action
import com.whiskersapps.clawlauncher.views.main.views.home.intent.Action.*
import com.whiskersapps.clawlauncher.views.main.views.home.model.HomeScreenVM
import com.whiskersapps.clawlauncher.views.main.views.search.view.SearchBar
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenRoot(
    sheetState: SheetState,
    vm: HomeScreenVM = hiltViewModel()
) {
    val scope = rememberCoroutineScope()

    HomeScreen(
        onAction = { action ->
            when (action) {
                OpenSearchSheet -> scope.launch { sheetState.expand() }
                else -> vm.onAction(action)
            }
        },
        vm = vm
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    onAction: (Action) -> Unit,
    vm: HomeScreenVM = hiltViewModel()
) {
    val state = vm.state.collectAsState().value

    val textShadow = Shadow(
        Color.Black,
        offset = Offset(2f, 2f)
    )

    if (state.showMenuDialog) {
        Menu(
            onSelectWallpaper = {
                onAction(OnChangeWallpaper)
            },
            onOpenSettings = {
                onAction(OnOpenSettings)
            },
            onDismiss = {
                onAction(CloseMenuDialog)
            }
        )
    }

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
                                        if (vm.state.value.enableSwipeUp) {
                                            onAction(OpenSearchSheet)
                                        }
                                    } else {
                                        onAction(OpenNotificationPanel)
                                    }
                                }
                            }
                            .combinedClickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = { },
                                onLongClick = {
                                    onAction(OpenMenuDialog)
                                },
                                onDoubleClick = {
                                    onAction(OnLockScreen)
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
                                //TODO: pill shape setting
                                pillShape = true,
                                onClick = {
                                    onAction(OnOpenCalendar)
                                }
                            )
                        }

                        Column(
                            modifier = Modifier
                                .clickable { onAction(OpenSearchSheet) }
                        ) {
                            if (state.showSearchBar) {
                                SearchBar(
                                    enabled = false,
                                    placeholder = if (state.showPlaceholder) stringResource(R.string.Search) else "",
                                    showMenu = false,
                                    onMenuClick = {

                                    },
                                    borderRadius = state.searchBarRadius.toInt(),
                                    backgroundColor = MaterialTheme.colorScheme.background
                                )
                            } else {
                                if (state.showPlaceholder) {
                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = if (state.enableSwipeUp) stringResource(R.string.HomeScreen_placeholder) else stringResource(
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



                        LockScreenDialog(
                            show = state.showLockScreenDialog,
                            onClose = {
                                onAction(CloseLockScreenDialog)
                            },
                            onOpenSettings = {
                                onAction(CloseLockScreenDialog)
                                onAction(OnOpenLockSettings)
                            }
                        )
                    }
                }
            }
        }
    }
}