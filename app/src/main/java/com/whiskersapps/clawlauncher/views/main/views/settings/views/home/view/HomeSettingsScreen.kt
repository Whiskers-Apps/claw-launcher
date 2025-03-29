package com.whiskersapps.clawlauncher.views.main.views.settings.views.home.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import com.whiskersapps.clawlauncher.shared.intent.settings.HomeSettingsAction
import com.whiskersapps.clawlauncher.shared.view.composables.ContentColumn
import com.whiskersapps.clawlauncher.shared.view.composables.NavBar
import com.whiskersapps.clawlauncher.shared.view.settings.HomeSettings
import com.whiskersapps.clawlauncher.views.main.views.settings.views.home.intent.HomeSettingsScreenAction
import com.whiskersapps.clawlauncher.views.main.views.settings.views.home.model.HomeSettingsScreenVM
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeSettingsScreenRoot(
    navController: NavController,
    vm: HomeSettingsScreenVM = koinViewModel(),
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
            tintClock = state.settings.tintClock,
            swipeUpToSearch = state.settings.swipeUpToSearch,
            showSearchBar = state.settings.showHomeSearchBar,
            showPlaceholder = state.settings.showHomeSearchBarPlaceholder,
            showSettings = state.settings.showHomeSearchBarSettings,
            searchBarRadius = state.settings.homeSearchBarRadius.toFloat(),
            clockPlacement = state.settings.clockPlacement,
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

                    is HomeSettingsAction.SetTintIcon -> onAction(
                        HomeSettingsScreenAction.SetTintIcon(action.tint)
                    )

                    is HomeSettingsAction.SetClockPlacement -> {
                        onAction(HomeSettingsScreenAction.SetClockPlacement(action.placement))
                    }
                }
            }
        )
    }
}