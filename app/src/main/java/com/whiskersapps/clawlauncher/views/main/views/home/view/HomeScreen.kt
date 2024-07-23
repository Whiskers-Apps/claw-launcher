package com.whiskersapps.clawlauncher.views.main.views.home.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
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
        }, vm = vm
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    onAction: (HomeScreenAction) -> Unit,
    vm: HomeScreenVM = hiltViewModel()
) {

    val state = vm.uiState.collectAsState().value
    val scope = rememberCoroutineScope()

    state?.let {

        Box(contentAlignment = Alignment.Center) {

            Column(modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .pointerInput(Unit) {
                    detectVerticalDragGestures { _, dragAmount ->
                        if (dragAmount < 0) {
                            onAction(HomeScreenAction.OpenSearchSheet)
                        } else {
                            onAction(HomeScreenAction.OpenNotificationPanel)
                        }
                    }
                }
                .combinedClickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { },
                    onLongClick = { onAction(HomeScreenAction.OpenMenuDialog) },
                )
                .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .widthIn(max = 650.dp)
                        .fillMaxSize()
                ){

                    Clock(
                        clock = state.clock,
                        date = state.date
                    )

                    Spacer(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f, fill = true)
                    )

                    Column(
                        modifier = Modifier
                            .clickable { onAction(HomeScreenAction.OpenSearchSheet) }
                    ) {
                        if (state.showSearchBar) {
                            SearchBar(
                                enabled = false,
                                placeholder = if (state.showPlaceholder) stringResource(R.string.Search_touch_or_swipe_up_to_search) else "",
                                showMenu = state.showSettings,
                                onMenuClick = { onAction(HomeScreenAction.OpenSettingsDialog) },
                                borderRadius = state.searchBarRadius,
                                opacity = state.searchBarOpacity
                            )
                        }
                    }

                    if (state.showMenuDialog) {
                        Dialog(onDismissRequest = { onAction(HomeScreenAction.CloseMenuDialog) }) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(MaterialTheme.colorScheme.background),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {

                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f, fill = true)
                                        .clickable { }
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
                                        text = "Select Wallpaper",
                                        color = MaterialTheme.colorScheme.onBackground,
                                        style = Typography.labelSmall
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
                                        text = "Settings",
                                        color = MaterialTheme.colorScheme.onBackground,
                                        style = Typography.labelSmall
                                    )
                                }
                            }
                        }
                    }
                    HomeSettingsDialog(onAction = { onAction(it) }, state = state)
                }
            }
        }
    }
}