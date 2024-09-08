package com.whiskersapps.clawlauncher.views.main.views.settings.views.security.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.whiskersapps.clawlauncher.R
import com.whiskersapps.clawlauncher.shared.view.composables.AppIcon
import com.whiskersapps.clawlauncher.shared.view.composables.Dialog
import com.whiskersapps.clawlauncher.shared.view.composables.NavBar
import com.whiskersapps.clawlauncher.shared.view.composables.sidePadding
import com.whiskersapps.clawlauncher.views.main.views.settings.views.security.intent.SecuritySettingsScreenAction
import com.whiskersapps.clawlauncher.views.main.views.settings.views.security.model.SecuritySettingsScreenState

@Composable
fun HiddenAppsDialog(
    onAction: (SecuritySettingsScreenAction) -> Unit,
    state: SecuritySettingsScreenState
) {
    Dialog(
        show = state.hiddenAppsDialog.show,
        onDismiss = { onAction(SecuritySettingsScreenAction.CloseHiddenAppsDialog) },
        fullScreen = true,
        scrollable = false
    ) {
        NavBar(
            navigateBack = { onAction(SecuritySettingsScreenAction.CloseHiddenAppsDialog) },
            useCloseIcon = true
        ) {
            Button(onClick = { onAction(SecuritySettingsScreenAction.SaveHiddenApps) }) {
                Text(
                    text = stringResource(R.string.Save),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        LazyColumn(
            modifier = Modifier.sidePadding(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(
                items = state.apps
            ) { app ->

                val selected = state.hiddenAppsDialog.selectedApps.contains(app.packageName)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(CircleShape)
                        .background(if (selected) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.background)
                        .clickable { onAction(SecuritySettingsScreenAction.ToggleHiddenApp(app.packageName)) }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.size(36.dp)) {
                        AppIcon(app = app)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(text = app.label)
                }
            }
        }
    }
}