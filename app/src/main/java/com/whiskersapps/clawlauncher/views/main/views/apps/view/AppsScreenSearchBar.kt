package com.whiskersapps.clawlauncher.views.main.views.apps.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import com.whiskersapps.clawlauncher.R
import com.whiskersapps.clawlauncher.views.main.views.apps.intent.AppsScreenAction
import com.whiskersapps.clawlauncher.views.main.views.apps.model.AppsScreenState
import com.whiskersapps.clawlauncher.views.main.views.search.view.SearchBar

@Composable
fun AppsScreenSearchBar(
    onAction: (AppsScreenAction) -> Unit,
    state: AppsScreenState
) {
    val context = LocalContext.current
    val fragmentActivity = context as FragmentActivity

    Box(modifier = Modifier.padding(8.dp)) {
        SearchBar(
            text = state.searchText,
            placeholder = if (state.showSearchBarPlaceholder) stringResource(id = R.string.Apps_search_apps) else "",
            onChange = {
                onAction(AppsScreenAction.SetSearchText(it))
            },
            showMenu = state.showSearchBarSettings,
            onMenuClick = {
                onAction(AppsScreenAction.OpenSettingsDialog)
            },
            borderRadius = state.searchBarRadius,
            backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
            onDone = {
                if (state.searchText.isNotEmpty()) {
                    onAction(AppsScreenAction.OpenFirstApp(fragmentActivity = fragmentActivity ))
                    onAction(AppsScreenAction.CloseKeyboard)
                    onAction(AppsScreenAction.NavigateToHome)
                }else{
                    onAction(AppsScreenAction.CloseKeyboard)
                }
            }
        )
    }
}