package com.whiskersapps.clawlauncher.views.main.views.settings.views.apps.view

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
import com.whiskersapps.clawlauncher.views.main.views.settings.views.apps.intent.AppsSettingsScreenAction
import com.whiskersapps.clawlauncher.views.main.views.settings.views.apps.model.AppsSettingsScreenVM

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
        useSystemBarsPadding = true,
        navigationBar = {
            NavBar(navigateBack = { onAction(AppsSettingsScreenAction.NavigateBack) })
        },
        loading = state.loading
    ) {

        AppsSettings(
            isFoldable = isFoldable(context),
            disableAppsScreen = state.settings.disableAppsScreen,
            viewType = state.settings.appsViewType,
            phoneCols = state.settings.portraitCols,
            phoneLandscapeCols = state.settings.landscapeCols,
            unfoldedCols = state.settings.unfoldedPortraitCols,
            unfoldedLandscapeCols = state.settings.unfoldedLandscapeCols,
            splitList = state.settings.splitListView,
            searchBarPosition = state.settings.appsSearchBarPosition,
            showSearchBar = state.settings.showAppsSearchBar,
            showSearchBarPlaceholder = state.settings.showAppsSearchBarPlaceholder,
            showSearchBarSettings = state.settings.showAppsSearchBarSettings,
            searchBarRadius = state.settings.appsSearchBarRadius
        ) { action ->
            when (action) {

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

                is AppsSettingsAction.SetDisableAppsScreen -> onAction(
                    AppsSettingsScreenAction.SetDisableAppsScreen(
                        action.disable
                    )
                )

                is AppsSettingsAction.SetSplitList -> {
                    onAction(AppsSettingsScreenAction.SetSplitList(action.split))
                }
            }
        }
    }
}