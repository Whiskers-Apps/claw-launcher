package com.whiskersapps.clawlauncher.views.main.views.settings.view.views.apps.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.whiskersapps.clawlauncher.shared.intent.settings.AppsSettingsAction
import com.whiskersapps.clawlauncher.shared.utils.isFoldable
import com.whiskersapps.clawlauncher.shared.view.composables.ContentColumn
import com.whiskersapps.clawlauncher.shared.view.composables.NavBar
import com.whiskersapps.clawlauncher.shared.view.settings.AppsSettings
import com.whiskersapps.clawlauncher.views.main.views.settings.view.views.apps.intent.AppsSettingsScreenAction
import com.whiskersapps.clawlauncher.views.main.views.settings.view.views.apps.model.AppsSettingsScreenVM

@Composable
fun AppsSettingsScreenRoot(
    navController: NavController,
    vm: AppsSettingsScreenVM = hiltViewModel()
) {

    AppsSettingsScreen(
        onAction = { action ->
            when(action){
               AppsSettingsScreenAction.NavigateBack -> navController.navigateUp()
                else -> vm.onAction(action)
            }
        },
        vm = vm
    )
}

@Composable
fun AppsSettingsScreen(
    onAction: (AppsSettingsScreenAction) -> Unit,
    vm: AppsSettingsScreenVM
) {

    val state = vm.state.collectAsState().value
    val context = LocalContext.current

    ContentColumn(
        navigationBar = {
            NavBar(navigateBack = { onAction(AppsSettingsScreenAction.NavigateBack) })
        },
        loading = state.loading
    ) {

        AppsSettings(
            isFoldable = isFoldable(context),
            viewType = state.settings.appsViewType,
            phoneCols = state.settings.portraitCols,
            phoneLandscapeCols = state.settings.landscapeCols,
            unfoldedCols = state.settings.unfoldedPortraitCols,
            unfoldedLandscapeCols = state.settings.unfoldedLandscapeCols,
            backgroundOpacity = state.settings.appsOpacity,
            searchBarPosition = state.settings.appsSearchBarPosition,
            showSearchBar = state.settings.showAppsSearchBar,
            showSearchBarPlaceholder = state.settings.showAppsSearchBarPlaceholder,
            showSearchBarSettings = state.settings.showAppsSearchBarSettings,
            searchBarOpacity = state.settings.appsSearchBarOpacity,
            searchBarRadius = state.settings.appsSearchBarRadius.toFloat()
        ) { action ->
            when (action) {
                is AppsSettingsAction.SetBackgroundOpacity -> onAction(
                    AppsSettingsScreenAction.SetBackgroundOpacity(
                        action.opacity
                    )
                )

                is AppsSettingsAction.SetPhoneCols -> onAction(
                    AppsSettingsScreenAction.SetPhoneCols(
                        action.cols
                    )
                )

                is AppsSettingsAction.SetPhoneLandscapeCols -> onAction(
                    AppsSettingsScreenAction.SetPhoneLandscapeCols(
                        action.cols
                    )
                )

                is AppsSettingsAction.SetSearchBarOpacity -> onAction(
                    AppsSettingsScreenAction.SetSearchBarOpacity(
                        action.opacity
                    )
                )

                is AppsSettingsAction.SetSearchBarPosition -> onAction(
                    AppsSettingsScreenAction.SetSearchBarPosition(
                        action.position
                    )
                )

                is AppsSettingsAction.SetSearchBarRadius -> onAction(
                    AppsSettingsScreenAction.SetSearchBarRadius(
                        action.radius
                    )
                )

                is AppsSettingsAction.SetShowSearchBar -> onAction(
                    AppsSettingsScreenAction.SetShowSearchBar(
                        action.show
                    )
                )

                is AppsSettingsAction.SetShowSearchBarPlaceholder -> onAction(
                    AppsSettingsScreenAction.SetShowSearchBarPlaceholder(action.show)
                )

                is AppsSettingsAction.SetShowSearchBarSettings -> onAction(
                    AppsSettingsScreenAction.SetShowSearchBarSettings(
                        action.show
                    )
                )

                is AppsSettingsAction.SetUnfoldedCols -> onAction(
                    AppsSettingsScreenAction.SetTabletCols(
                        action.cols
                    )
                )

                is AppsSettingsAction.SetUnfoldedLandscapeCols -> onAction(
                    AppsSettingsScreenAction.SetTabletLandscapeCols(
                        action.cols
                    )
                )

                is AppsSettingsAction.SetViewType -> onAction(
                    AppsSettingsScreenAction.SetViewType(
                        action.type
                    )
                )
            }
        }
    }
}