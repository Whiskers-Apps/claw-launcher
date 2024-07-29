package com.whiskersapps.clawlauncher.views.main.views.home.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.whiskersapps.clawlauncher.R
import com.whiskersapps.clawlauncher.shared.intent.settings.HomeSettingsAction
import com.whiskersapps.clawlauncher.shared.view.settings.HomeSettings
import com.whiskersapps.clawlauncher.shared.view.theme.Typography
import com.whiskersapps.clawlauncher.views.main.views.home.intent.HomeScreenAction
import com.whiskersapps.clawlauncher.views.main.views.home.model.HomeScreenState

@Composable
fun HomeSettingsDialog(
    onAction: (HomeScreenAction) -> Unit,
    state: HomeScreenState
) {
    if (state.showSettingsDialog) {
        Dialog(
            onDismissRequest = { onAction(HomeScreenAction.CloseSettingsDialog) },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {

            Surface(
                Modifier
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
                            painter = painterResource(id = R.drawable.home),
                            contentDescription = "home icon",
                            tint = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Home Settings",
                            style = Typography.titleSmall,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        HomeSettings(
                            showSearchBar = state.showSearchBar,
                            showPlaceholder = state.showPlaceholder,
                            showSettings = state.showSettings,
                            searchBarOpacity = state.searchBarOpacity,
                            searchBarRadius = state.searchBarRadius,
                            onAction = { action ->
                                when (action) {
                                    is HomeSettingsAction.SetSearchBarOpacity -> onAction(
                                        HomeScreenAction.SetSearchBarOpacity(action.opacity)
                                    )

                                    is HomeSettingsAction.SetSearchBarRadius -> onAction(
                                        HomeScreenAction.SetSearchBarRadius(action.radius)
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
            }
        }
    }
}