package com.whiskersapps.clawlauncher.views.main.views.settings.view.views.bookmarks.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.whiskersapps.clawlauncher.R
import com.whiskersapps.clawlauncher.shared.utils.getFaviconUrl
import com.whiskersapps.clawlauncher.shared.view.composables.NavBar
import com.whiskersapps.clawlauncher.shared.view.theme.Typography
import com.whiskersapps.clawlauncher.views.main.views.settings.view.views.bookmarks.intent.BookmarksScreenAction
import com.whiskersapps.clawlauncher.views.main.views.settings.view.views.bookmarks.model.BookmarksScreenState
import com.whiskersapps.clawlauncher.views.main.views.settings.view.views.bookmarks.model.BookmarksScreenVM
import kotlinx.coroutines.launch


@Composable
fun BookmarksScreenRoot(
    navController: NavController,
    vm: BookmarksScreenVM = hiltViewModel()
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

@OptIn(ExperimentalFoundationApi::class)
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

    Column(
        Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        NavBar(navigateBack = { onAction(BookmarksScreenAction.NavigateBack) }) {
            if (!state.loading) {
                Text(
                    modifier = Modifier.clickable {
                        onAction(BookmarksScreenAction.OpenAddDialog(pagerState.currentPage))
                    },
                    text = "Add",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        if (!state.loading) {

            TabRow(
                selectedTabIndex = pagerState.currentPage,
                indicator = {},
                containerColor = Color.Transparent,
            ) {
                Tab(
                    selected = pagerState.currentPage == 0,
                    onClick = { selectTab(0) },
                    text = {
                        Row {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(id = R.drawable.bookmark),
                                contentDescription = "Bookmarks Icon"
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text("Bookmarks")
                        }
                    },
                    unselectedContentColor = MaterialTheme.colorScheme.onBackground,
                )

                Tab(
                    selected = pagerState.currentPage == 1,
                    onClick = { selectTab(1) },
                    text = {
                        Row {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(id = R.drawable.folder),
                                contentDescription = "Groups Icon"
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text("Groups")
                        }
                    },
                    unselectedContentColor = MaterialTheme.colorScheme.onBackground
                )
            }

            HorizontalPager(modifier = Modifier.fillMaxSize(), state = pagerState) { pageIndex ->
                when (pageIndex) {
                    0 -> {
                        LazyColumn(Modifier.fillMaxSize()) {
                            items(state.bookmarks, key = { it.url }) { bookmark ->
                                Row(modifier = Modifier
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

                    1 -> {
                        LazyColumn(Modifier.fillMaxSize()) {
                            items(state.groups, key = { it._id.toHexString() }) { group ->
                                Row(modifier = Modifier
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
                        }
                    }
                }
            }

            if (state.showAddBookmarkDialog) {
                AddBookmarkDialog(state = state, onAction = { onAction(it) })
            }

            if (state.showEditBookmarkDialog) {
                EditBookmarkDialog(state = state, onAction = { onAction(it) })
            }

            if (state.showAddGroupDialog) {
                AddGroupDialog(state = state, onAction = { onAction(it) })
            }

            if (state.showEditGroupDialog) {
                EditGroupDialog(state = state, onAction = { onAction(it) })
            }
        }
    }
}