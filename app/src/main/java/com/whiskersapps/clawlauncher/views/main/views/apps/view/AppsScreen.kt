package com.whiskersapps.clawlauncher.views.main.views.apps.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lighttigerxiv.layout_scaffold.inLandscape
import com.lighttigerxiv.layout_scaffold.isTablet
import com.whiskersapps.clawlauncher.shared.view.composables.GridAppShortcut
import com.whiskersapps.clawlauncher.views.main.views.apps.viewmodel.AppsScreenVM
import com.whiskersapps.clawlauncher.views.main.views.search.view.SearchBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AppsScreen(
    vm: AppsScreenVM = hiltViewModel(),
    navigateHome: () -> Unit
) {

    val uiState = vm.uiState.collectAsState().value
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()
    val isTablet = isTablet()
    val inLandscape = inLandscape()

    uiState?.let {

        Box(contentAlignment = Alignment.Center) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(uiState.opacity)
                    .background(MaterialTheme.colorScheme.background)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .systemBarsPadding()
                    .imePadding()
                    .padding(8.dp)
            ) {

                if (uiState.showSearchBar && uiState.searchBarPosition == "top") {

                    Box(modifier = Modifier.padding(8.dp)) {
                        SearchBar(
                            text = uiState.searchText,
                            onChange = {
                                vm.updateSearchText(it)
                            },
                            onDone = {
                                scope.launch {
                                    vm.openFirstApp()
                                    delay(1000)

                                    focusManager.clearFocus()
                                    keyboardController?.hide()

                                    navigateHome()
                                }
                            }
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

                if (uiState.viewType == "list") {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f, fill = true)
                    ) {
                        itemsIndexed(
                            items = uiState.appShortcuts,
                            key = { index, app -> "${index}-${app.packageName}" }) { _, app ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(CircleShape)
                                    .clickable {
                                        scope.launch {
                                            vm.openApp(app.packageName)
                                            delay(1000)

                                            focusManager.clearFocus()
                                            keyboardController?.hide()

                                            navigateHome()
                                        }
                                    }
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                val image by remember {
                                    derivedStateOf { app.icon.asImageBitmap() }
                                }

                                Box(
                                    modifier = Modifier
                                        .size(56.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.background)
                                ) {
                                    Image(
                                        modifier = Modifier.fillMaxSize(),
                                        bitmap = image,
                                        contentDescription = "${app.packageName} icon"
                                    )
                                }

                                Spacer(modifier = Modifier.width(16.dp))

                                Text(text = app.name)
                            }
                        }
                    }
                }

                if (uiState.viewType == "grid") {

                    val phoneColumns =
                        if (inLandscape) uiState.phoneLandscapeCols else uiState.phoneCols
                    val tabletColumns =
                        if (inLandscape) uiState.tabletLandscapeCols else uiState.tabletCols
                    val columns = if (isTablet) tabletColumns else phoneColumns


                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f, fill = true)
                    ) {
                        LazyVerticalGrid(columns = GridCells.Fixed(columns)) {
                            itemsIndexed(
                                items = uiState.appShortcuts,
                                key = { index, app -> "$index - ${app.packageName}" }
                            ) { _, app ->
                                GridAppShortcut(
                                    app = app,
                                    padding = uiState.iconPadding,
                                    openApp = {
                                        scope.launch {
                                            vm.openApp(app.packageName)
                                            delay(200)

                                            focusManager.clearFocus()
                                            keyboardController?.hide()

                                            navigateHome()
                                        }
                                    },
                                    openInfo = {vm.openAppInfo(app.packageName)},
                                    requestUninstall = {vm.requestUninstall(app.packageName)}
                                )
                            }
                        }
                    }

                }


                if (uiState.showSearchBar && uiState.searchBarPosition == "bottom") {
                    Spacer(modifier = Modifier.height(8.dp))

                    Box(modifier = Modifier.padding(8.dp)) {
                        SearchBar(
                            text = uiState.searchText,
                            onChange = { vm.updateSearchText(it) },
                            showMenu = true,
                            onMenuClick = { vm.updateShowSettingsDialog(true) },
                            opacity = uiState.searchBarOpacity,
                            onDone = {
                                scope.launch {
                                    if (uiState.searchText.isNotEmpty()) {
                                        vm.openFirstApp()
                                        delay(1000)
                                        navigateHome()
                                    }

                                    focusManager.clearFocus()
                                    keyboardController?.hide()
                                }
                            }
                        )
                    }
                }
            }

            AppsSettingsDialog(vm, uiState)
        }
    }
}