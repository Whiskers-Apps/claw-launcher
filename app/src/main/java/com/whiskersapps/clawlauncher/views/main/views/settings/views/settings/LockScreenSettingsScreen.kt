package com.whiskersapps.clawlauncher.views.main.views.settings.views.settings

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.whiskersapps.clawlauncher.R
import com.whiskersapps.clawlauncher.shared.model.Routes
import com.whiskersapps.clawlauncher.shared.view.composables.ContentColumn
import com.whiskersapps.clawlauncher.shared.view.composables.NavBar
import com.whiskersapps.clawlauncher.shared.view.composables.sidePadding
import com.whiskersapps.clawlauncher.views.main.views.settings.views.settings.LockScreenSettingsScreenVM.Companion.Action

@Composable
fun LockScreenSettingsScreenRoot(
    navController: NavController,
    goHome: Boolean,
    vm: LockScreenSettingsScreenVM = hiltViewModel()
) {
    LockScreenSettingsScreen(
        vm = vm,
        onAction = { action ->
            when (action) {
                Action.OnNavigateBack -> {
                    if (goHome) {
                        navController.navigate(Routes.Launcher.HOME)
                    } else {
                        navController.navigateUp()
                    }
                }

                else -> {
                    vm.onAction(action)
                }
            }
        }
    )
}

@Composable
fun LockScreenSettingsScreen(
    vm: LockScreenSettingsScreenVM,
    onAction: (Action) -> Unit
) {
    val state = vm.state.collectAsState().value

    // Required so when going back from the home screen it actually goes to the home screen
    BackHandler {
        onAction(Action.OnNavigateBack)
    }

    ContentColumn(
        useSystemBarsPadding = true,
        navigationBar = {
            NavBar(
                navigateBack = {
                    onAction(Action.OnNavigateBack)
                },
                endContent = {
                    Button(
                        enabled = state.accessibilityServiceEnabled,
                        onClick = {
                            onAction(Action.OnLockScreen)
                        }
                    ) {
                        Text("Lock")
                    }
                }
            )
        },
    ) {

        Column(
            modifier = Modifier.sidePadding()
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .clickable {
                        onAction(Action.OnOpenAccessibilitySettings)
                    }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f, fill = true)
                ) {
                    Text(
                        text = stringResource(R.string.HomeScreen_accessibility),
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )

                    Text(
                        text = stringResource(R.string.HomeScreen_accessibility_description),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                Spacer(Modifier.width(16.dp))

                Icon(
                    modifier = Modifier.size(32.dp),
                    painter = if (state.accessibilityServiceEnabled) painterResource(R.drawable.check) else painterResource(
                        R.drawable.close
                    ),
                    tint = MaterialTheme.colorScheme.onBackground,
                    contentDescription = null
                )
            }
        }
    }
}
