package com.whiskersapps.clawlauncher.views.main.views.settings.view.views.home.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.whiskersapps.clawlauncher.shared.intent.settings.HomeSettingsAction
import com.whiskersapps.clawlauncher.shared.view.composables.ContentColumn
import com.whiskersapps.clawlauncher.shared.view.composables.NavBar
import com.whiskersapps.clawlauncher.shared.view.settings.HomeSettings
import com.whiskersapps.clawlauncher.views.main.views.settings.view.views.home.intent.HomeSettingsScreenAction
import com.whiskersapps.clawlauncher.views.main.views.settings.view.views.home.model.HomeSettingsScreenVM

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
        navigationBar = {
            NavBar(navigateBack = { onAction(HomeSettingsScreenAction.NavigateBack) })
        },
        loading = state.loading
    ) {
        HomeSettings(
            showSearchBar = state.settings.showHomeSearchBar,
            showPlaceholder = state.settings.showHomeSearchBarPlaceholder,
            showSettings = state.settings.showHomeSearchBarSettings,
            searchBarOpacity = state.settings.homeSearchBarOpacity,
            searchBarRadius = state.settings.homeSearchBarRadius.toFloat(),
            onAction = { action ->
                when (action) {
                    is HomeSettingsAction.SetSearchBarOpacity -> onAction(
                        HomeSettingsScreenAction.SetSearchBarOpacity(action.opacity)
                    )

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
                }
            }
        )
    }
}