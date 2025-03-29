package com.whiskersapps.clawlauncher.views.main.views.settings.views.search_engines.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.whiskersapps.clawlauncher.R
import com.whiskersapps.clawlauncher.shared.view.composables.ContentColumn
import com.whiskersapps.clawlauncher.shared.view.composables.NavBar
import com.whiskersapps.clawlauncher.shared.view.composables.sidePadding
import com.whiskersapps.clawlauncher.shared.view.theme.Typography
import com.whiskersapps.clawlauncher.views.main.views.settings.views.search_engines.intent.SearchEnginesScreenAction
import com.whiskersapps.clawlauncher.views.main.views.settings.views.search_engines.model.SearchEnginesScreenVM
import org.koin.androidx.compose.koinViewModel


@Composable
fun SearchEnginesScreenRoot(
    navController: NavController,
    vm: SearchEnginesScreenVM = koinViewModel()
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

        if (state.searchEngines.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier.size(48.dp),
                    painter = painterResource(R.drawable.search),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )

                Text(
                    text = stringResource(R.string.SearchEnginesScreen_no_search_engines),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        } else {
            LazyColumn {

                item {
                    Column {
                        Text(
                            modifier = Modifier.sidePadding(),
                            text = stringResource(R.string.SearchEnginesScreen_default),
                            color = MaterialTheme.colorScheme.onBackground,
                            style = Typography.titleMedium
                        )

                        if (state.defaultSearchEngineId == null) {
                            Row(
                                modifier = Modifier
                                    .sidePadding()
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(MaterialTheme.colorScheme.surfaceVariant)
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    modifier = Modifier.size(32.dp),
                                    painter = painterResource(R.drawable.warning),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                Text(
                                    text = stringResource(R.string.SearchEnginesScreen_no_default_engine),
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                            Spacer(modifier = Modifier.height(16.dp))
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
                                )
                            }
                        }

                        if (state.searchEngines.any { it._id != state.defaultSearchEngineId }) {
                            Text(
                                modifier = Modifier.sidePadding(),
                                text = stringResource(R.string.SearchEnginesScreen_others),
                                color = MaterialTheme.colorScheme.onBackground,
                                style = Typography.titleMedium
                            )
                        }
                    }
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
                            }
                        )
                    }
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


