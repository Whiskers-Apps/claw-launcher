package com.whiskersapps.clawlauncher.launcher.apps.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.FragmentActivity
import com.whiskersapps.clawlauncher.R
import com.whiskersapps.clawlauncher.launcher.apps.AppsScreenAction
import com.whiskersapps.clawlauncher.launcher.apps.AppsScreenState
import com.whiskersapps.clawlauncher.launcher.search.composables.SearchBar

@Composable
fun AppsScreenSearchBar(
    onAction: (AppsScreenAction) -> Unit,
    state: AppsScreenState
) {
    val context = LocalContext.current
    val fragmentActivity = context as FragmentActivity

    Box {
        SearchBar(
            text = state.searchText,
            placeholder = if (state.showSearchBarPlaceholder) stringResource(id = R.string.Search) else "",
            onChange = {
                onAction(AppsScreenAction.SetSearchText(it))
            },
            borderRadius = state.searchBarRadius,
            backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
            onDone = {
                if (state.searchText.isNotEmpty()) {
                    onAction(AppsScreenAction.OpenFirstApp(fragmentActivity = fragmentActivity))
                    onAction(AppsScreenAction.CloseKeyboard)
                    onAction(AppsScreenAction.NavigateToHome)
                } else {
                    onAction(AppsScreenAction.CloseKeyboard)
                }
            }
        )
    }
}