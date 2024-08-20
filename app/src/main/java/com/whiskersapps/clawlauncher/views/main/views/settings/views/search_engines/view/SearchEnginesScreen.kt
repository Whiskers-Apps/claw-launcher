package com.whiskersapps.clawlauncher.views.main.views.settings.views.search_engines.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.whiskersapps.clawlauncher.shared.view.composables.ContentColumn
import com.whiskersapps.clawlauncher.shared.view.composables.NavBar
import com.whiskersapps.clawlauncher.shared.view.theme.Typography
import com.whiskersapps.clawlauncher.views.main.views.settings.views.search_engines.intent.SearchEnginesScreenAction
import com.whiskersapps.clawlauncher.views.main.views.settings.views.search_engines.model.SearchEnginesScreenVM


@Composable
fun SearchEnginesScreenRoot(
    navController: NavController,
    vm: SearchEnginesScreenVM = hiltViewModel()
) {

    SearchEnginesScreen(
        onAction = { action ->
            when (action) {
                SearchEnginesScreenAction.NavigateBack -> navController.navigateUp()
                else -> vm.onAction(action)
            }
        },
        vm = vm
    )
}

@Composable
fun SearchEnginesScreen(
    onAction: (SearchEnginesScreenAction) -> Unit,
    vm: SearchEnginesScreenVM
) {

    val state = vm.state.collectAsState().value

    ContentColumn(
        useSystemBarsPadding = true,
        navigationBar = {
            NavBar(navigateBack = { onAction(SearchEnginesScreenAction.NavigateBack) }) {
                if (!state.loading) {
                    Text(
                        modifier = Modifier.clickable {
                            onAction(SearchEnginesScreenAction.ShowAddEngineDialog)
                        },
                        text = "Add",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        },
        loading = state.loading,
        scrollable = false
    ) {

        LazyColumn {

            item {
                Column {
                    Text(
                        text = "Default",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = Typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    if (state.defaultSearchEngineId == null) {
                        Text(
                            text = "There's currently no default search engine",
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    } else {
                        state.defaultSearchEngine?.let {
                            SearchEngineCard(
                                onClick = {
                                    onAction(
                                        SearchEnginesScreenAction.ShowEditEngineDialog(
                                            state.defaultSearchEngine
                                        )
                                    )
                                },
                                searchEngine = state.defaultSearchEngine,
                                padding = 0.dp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Others",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = Typography.titleMedium
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            items(state.searchEngines) { searchEngine ->
                if (state.defaultSearchEngineId != searchEngine._id) {
                    SearchEngineCard(
                        searchEngine = searchEngine,
                        onClick = {
                            onAction(
                                SearchEnginesScreenAction.ShowEditEngineDialog(
                                    searchEngine
                                )
                            )
                        },
                        padding = 0.dp
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

        if (state.addEngineDialog.show) {
            AddSearchEngineDialog(
                onAction = { onAction(it) },
                name = state.addEngineDialog.name,
                query = state.addEngineDialog.query
            )
        }

        if (state.editEngineDialog.show) {
            EditSearchEngineDialog(
                onAction = { onAction(it) },
                name = state.editEngineDialog.name,
                query = state.editEngineDialog.query,
                default = state.editEngineDialog.defaultEngine
            )
        }
    }

}


