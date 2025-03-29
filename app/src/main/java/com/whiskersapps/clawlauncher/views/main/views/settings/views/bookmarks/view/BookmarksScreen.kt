package com.whiskersapps.clawlauncher.views.main.views.settings.views.bookmarks.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.whiskersapps.clawlauncher.R
import com.whiskersapps.clawlauncher.shared.utils.getCachedImageRequest
import com.whiskersapps.clawlauncher.shared.utils.getFaviconUrl
import com.whiskersapps.clawlauncher.shared.view.composables.ContentColumn
import com.whiskersapps.clawlauncher.shared.view.composables.NavBar
import com.whiskersapps.clawlauncher.shared.view.theme.Typography
import com.whiskersapps.clawlauncher.views.main.views.settings.views.bookmarks.intent.BookmarksScreenAction
import com.whiskersapps.clawlauncher.views.main.views.settings.views.bookmarks.model.BookmarksScreenState
import com.whiskersapps.clawlauncher.views.main.views.settings.views.bookmarks.model.BookmarksScreenVM
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


@Composable
fun BookmarksScreenRoot(
    navController: NavController,
    vm: BookmarksScreenVM = koinViewModel()
) {

    BookmarksScreen(
        state = vm.state.collectAsState().value,
        onAction = { action ->
            when (action) {
                BookmarksScreenAction.NavigateBack -> navController.navigateUp()
                else -> vm.onAction(action)
            }
        }
    )
}

@Composable
fun BookmarksScreen(
    state: BookmarksScreenState,
    onAction: (BookmarksScreenAction) -> Unit,
) {

    val pagerState = rememberPagerState(pageCount = { 2 })
    val scope = rememberCoroutineScope()

    fun selectTab(tabIndex: Int) {
        scope.launch { pagerState.animateScrollToPage(tabIndex) }
    }

    ContentColumn(
        useSystemBarsPadding = true,
        navigationBar = {
            NavBar(navigateBack = { onAction(BookmarksScreenAction.NavigateBack) }) {
                if (!state.loading) {
                    Text(
                        modifier = Modifier.clickable {
                            onAction(BookmarksScreenAction.OpenAddDialog(pagerState.currentPage))
                        },
                        text = stringResource(R.string.Add),
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        },
        loading = state.loading,
        scrollable = false
    ) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = Color.Transparent,
        ) {
            Tab(
                selected = pagerState.currentPage == 0,
                onClick = { selectTab(0) },
                text = {
                    Text(
                        stringResource(R.string.BookmarksScreen_bookmarks),
                    )
                },
                unselectedContentColor = MaterialTheme.colorScheme.onBackground,
            )

            Tab(
                selected = pagerState.currentPage == 1,
                onClick = { selectTab(1) },
                text = {

                    Text(
                        stringResource(R.string.BookmarksScreen_groups),
                    )

                },
                unselectedContentColor = MaterialTheme.colorScheme.onBackground
            )
        }

        HorizontalPager(modifier = Modifier.fillMaxSize(), state = pagerState) { pageIndex ->
            when (pageIndex) {
                0 -> {
                    if (state.bookmarks.isEmpty()) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                modifier = Modifier.size(48.dp),
                                painter = painterResource(R.drawable.bookmark),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onBackground
                            )

                            Text(
                                text = stringResource(R.string.BookmarksScreen_no_bookmarks),
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    } else {
                        LazyColumn(Modifier.fillMaxSize()) {
                            items(state.bookmarks, key = { it.url }) { bookmark ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            onAction(
                                                BookmarksScreenAction.OpenEditBookmarkDialog(
                                                    bookmark
                                                )
                                            )
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
                    }
                }

                1 -> {
                    if (state.groups.isEmpty()) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                modifier = Modifier.size(48.dp),
                                painter = painterResource(R.drawable.folder),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onBackground
                            )

                            Text(
                                text = stringResource(R.string.BookmarksScreen_no_groups),
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    } else {
                        LazyColumn(Modifier.fillMaxSize()) {
                            items(state.groups, key = { it._id.toHexString() }) { group ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            onAction(
                                                BookmarksScreenAction.OpenEditGroupDialog(
                                                    group
                                                )
                                            )
                                        }
                                        .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .size(24.dp),
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
                        }
                    }
                }
            }
        }

        AddBookmarkDialog(state = state, onAction = { onAction(it) })

        EditBookmarkDialog(state = state, onAction = { onAction(it) })

        AddGroupDialog(state = state, onAction = { onAction(it) })

        EditGroupDialog(state = state, onAction = { onAction(it) })
    }
}