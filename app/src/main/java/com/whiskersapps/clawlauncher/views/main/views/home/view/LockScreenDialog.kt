package com.whiskersapps.clawlauncher.views.main.views.home.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.whiskersapps.clawlauncher.R
import com.whiskersapps.clawlauncher.shared.view.composables.Dialog
import com.whiskersapps.clawlauncher.shared.view.composables.DialogFooter
import com.whiskersapps.clawlauncher.shared.view.composables.DialogHeader

@Composable
fun LockScreenDialog(
    show: Boolean,
    onClose: () -> Unit,
    onOpenSettings: () -> Unit
) {
    Dialog(
        show = show,
        onDismiss = {
            onClose()
        },
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            DialogHeader(
                icon = R.drawable.lock,
                title = stringResource(R.string.HomeScreen_lock_screen)
            )

            Text(
                text = stringResource(R.string.HomeScreen_lock_screen_description),
                color = MaterialTheme.colorScheme.onBackground
            )

            DialogFooter(
                primaryButtonText = stringResource(R.string.HomeScreen_open_settings),
                onDismiss = {
                    onClose()
                },
                onPrimaryClick = {
                    onOpenSettings()
                }
            )
        }
    }
}