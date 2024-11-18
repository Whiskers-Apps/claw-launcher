package com.whiskersapps.clawlauncher.views.main.views.apps.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.whiskersapps.clawlauncher.shared.intent.settings.AppsSettingsAction
import com.whiskersapps.clawlauncher.shared.utils.isFoldable
import com.whiskersapps.clawlauncher.shared.view.composables.Dialog
import com.whiskersapps.clawlauncher.shared.view.composables.NavBar
import com.whiskersapps.clawlauncher.shared.view.settings.AppsSettings
import com.whiskersapps.clawlauncher.views.main.views.apps.intent.AppsScreenAction
import com.whiskersapps.clawlauncher.views.main.views.apps.model.AppsScreenState

@Composable
fun AppsSettingsDialog(
    onAction: (AppsScreenAction) -> Unit,
    state: AppsScreenState
) {

    val context = LocalContext.current

    Dialog(
        show = state.showSettingsDialog,
        onDismiss = {onAction(AppsScreenAction.CloseSettingsDialog)}
    ) {
        NavBar(navigateBack = { onAction(AppsScreenAction.CloseSettingsDialog) }, useCloseIcon = true)

        AppsSettings(
            isFoldable = isFoldable(context),
            disableAppsScreen = state.disableAppsScreen,
            viewType = state.viewType,
            phoneCols = state.cols,
            phoneLandscapeCols = state.landscapeCols,
            unfoldedCols = state.unfoldedCols,
            unfoldedLandscapeCols = state.unfoldedLandscapeCols,
            splitList = state.splitList,
            searchBarPosition = state.searchBarPosition,
            showSearchBar = state.showSearchBar,
            showSearchBarPlaceholder = state.showSearchBarPlaceholder,
            showSearchBarSettings = state.showSearchBarSettings,
            searchBarRadius = state.searchBarRadius
        ) { action ->
            when (action) {

                is AppsSettingsAction.SetPhoneCols -> {
                    onAction(AppsScreenAction.SetCols(action.cols))
                }

                is AppsSettingsAction.SetPhoneLandscapeCols -> {
                    onAction(AppsScreenAction.SetLandscapeCols(action.cols))
                }

                is AppsSettingsAction.SetSearchBarPosition -> {
                    onAction(AppsScreenAction.SetSearchBarPosition(action.position))
                }

                is AppsSettingsAction.SetSearchBarRadius -> {
                    onAction(AppsScreenAction.SetSearchBarRadius(action.radius))
                }

                is AppsSettingsAction.SetShowSearchBar -> {
                    onAction(AppsScreenAction.SetShowSearchBar(action.show))
                }

                is AppsSettingsAction.SetShowSearchBarPlaceholder -> {
                    onAction(AppsScreenAction.SetShowSearchBarPlaceholder(action.show))
                }

                is AppsSettingsAction.SetShowSearchBarSettings -> {
                    onAction(AppsScreenAction.SetShowSearchBarSettings(action.show))
                }

                is AppsSettingsAction.SetUnfoldedCols -> {
                    onAction(AppsScreenAction.SetUnfoldedCols(action.cols))
                }

                is AppsSettingsAction.SetUnfoldedLandscapeCols -> {
                    onAction(AppsScreenAction.SetUnfoldedLandscapeCols(action.cols))
                }

                is AppsSettingsAction.SetViewType -> {
                    onAction(AppsScreenAction.SetViewType(action.type))
                }

                is AppsSettingsAction.SetDisableAppsScreen -> {
                    onAction(AppsScreenAction.SetDisableAppsScreen(action.disable))
                }

                is AppsSettingsAction.SetSplitList -> {
                    onAction(AppsScreenAction.SetSplitList(action.split))
                }
            }
        }
    }
}