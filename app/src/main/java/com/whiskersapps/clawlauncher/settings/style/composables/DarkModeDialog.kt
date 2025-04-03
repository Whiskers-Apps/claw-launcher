package com.whiskersapps.clawlauncher.settings.style.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.whiskersapps.clawlauncher.R
import com.whiskersapps.clawlauncher.shared.view.composables.Dialog
import com.whiskersapps.clawlauncher.shared.view.composables.DialogFooter
import com.whiskersapps.clawlauncher.shared.view.composables.DialogHeader

@Composable
fun DarkModeDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    defaultValue: String,
    save: (String) -> Unit,
) {

    var darkMode by remember { mutableStateOf(defaultValue) }

    Dialog(onDismiss = { onDismiss() }, show = show) {

        Column(modifier = Modifier.padding(16.dp)) {

            DialogHeader(
                icon = R.drawable.moon,
                title = stringResource(R.string.StyleSettings_dark_mode)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { darkMode = "system" },
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = darkMode == "system",
                    onClick = { darkMode = "system" }
                )

                Text(
                    text = stringResource(R.string.StyleSettings_system),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { darkMode = "light" },
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = darkMode == "light",
                    onClick = { darkMode = "light" }
                )

                Text(
                    text = stringResource(R.string.StyleSettings_light),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { darkMode = "dark" },
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = darkMode == "dark",
                    onClick = { darkMode = "dark" }
                )

                Text(
                    text = stringResource(R.string.StyleSettings_dark),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            DialogFooter(
                onDismiss = {
                    darkMode = defaultValue
                    onDismiss()
                },
                primaryButtonText = stringResource(R.string.Save),
                onPrimaryClick = { save(darkMode) }
            )
        }
    }
}