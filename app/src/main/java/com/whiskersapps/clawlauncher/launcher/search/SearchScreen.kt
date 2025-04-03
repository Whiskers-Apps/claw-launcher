package com.whiskersapps.clawlauncher.launcher.search

import androidx.activity.compose.LocalActivity
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import coil.compose.AsyncImage
import com.whiskersapps.clawlauncher.R
import com.whiskersapps.clawlauncher.launcher.search.composables.SearchBar
import com.whiskersapps.clawlauncher.shared.utils.getCachedImageRequest
import com.whiskersapps.clawlauncher.shared.utils.getFaviconUrl
import com.whiskersapps.clawlauncher.shared.utils.inPortrait
import com.whiskersapps.clawlauncher.shared.view.composables.ContentColumn
import com.whiskersapps.clawlauncher.shared.view.composables.GridAppShortcut
import com.whiskersapps.clawlauncher.shared.view.composables.sidePadding
import com.whiskersapps.clawlauncher.shared.view.theme.Typography
import com.whiskersapps.clawlauncher.launcher.search.SearchScreenAction.OnClearSearch
import com.whiskersapps.clawlauncher.launcher.search.SearchScreenAction.OnCloseSheet
import com.whiskersapps.clawlauncher.launcher.search.SearchScreenAction.OnOpenApp
import com.whiskersapps.clawlauncher.launcher.search.SearchScreenAction.OnOpenAppInfo
import com.whiskersapps.clawlauncher.launcher.search.SearchScreenAction.OnOpenGroup
import com.whiskersapps.clawlauncher.launcher.search.SearchScreenAction.OnOpenShortcut
import com.whiskersapps.clawlauncher.launcher.search.SearchScreenAction.OnOpenUrl
import com.whiskersapps.clawlauncher.launcher.search.SearchScreenAction.OnRequestUninstall
import com.whiskersapps.clawlauncher.launcher.search.SearchScreenAction.OnRunAction
import com.whiskersapps.clawlauncher.launcher.search.SearchScreenAction.OnSearchInput
import com.whiskersapps.clawlauncher.launcher.search.SearchScreenAction.OnSetFocusSearchBar
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreenRoot(
    sheetState: SheetState,
    vm: SearchScreenVM = koinViewModel(),
    onCloseSheet: () -> Unit
) {
    SearchScreen(vm, sheetState) { action ->
        when (action) {
            OnCloseSheet -> {
                onCloseSheet()
            }

            else -> {
                vm.onAction(action)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    vm: SearchScreenVM,
    sheetState: SheetState,
    onAction: (SearchScreenAction) -> Unit
) {

    val activity = LocalActivity.current as FragmentActivity
    val state = vm.state.collectAsState().value
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val colsCount =
        if (inPortrait()) state.portraitColors else state.landscapeCols


    LaunchedEffect(sheetState.currentValue) {
        if (sheetState.currentValue == SheetValue.Expanded) {
            onAction(OnSetFocusSearchBar(true))
        } else {
            onAction(OnClearSearch)
            focusManager.clearFocus()
            keyboardController?.hide()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        if (!state.loading) {
            ContentColumn(useSystemBarsPadding = true, scrollable = false) {
                Box(
                    modifier = Modifier.sidePadding()
                ) {
                    SearchBar(
                        text = state.searchText,
                        onChange = { text ->
                            onAction(OnSearchInput(text))
                        },
                        placeholder = stringResource(R.string.Search),
                        focus = state.focusSearchBar,
                        onFocused = {
                            onAction(OnSetFocusSearchBar(false))
                        },
                        backgroundColor = Color.Transparent,
                        onDone = {
                            onAction(OnRunAction(activity))
                            onAction(OnCloseSheet)
                        }
                    )
                }


                Spacer(modifier = Modifier.height(16.dp))

                if (state.apps.isNotEmpty() && state.showResults) {

                    Column(modifier = Modifier.sidePadding()) {
                        Text(
                            text = stringResource(R.string.SearchScreen_apps),
                            color = MaterialTheme.colorScheme.onBackground,
                            style = Typography.titleSmall
                        )

                        LazyVerticalGrid(
                            modifier = Modifier
                                .clip(RoundedCornerShape(32.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .padding(8.dp),
                            columns = GridCells.Fixed(colsCount),
                        ) {
                            itemsIndexed(
                                state.apps,
                                key = { index, app -> "${index}-${app.packageName}" }
                            ) { index, app ->
                                GridAppShortcut(
                                    app = app,
                                    openApp = {
                                        onAction(OnOpenApp(app.packageName, activity))
                                        onAction(OnCloseSheet)
                                    },
                                    openInfo = {
                                        onAction(OnOpenAppInfo(app.packageName))
                                        onAction(OnCloseSheet)
                                    },
                                    requestUninstall = {
                                        onAction(OnRequestUninstall(app.packageName))
                                        onAction(OnCloseSheet)
                                    },
                                    openShortcut = { shortcut ->
                                        onAction(OnOpenShortcut(app.packageName, shortcut))
                                        onAction(OnCloseSheet)
                                    },
                                    backgroundColor = if (index == 0) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.surfaceVariant,
                                    radius = 24.dp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                if (state.searchText.isNotEmpty() && state.showResults) {

                    if (state.groups.isNotEmpty() || state.bookmarks.isNotEmpty()) {

                        Column(modifier = Modifier.sidePadding()) {
                            Text(
                                text = stringResource(R.string.SearchScreen_bookmarks),
                                color = MaterialTheme.colorScheme.onBackground,
                                style = Typography.titleSmall
                            )

                            LazyColumn {
                                items(
                                    items = state.groups,
                                    key = { "group - ${it._id.toHexString()}" }
                                ) { group ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(CircleShape)
                                            .background(MaterialTheme.colorScheme.surfaceVariant)
                                            .clickable {
                                                onAction(OnOpenGroup(group))
                                                onAction(OnCloseSheet)
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
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(CircleShape)
                                            .background(MaterialTheme.colorScheme.surfaceVariant)
                                            .clickable {
                                                onAction(OnOpenUrl(bookmark.url))
                                                onAction(OnCloseSheet)
                                            }
                                            .padding(16.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        AsyncImage(
                                            modifier = Modifier
                                                .clip(CircleShape)
                                                .size(42.dp)
                                                .background(MaterialTheme.colorScheme.surfaceVariant),
                                            model = getCachedImageRequest(getFaviconUrl(bookmark.url)),
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

                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }

                    state.searchEngine?.let {
                        Column(modifier = Modifier.sidePadding()) {
                            Text(
                                text = stringResource(R.string.SearchScreen_web),
                                color = MaterialTheme.colorScheme.onBackground,
                                style = Typography.titleSmall
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.surfaceVariant)
                                    .clickable {
                                        onAction(OnOpenUrl(vm.getSearchEngineUrl()))
                                        onAction(OnCloseSheet)
                                    }
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AsyncImage(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .size(42.dp),
                                    model = getCachedImageRequest(getFaviconUrl(state.searchEngine.query)),
                                    contentDescription = "${state.searchEngine.name} icon"
                                )

                                Spacer(modifier = Modifier.width(16.dp))

                                Text(
                                    text = stringResource(R.string.SearchScreen_search_on_for).replace(
                                        "{engine}",
                                        state.searchEngine.name
                                    ).replace("{search}", state.searchText),
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