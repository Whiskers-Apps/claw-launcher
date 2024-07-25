package com.whiskersapps.clawlauncher.views.main.views.apps.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.whiskersapps.clawlauncher.R
import com.whiskersapps.clawlauncher.shared.intent.settings.AppsSettingsAction
import com.whiskersapps.clawlauncher.shared.utils.isFoldable
import com.whiskersapps.clawlauncher.shared.view.settings.AppsSettings
import com.whiskersapps.clawlauncher.shared.view.theme.Typography
import com.whiskersapps.clawlauncher.views.main.views.apps.intent.AppsScreenAction
import com.whiskersapps.clawlauncher.views.main.views.apps.model.AppsScreenState

@Composable
fun AppsSettingsDialog(
    onAction: (AppsScreenAction) -> Unit,
    state: AppsScreenState
) {

    val context = LocalContext.current

    if (state.showSettingsDialog) {
        Dialog(
            onDismissRequest = {
                onAction(AppsScreenAction.CloseSettingsDialog)
            },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {

            Surface(
                Modifier
                    .widthIn(max = 650.dp)
                    .fillMaxSize()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .padding(24.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            painter = painterResource(id = R.drawable.apps),
                            contentDescription = "home icon",
                            tint = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Apps Settings",
                            style = Typography.titleSmall,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        AppsSettings(
                            isFoldable = isFoldable(context),
                            viewType = state.viewType,
                            phoneCols = state.cols,
                            phoneLandscapeCols = state.landscapeCols,
                            unfoldedCols = state.unfoldedCols,
                            unfoldedLandscapeCols = state.unfoldedLandscapeCols,
                            backgroundOpacity = state.opacity,
                            searchBarPosition = state.searchBarPosition,
                            showSearchBar = state.showSearchBar,
                            showSearchBarPlaceholder = state.showSearchBarPlaceholder,
                            showSearchBarSettings = state.showSearchBarSettings,
                            searchBarOpacity = state.searchBarOpacity,
                            searchBarRadius = state.searchBarRadius?.value
                        ) { action ->
                            when (action) {
                                is AppsSettingsAction.SetBackgroundOpacity -> onAction(
                                    AppsScreenAction.SetBackgroundOpacity(
                                        action.opacity
                                    )
                                )

                                is AppsSettingsAction.SetPhoneCols -> onAction(
                                    AppsScreenAction.SetPhoneCols(
                                        action.cols
                                    )
                                )

                                is AppsSettingsAction.SetPhoneLandscapeCols -> onAction(
                                    AppsScreenAction.SetPhoneLandscapeCols(
                                        action.cols
                                    )
                                )

                                is AppsSettingsAction.SetSearchBarOpacity -> onAction(
                                    AppsScreenAction.SetSearchBarOpacity(
                                        action.opacity
                                    )
                                )

                                is AppsSettingsAction.SetSearchBarPosition -> onAction(
                                    AppsScreenAction.SetSearchBarPosition(
                                        action.position
                                    )
                                )

                                is AppsSettingsAction.SetSearchBarRadius -> onAction(
                                    AppsScreenAction.SetSearchBarRadius(
                                        action.radius
                                    )
                                )

                                is AppsSettingsAction.SetShowSearchBar -> onAction(
                                    AppsScreenAction.SetShowSearchBar(
                                        action.show
                                    )
                                )

                                is AppsSettingsAction.SetShowSearchBarPlaceholder -> onAction(
                                    AppsScreenAction.SetShowSearchBarPlaceholder(action.show)
                                )

                                is AppsSettingsAction.SetShowSearchBarSettings -> onAction(
                                    AppsScreenAction.SetShowSearchBarSettings(
                                        action.show
                                    )
                                )

                                is AppsSettingsAction.SetUnfoldedCols -> onAction(
                                    AppsScreenAction.SetUnfoldedCols(
                                        action.cols
                                    )
                                )

                                is AppsSettingsAction.SetUnfoldedLandscapeCols -> onAction(
                                    AppsScreenAction.SetUnfoldedLandscapeCols(
                                        action.cols
                                    )
                                )

                                is AppsSettingsAction.SetViewType -> onAction(
                                    AppsScreenAction.SetViewType(
                                        action.type
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}