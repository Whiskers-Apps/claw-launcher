package com.whiskersapps.clawlauncher.views.main.views.search.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.whiskersapps.clawlauncher.R
import com.whiskersapps.clawlauncher.shared.utils.getFaviconUrl
import com.whiskersapps.clawlauncher.shared.view.composables.GridAppShortcut
import com.whiskersapps.clawlauncher.shared.view.theme.Typography
import com.whiskersapps.clawlauncher.views.main.views.search.viewmodel.SearchScreenVM
import com.whiskersapps.clawlauncher.views.main.views.settings.views.search_engines.view.SearchEngineCard
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    vm: SearchScreenVM = hiltViewModel(),
    sheetState: SheetState,
    closeSheet: () -> Unit
) {

    val uiState = vm.uiState.collectAsState().value
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(sheetState.currentValue) {
        if (sheetState.currentValue == SheetValue.Expanded) {
            vm.updateFocusSearchBar(true)
        } else {
            vm.updateSearchText("")
            focusManager.clearFocus()
            keyboardController?.hide()
        }
    }

    if (!uiState.loading) {

        Box(contentAlignment = Alignment.Center) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.9f)
                    .background(MaterialTheme.colorScheme.background)
            )


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .systemBarsPadding()
                    .padding(16.dp)
            ) {

                SearchBar(
                    text = uiState.searchText,
                    onChange = { vm.updateSearchText(it) },
                    placeholder = stringResource(R.string.Search_apps_and_much_more),
                    focus = uiState.focusSearchBar,
                    onFocused = { vm.updateFocusSearchBar(false) },
                    opacity = 0f,
                    onDone = {
                        scope.launch {
                            vm.runAction()

                            focusManager.clearFocus()
                            keyboardController?.hide()

                            closeSheet()
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (uiState.appShortcuts.isNotEmpty()) {

                    Text(
                        text = "Apps",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = Typography.titleSmall
                    )

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(4),
                    ) {
                        itemsIndexed(
                            uiState.appShortcuts,
                            key = { index, app -> "${index}-${app.packageName}" }
                        ) { _, app ->
                            GridAppShortcut(
                                app = app,
                                padding = 16.dp,
                                openApp = {
                                    scope.launch {
                                        vm.openApp(app.packageName)

                                        focusManager.clearFocus()
                                        keyboardController?.hide()

                                        closeSheet()
                                    }
                                },
                                openInfo = { vm.openAppInfo(app.packageName) },
                                requestUninstall = { vm.requestUninstall(app.packageName) }
                            )
                        }
                    }

                    Text(
                        text = "Bookmarks",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = Typography.titleSmall
                    )
                }

                if (uiState.searchText.isNotEmpty()) {
                    uiState.searchEngine?.let {
                        Text(
                            text = "Web",
                            color = MaterialTheme.colorScheme.onBackground,
                            style = Typography.titleSmall
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { vm.openUrl(vm.getSearchEngineUrl()) }
                                .padding(top = 16.dp, bottom = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .size(42.dp),
                                model = getFaviconUrl(uiState.searchEngine.query),
                                contentDescription = "${uiState.searchEngine.name} icon"
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            Text(
                                text = "Search on ${uiState.searchEngine.name} for ${uiState.searchText}",
                                color = MaterialTheme.colorScheme.onBackground,
                                maxLines = 2
                            )
                        }
                    }
                }
            }
        }
    }
}