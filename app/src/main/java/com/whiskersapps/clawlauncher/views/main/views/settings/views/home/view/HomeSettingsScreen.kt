package com.whiskersapps.clawlauncher.views.main.views.settings.views.home.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.whiskersapps.clawlauncher.shared.intent.settings.HomeSettingsAction
import com.whiskersapps.clawlauncher.shared.view.composables.ContentColumn
import com.whiskersapps.clawlauncher.shared.view.composables.NavBar
import com.whiskersapps.clawlauncher.shared.view.settings.HomeSettings
import com.whiskersapps.clawlauncher.views.main.views.settings.views.home.intent.HomeSettingsScreenAction
import com.whiskersapps.clawlauncher.views.main.views.settings.views.home.model.HomeSettingsScreenVM

@Composable
fun HomeSettingsScreenRoot(
    navController: NavController,
    vm: HomeSettingsScreenVM = hiltViewModel(),
) {

    HomeSettingsScreen(
        onAction = { action ->
            when (action) {
                HomeSettingsScreenAction.NavigateBack -> navController.navigateUp()
                else -> vm.onAction(action)
            }
        },
        vm = vm
    )
}

@Composable
fun HomeSettingsScreen(
    onAction: (HomeSettingsScreenAction) -> Unit,
    vm: HomeSettingsScreenVM,
) {
    val state = vm.state.collectAsState().value

    ContentColumn(
        useSystemBarsPadding = true,
        navigationBar = {
            NavBar(navigateBack = { onAction(HomeSettingsScreenAction.NavigateBack) })
        },
        loading = state.loading
    ) {

        HomeSettings(
            swipeUpToSearch = state.settings.swipeUpToSearch,
            showSearchBar = state.settings.showHomeSearchBar,
            showPlaceholder = state.settings.showHomeSearchBarPlaceholder,
            showSettings = state.settings.showHomeSearchBarSettings,
            searchBarRadius = state.settings.homeSearchBarRadius.toFloat(),
            onAction = { action ->
                when (action) {

                    is HomeSettingsAction.SetSearchBarRadius -> onAction(
                        HomeSettingsScreenAction.SetSearchBarRadius(action.radius)
                    )

                    is HomeSettingsAction.SetShowSearchBar -> onAction(
                        HomeSettingsScreenAction.SetShowSearchBar(action.show)
                    )

                    is HomeSettingsAction.SetShowSearchBarPlaceholder -> onAction(
                        HomeSettingsScreenAction.SetShowSearchBarPlaceholder(action.show)
                    )

                    is HomeSettingsAction.SetShowSettings -> onAction(
                        HomeSettingsScreenAction.SetShowSettings(action.show)
                    )

                    is HomeSettingsAction.SaveSearchBarRadius -> onAction(
                        HomeSettingsScreenAction.SaveSearchBarRadius(action.radius)
                    )

                    is HomeSettingsAction.SetSwipeUpToSearch -> onAction(
                        HomeSettingsScreenAction.SetSwipeUpToSearch(action.swipeUp)
                    )
                }
            }
        )
    }
}