package com.whiskersapps.clawlauncher.views.main.views.home.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.whiskersapps.clawlauncher.R
import com.whiskersapps.clawlauncher.shared.view.composables.Dialog
import com.whiskersapps.clawlauncher.shared.view.composables.DialogFooter
import com.whiskersapps.clawlauncher.shared.view.composables.DialogHeader

@Composable
fun ScreenLockDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    serviceEnabled: Boolean,
    batteryOptimized: Boolean,
    onOpenAppInfo: () -> Unit,
    onOpenAccessibilitySettings: () -> Unit,
    onOpenBatteryOptimizationSettings: () -> Unit,
    onOk: () -> Unit
) {

    Dialog(
        show = show,
        onDismiss = {
            onDismiss()
        }
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            DialogHeader(
                icon = R.drawable.lock,
                title = stringResource(R.string.HomeScreen_lock_screen)
            )

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f, fill = true)
                ) {
                    Text(
                        text = stringResource(R.string.HomeScreen_restricted_access),
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )

                    Text(
                        text = stringResource(R.string.HomeScreen_restricted_access_description),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                Spacer(Modifier.width(16.dp))

                Button(
                    onClick = {
                        onOpenAppInfo()
                    }
                ) {
                    Text(text = "Open")
                }
            }

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
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

                Button(
                    onClick = {
                        onOpenAccessibilitySettings()
                    },
                    enabled = !serviceEnabled
                ) {
                    Text(text = "Enable")
                }
            }

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f, fill = true)
                ) {
                    Text(
                        text = stringResource(R.string.HomeScreen_battery_optimization),
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )

                    Text(
                        text = stringResource(R.string.HomeScreen_battery_optimization_description),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                Spacer(Modifier.width(16.dp))

                Button(
                    onClick = {
                        onOpenBatteryOptimizationSettings()
                    },
                    enabled = batteryOptimized
                ) {
                    Text(text = stringResource(R.string.Disable))
                }
            }

            DialogFooter(
                onDismiss = {
                    onDismiss()
                },
                onPrimaryClick = {
                    onOk()
                },
                primaryButtonText = "OK"
            )
        }
    }
}