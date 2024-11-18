package com.whiskersapps.clawlauncher.views.main.views.apps.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import com.whiskersapps.clawlauncher.shared.utils.inPortrait
import com.whiskersapps.clawlauncher.shared.utils.isSplitAvailable
import com.whiskersapps.clawlauncher.shared.view.composables.AppIcon
import com.whiskersapps.clawlauncher.shared.view.composables.AppPopup
import com.whiskersapps.clawlauncher.shared.view.composables.GridAppShortcut
import com.whiskersapps.clawlauncher.views.main.views.apps.intent.AppsScreenAction
import com.whiskersapps.clawlauncher.views.main.views.apps.model.AppsScreenVM
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AppsScreenRoot(
    pagerState: PagerState,
    vm: AppsScreenVM = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    AppsScreen(
        onAction = { action ->
            when (action) {
                AppsScreenAction.NavigateToHome -> scope.launch {
                    pagerState.scrollToPage(0)
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }

                AppsScreenAction.CloseKeyboard -> {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }

                else -> vm.onAction(action)
            }
        },
        vm = vm
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AppsScreen(
    onAction: (AppsScreenAction) -> Unit,
    vm: AppsScreenVM,
) {

    val fragmentActivity = LocalContext.current as FragmentActivity
    val state = vm.state.collectAsState().value
    val colsCount =
        if (inPortrait()) state.gridColsCount.portrait else state.gridColsCount.landscape

    val splitList = isSplitAvailable() && state.splitList

    if (!state.loading) {
        Box(contentAlignment = Alignment.Center) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            )

            Column(
                modifier = Modifier
                    .widthIn(max = 900.dp)
                    .fillMaxSize()
                    .systemBarsPadding()
                    .imePadding()
                    .padding(8.dp)
            ) {

                if (state.showSearchBar && state.searchBarPosition == "top") {
                    AppsScreenSearchBar(
                        onAction = { onAction(it) },
                        state = state
                    )
                }

                if (state.viewType == "list") {

                    when(splitList){
                        true -> {
                            LazyVerticalGrid(
                                modifier = Modifier.fillMaxHeight().weight(1f, fill = true),
                                columns = GridCells.Fixed(2)
                            ) {
                                itemsIndexed(
                                    items = state.appShortcuts,
                                    key = { index, app -> "${index}-${app.packageName}" }
                                ) { index, app ->

                                    var showMenu by remember { mutableStateOf(false) }

                                    Column {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clip(CircleShape)
                                                .background(if (index == 0 && state.searchText.isNotEmpty()) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.background)
                                                .combinedClickable(
                                                    onClick = {
                                                        onAction(
                                                            AppsScreenAction.OpenApp(
                                                                app.packageName,
                                                                fragmentActivity
                                                            )
                                                        )
                                                        onAction(AppsScreenAction.CloseKeyboard)
                                                        onAction(AppsScreenAction.NavigateToHome)
                                                    },
                                                    onLongClick = {
                                                        showMenu = true
                                                    }
                                                )
                                                .padding(8.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {

                                            Box(modifier = Modifier.size(48.dp)) {
                                                AppIcon(app = app)
                                            }

                                            Spacer(modifier = Modifier.width(16.dp))

                                            Text(
                                                text = app.label,
                                                color = MaterialTheme.colorScheme.onBackground
                                            )
                                        }
                                        if (showMenu) {
                                            AppPopup(
                                                app = app,
                                                onDismiss = {
                                                    showMenu = false
                                                },
                                                onInfoClick = {
                                                    onAction(AppsScreenAction.OpenAppInfo(app.packageName))
                                                    showMenu = false
                                                    onAction(AppsScreenAction.NavigateToHome)
                                                },
                                                onUninstallClick = {
                                                    onAction(AppsScreenAction.RequestUninstall(app.packageName))
                                                    showMenu = false
                                                },
                                                onOpenShortcut = { shortcut ->
                                                    onAction(
                                                        AppsScreenAction.OpenShortcut(
                                                            app.packageName,
                                                            shortcut
                                                        )
                                                    )
                                                    showMenu = false
                                                    onAction(AppsScreenAction.NavigateToHome)
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        false -> {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(1f, fill = true)
                            ) {
                                itemsIndexed(
                                    items = state.appShortcuts,
                                    key = { index, app -> "${index}-${app.packageName}" }
                                ) { index, app ->

                                    var showMenu by remember { mutableStateOf(false) }

                                    Column {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clip(CircleShape)
                                                .background(if (index == 0 && state.searchText.isNotEmpty()) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.background)
                                                .combinedClickable(
                                                    onClick = {
                                                        onAction(
                                                            AppsScreenAction.OpenApp(
                                                                app.packageName,
                                                                fragmentActivity
                                                            )
                                                        )
                                                        onAction(AppsScreenAction.CloseKeyboard)
                                                        onAction(AppsScreenAction.NavigateToHome)
                                                    },
                                                    onLongClick = {
                                                        showMenu = true
                                                    }
                                                )
                                                .padding(8.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {

                                            Box(modifier = Modifier.size(48.dp)) {
                                                AppIcon(app = app)
                                            }

                                            Spacer(modifier = Modifier.width(16.dp))

                                            Text(
                                                text = app.label,
                                                color = MaterialTheme.colorScheme.onBackground
                                            )
                                        }
                                        if (showMenu) {
                                            AppPopup(
                                                app = app,
                                                onDismiss = {
                                                    showMenu = false
                                                },
                                                onInfoClick = {
                                                    onAction(AppsScreenAction.OpenAppInfo(app.packageName))
                                                    showMenu = false
                                                    onAction(AppsScreenAction.NavigateToHome)
                                                },
                                                onUninstallClick = {
                                                    onAction(AppsScreenAction.RequestUninstall(app.packageName))
                                                    showMenu = false
                                                },
                                                onOpenShortcut = { shortcut ->
                                                    onAction(
                                                        AppsScreenAction.OpenShortcut(
                                                            app.packageName,
                                                            shortcut
                                                        )
                                                    )
                                                    showMenu = false
                                                    onAction(AppsScreenAction.NavigateToHome)
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                if (state.viewType == "grid") {

                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f, fill = true)
                    ) {
                        LazyVerticalGrid(columns = GridCells.Fixed(colsCount)) {
                            itemsIndexed(
                                items = state.appShortcuts,
                                key = { index, app -> "$index - ${app.packageName}" }
                            ) { index, app ->
                                GridAppShortcut(
                                    app = app,
                                    openApp = {
                                        onAction(
                                            AppsScreenAction.OpenApp(
                                                app.packageName,
                                                fragmentActivity
                                            )
                                        )
                                        onAction(AppsScreenAction.CloseKeyboard)
                                        onAction(AppsScreenAction.NavigateToHome)
                                    },
                                    openInfo = {
                                        onAction(AppsScreenAction.OpenAppInfo(app.packageName))
                                        onAction(AppsScreenAction.NavigateToHome)
                                    },
                                    requestUninstall = {
                                        onAction(AppsScreenAction.RequestUninstall(app.packageName))
                                    },
                                    openShortcut = { shortcut ->
                                        onAction(
                                            AppsScreenAction.OpenShortcut(
                                                app.packageName,
                                                shortcut
                                            )
                                        )
                                        onAction(AppsScreenAction.NavigateToHome)
                                    },
                                    backgroundColor = if (index == 0 && state.searchText.isNotEmpty()) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.background
                                )
                            }
                        }
                    }
                }

                if (state.showSearchBar && state.searchBarPosition == "bottom") {
                    Spacer(modifier = Modifier.height(16.dp))

                    AppsScreenSearchBar(
                        onAction = { onAction(it) },
                        state = state
                    )
                }
            }

            AppsSettingsDialog(
                onAction = { onAction(it) },
                state
            )
        }
    }
}