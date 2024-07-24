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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.lighttigerxiv.layout_scaffold.inLandscape
import com.lighttigerxiv.layout_scaffold.isTablet
import com.whiskersapps.clawlauncher.R
import com.whiskersapps.clawlauncher.shared.utils.getFaviconUrl
import com.whiskersapps.clawlauncher.shared.view.composables.GridAppShortcut
import com.whiskersapps.clawlauncher.shared.view.theme.Typography
import com.whiskersapps.clawlauncher.views.main.views.search.viewmodel.SearchScreenVM
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    vm: SearchScreenVM = hiltViewModel(),
    sheetState: SheetState,
    closeSheet: () -> Unit
) {

    val state = vm.state.collectAsState().value
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val inLandscape = inLandscape()
    val isTablet = isTablet()

    LaunchedEffect(sheetState.currentValue) {
        if (sheetState.currentValue == SheetValue.Expanded) {
            vm.updateFocusSearchBar(true)
        } else {
            vm.updateSearchText("")
            focusManager.clearFocus()
            keyboardController?.hide()
        }
    }

    if (!state.loading) {

        val phoneColumns =
            if (inLandscape) state.phoneLandscapeCols else state.phoneCols
        val tabletColumns =
            if (inLandscape) state.tabletLandscapeCols else state.tabletCols
        val columns = if (isTablet) tabletColumns else phoneColumns

        Box(contentAlignment = Alignment.Center) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            )


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .systemBarsPadding()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Column(
                    modifier = Modifier
                        .widthIn(max = 650.dp)
                        .fillMaxSize()
                ) {
                    SearchBar(
                        text = state.searchText,
                        onChange = { vm.updateSearchText(it) },
                        placeholder = stringResource(R.string.Search_apps_and_much_more),
                        focus = state.focusSearchBar,
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

                    if (state.appShortcuts.isNotEmpty()) {

                        Text(
                            text = "Apps",
                            color = MaterialTheme.colorScheme.onBackground,
                            style = Typography.titleSmall
                        )

                        LazyVerticalGrid(
                            columns = GridCells.Fixed(columns),
                        ) {
                            itemsIndexed(
                                state.appShortcuts,
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
                    }

                    if (state.searchText.isNotEmpty()) {

                        if (state.groups.isNotEmpty() || state.bookmarks.isNotEmpty()) {

                            Text(
                                text = "Bookmarks",
                                color = MaterialTheme.colorScheme.onBackground,
                                style = Typography.titleSmall
                            )

                            LazyColumn {
                                items(
                                    items = state.groups,
                                    key = { "group - ${it._id.toHexString()}" }
                                ) { group ->
                                    Row(modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            vm.openGroup(group)
                                            closeSheet()
                                        }
                                        .padding(16.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            modifier = Modifier
                                                .clip(CircleShape)
                                                .size(42.dp),
                                            painter = painterResource(id = R.drawable.folder),
                                            contentDescription = "${group.name} icon",
                                            tint = MaterialTheme.colorScheme.onBackground
                                        )

                                        Spacer(modifier = Modifier.width(8.dp))

                                        Text(
                                            text = group.name,
                                            color = MaterialTheme.colorScheme.onBackground
                                        )
                                    }
                                }
                                items(
                                    items = state.bookmarks,
                                    key = { it._id.toHexString() }) { bookmark ->
                                    Row(modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            vm.openUrl(bookmark.url)
                                        }
                                        .padding(16.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        AsyncImage(
                                            modifier = Modifier
                                                .clip(CircleShape)
                                                .size(42.dp)
                                                .background(MaterialTheme.colorScheme.surfaceVariant),
                                            model = getFaviconUrl(bookmark.url),
                                            contentDescription = "${bookmark.name} icon"
                                        )

                                        Spacer(modifier = Modifier.width(8.dp))

                                        Column {
                                            Text(
                                                text = bookmark.name,
                                                color = MaterialTheme.colorScheme.onBackground
                                            )

                                            Text(
                                                text = bookmark.url,
                                                color = MaterialTheme.colorScheme.onBackground,
                                                style = Typography.labelSmall
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        state.searchEngine?.let {
                            Text(
                                text = "Web",
                                color = MaterialTheme.colorScheme.onBackground,
                                style = Typography.titleSmall
                            )

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
                                    model = getFaviconUrl(state.searchEngine.query),
                                    contentDescription = "${state.searchEngine.name} icon"
                                )

                                Spacer(modifier = Modifier.width(16.dp))

                                Text(
                                    text = "Search on ${state.searchEngine.name} for ${state.searchText}",
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
}