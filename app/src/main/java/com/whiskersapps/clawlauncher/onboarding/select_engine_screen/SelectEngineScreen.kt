package com.whiskersapps.clawlauncher.onboarding.select_engine_screen

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.whiskersapps.clawlauncher.R
import com.whiskersapps.clawlauncher.launcher.LauncherActivity
import com.whiskersapps.clawlauncher.onboarding.composables.OnBoardingButton
import com.whiskersapps.clawlauncher.onboarding.composables.OnBoardingScaffold
import com.whiskersapps.clawlauncher.onboarding.select_engine_screen.SelectEngineScreenAction.Finish
import com.whiskersapps.clawlauncher.onboarding.select_engine_screen.SelectEngineScreenAction.NavigateBack
import com.whiskersapps.clawlauncher.onboarding.select_engine_screen.SelectEngineScreenAction.SetDefaultEngine
import com.whiskersapps.clawlauncher.shared.view.composables.sidePadding
import com.whiskersapps.clawlauncher.views.main.views.settings.views.search_engines.view.SearchEngineCard
import org.koin.androidx.compose.koinViewModel

@Composable
fun SelectEngineScreenRoot(
    navController: NavController,
    vm: SelectEngineScreenVM = koinViewModel()
) {
    val context = LocalContext.current
    val activity = context as? Activity

    SelectEngineScreen(
        vm = vm,
    ) { action ->
        when (action) {
            NavigateBack -> {
                navController.navigateUp()
            }

            Finish -> {
                vm.onAction(Finish)

                val intent = Intent(context, LauncherActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }

                activity?.startActivity(intent)
                activity?.finish()
            }

            else -> {
                vm.onAction(action)
            }
        }
    }
}

@Composable
fun SelectEngineScreen(
    vm: SelectEngineScreenVM,
    onAction: (SelectEngineScreenAction) -> Unit,
) {
    val state = vm.state.collectAsState().value

    OnBoardingScaffold(
        mainContent = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                item {
                    Text(
                        modifier = Modifier.sidePadding(),
                        text = stringResource(R.string.SearchEnginesSetupScreen_description),
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
                            onAction(SetDefaultEngine(searchEngine))
                        }
                    )
                }
            }
        },
        navContent = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                OnBoardingButton(stringResource(R.string.SetupScreen_previous)) {
                    onAction(NavigateBack)
                }

                Spacer(Modifier.width(8.dp))

                OnBoardingButton(stringResource(R.string.SetupScreen_finish)) {
                    onAction(Finish)
                }
            }
        }
    )
}