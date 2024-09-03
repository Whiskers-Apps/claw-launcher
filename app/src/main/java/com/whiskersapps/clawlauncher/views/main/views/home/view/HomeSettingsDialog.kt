package com.whiskersapps.clawlauncher.views.main.views.home.view

import androidx.compose.runtime.Composable
import com.whiskersapps.clawlauncher.shared.intent.settings.HomeSettingsAction
import com.whiskersapps.clawlauncher.shared.view.composables.Dialog
import com.whiskersapps.clawlauncher.shared.view.composables.NavBar
import com.whiskersapps.clawlauncher.shared.view.settings.HomeSettings
import com.whiskersapps.clawlauncher.views.main.views.home.intent.HomeScreenAction
import com.whiskersapps.clawlauncher.views.main.views.home.model.HomeScreenState

@Composable
fun HomeSettingsDialog(
    onAction: (HomeScreenAction) -> Unit,
    state: HomeScreenState
) {
    Dialog(
        show = state.showSettingsDialog,
        onDismiss = { onAction(HomeScreenAction.CloseSettingsDialog) }
    ) {
        NavBar(
            navigateBack = { onAction(HomeScreenAction.CloseSettingsDialog) },
            useCloseIcon = true
        )

        HomeSettings(
            showSearchBar = state.showSearchBar,
            showPlaceholder = state.showPlaceholder,
            showSettings = state.showSearchBarSettings,
            searchBarRadius = state.searchBarRadius,
            onAction = { action ->
                when (action) {

                    is HomeSettingsAction.SetSearchBarRadius -> onAction(
                        HomeScreenAction.SetSearchBarRadius(action.radius)
                    )

                    is HomeSettingsAction.SaveSearchBarRadius -> onAction(
                        HomeScreenAction.SaveSearchBarRadius(action.radius)
                    )

                    is HomeSettingsAction.SetShowSearchBar -> onAction(
                        HomeScreenAction.SetShowSearchBar(action.show)
                    )

                    is HomeSettingsAction.SetShowSearchBarPlaceholder -> onAction(
                        HomeScreenAction.SetShowSearchBarPlaceholder(action.show)
                    )

                    is HomeSettingsAction.SetShowSettings -> onAction(
                        HomeScreenAction.SetShowSettings(action.show)
                    )
                }
            }
        )
    }
}