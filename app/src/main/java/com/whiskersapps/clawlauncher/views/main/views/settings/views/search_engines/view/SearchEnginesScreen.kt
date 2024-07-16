package com.whiskersapps.clawlauncher.views.main.views.settings.views.search_engines.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
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
import com.whiskersapps.clawlauncher.shared.view.composables.NavBar
import com.whiskersapps.clawlauncher.shared.view.theme.Typography
import com.whiskersapps.clawlauncher.views.main.views.settings.views.search_engines.viewmodel.SearchEnginesScreenVM

@Composable
fun SearchEnginesScreen(
    vm: SearchEnginesScreenVM = hiltViewModel(),
    navigateBack: () -> Unit
) {

    val uiState = vm.uiState.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        NavBar(navigateBack = { navigateBack() }) {
            if (!uiState.loading) {
                Text(
                    modifier = Modifier.clickable {
                        vm.updateShowAddSearchEngineDialog(true)
                    },
                    text = "Add",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        if (!uiState.loading) {

            LazyColumn {

                item {
                    Column(Modifier.padding(start = 16.dp, end = 16.dp)) {
                        Text(
                            text = "Default",
                            color = MaterialTheme.colorScheme.onBackground,
                            style = Typography.titleMedium
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        if (uiState.defaultSearchEngineId == null) {
                            Text(
                                text = "There's currently no default search engine",
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        } else {
                            uiState.defaultSearchEngine?.let {
                                SearchEngineCard(
                                    onClick = { vm.showEditDialog(uiState.defaultSearchEngine) },
                                    searchEngine = uiState.defaultSearchEngine,
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

                items(uiState.searchEngines) { searchEngine ->
                    if (uiState.defaultSearchEngineId != searchEngine._id) {
                        SearchEngineCard(searchEngine = searchEngine, onClick = {vm.showEditDialog(searchEngine)})
                    }
                }
            }

            if (uiState.addEngineDialog.show) {
                AddSearchEngineDialog(
                    vm = vm,
                    name = uiState.addEngineDialog.name,
                    query = uiState.addEngineDialog.query
                )
            }

            if (uiState.editEngineDialog.show) {
                EditSearchEngineDialog(
                    vm = vm,
                    name = uiState.editEngineDialog.name,
                    query = uiState.editEngineDialog.query,
                    default = uiState.editEngineDialog.defaultEngine
                )
            }
        }
    }
}

