package com.whiskersapps.clawlauncher.bookmarks

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.whiskersapps.clawlauncher.R
import com.whiskersapps.clawlauncher.core.composables.TextForm
import com.whiskersapps.clawlauncher.launcher.search.composables.SearchBar
import com.whiskersapps.clawlauncher.shared.view.composables.ContentColumn
import com.whiskersapps.clawlauncher.shared.view.composables.NavBar
import com.whiskersapps.clawlauncher.shared.view.theme.REGULAR_LABEL_STYLE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import com.whiskersapps.clawlauncher.bookmarks.BookmarksShareScreenAction as Action


@Composable
fun BookmarksShareScreenRoot(
    initialName: String,
    initialUrl: String,
    vm: BookmarksShareScreenVM = koinViewModel()
) {
    val activity = LocalActivity.current as Activity
    val scope = rememberCoroutineScope()

    LaunchedEffect(initialName, initialUrl) {
        vm.onAction(Action.SetName(initialName))
        vm.onAction(Action.SetURL(initialUrl))
    }

    BookmarksShareScreen(vm) { action ->
        when (action) {
            Action.NavigateBack -> {
                scope.launch(Dispatchers.Main) {
                    activity.finish()
                }
            }

            Action.AddBookmark -> {
                vm.onBookmarkAddedListener = {
                    activity.finish()
                }

                vm.onAction(action)
            }

            else -> {
                vm.onAction(action)
            }
        }
    }
}

@Composable
fun BookmarksShareScreen(
    vm: BookmarksShareScreenVM,
    state: BookmarksShareScreenState = vm.state.collectAsState().value,
    onAction: (Action) -> Unit
) {
    ContentColumn(
        useSystemBarsPadding = true,
        scrollable = true,
        navigationBar = {
            NavBar(navigateBack = {
                onAction(Action.NavigateBack)
            }) {
                Button(
                    onClick = {
                        onAction(Action.AddBookmark)
                    },
                    enabled = state.enableAddButton
                ) {
                    Text("Add")
                }
            }
        }
    ) {
        Column(
            modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextForm(
                title = "Name",
                placeholder = "Whiskers Apps",
                value = state.name,
                onValueChange = { name ->
                    onAction(Action.SetName(name))
                }
            )

            TextForm(
                title = "Url",
                placeholder = "https://github.com/whiskers-apps",
                value = state.url,
                onValueChange = { url ->
                    onAction(Action.SetURL(url))
                }
            )
        }
    }
}