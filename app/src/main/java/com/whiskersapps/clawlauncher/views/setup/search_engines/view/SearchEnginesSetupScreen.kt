package com.whiskersapps.clawlauncher.views.setup.search_engines.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.whiskersapps.clawlauncher.R
import com.whiskersapps.clawlauncher.shared.model.Routes
import com.whiskersapps.clawlauncher.shared.view.composables.ContentColumn
import com.whiskersapps.clawlauncher.shared.view.composables.NavBar
import com.whiskersapps.clawlauncher.shared.view.composables.sidePadding
import com.whiskersapps.clawlauncher.views.main.views.settings.views.search_engines.view.SearchEngineCard
import com.whiskersapps.clawlauncher.views.setup.search_engines.intent.SearchEnginesSetupScreenAction
import com.whiskersapps.clawlauncher.views.setup.search_engines.model.SearchEnginesSetupScreenVM

@Composable
fun SearchEnginesSetupScreenRoot(
    navController: NavController,
    vm: SearchEnginesSetupScreenVM = hiltViewModel()
) {
    SearchEnginesSetupScreen(
        onAction = { action ->
            when (action) {
                SearchEnginesSetupScreenAction.NavigateBack -> navController.navigateUp()
                SearchEnginesSetupScreenAction.Finish -> {
                    navController.navigate(Routes.Main.ROUTE) {
                        popUpTo(Routes.Main.ROUTE) { inclusive = true }
                    }

                    vm.onAction(action)
                }

                else -> {
                    vm.onAction(action)
                }
            }
        },
        vm = vm
    )
}

@Composable
fun SearchEnginesSetupScreen(
    onAction: (SearchEnginesSetupScreenAction) -> Unit,
    vm: SearchEnginesSetupScreenVM
) {
    val state = vm.state.collectAsState().value

    ContentColumn(
        useSystemBarsPadding = true,
        scrollable = false
    ) {
        NavBar(navigateBack = { onAction(SearchEnginesSetupScreenAction.NavigateBack) })

        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f, fill = true)
        ) {

            item {
                Text(
                    modifier = Modifier.sidePadding(),
                    text = "Select your favourite search engine. You can change this later in the settings.",
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            items(
                items = state.searchEngines,
                key = { it._id.toHexString() }
            ) { searchEngine ->
                SearchEngineCard(
                    searchEngine = searchEngine,
                    backgroundColor = if (state.selectedEngine == searchEngine) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.background,
                    onClick = {
                        onAction(SearchEnginesSetupScreenAction.SetDefaultEngine(searchEngine))
                    }
                )
            }
        }


        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.End) {
            Button(onClick = { onAction(SearchEnginesSetupScreenAction.NavigateBack) }) {
                Text(
                    text = stringResource(id = R.string.SetupScreen_previous),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(onClick = { onAction(SearchEnginesSetupScreenAction.Finish) }) {
                Text(
                    text = stringResource(id = R.string.SetupScreen_finish),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}