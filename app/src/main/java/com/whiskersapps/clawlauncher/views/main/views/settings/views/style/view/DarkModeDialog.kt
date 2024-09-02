package com.whiskersapps.clawlauncher.views.main.views.settings.views.style.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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

        DialogHeader(icon = R.drawable.moon, title = "Dark Mode")

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

            Text(text = "System", color = MaterialTheme.colorScheme.onBackground)
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

            Text(text = "Light", color = MaterialTheme.colorScheme.onBackground)
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

            Text(text = "Dark", color = MaterialTheme.colorScheme.onBackground)
        }

        DialogFooter(
            onDismiss = {
                darkMode = defaultValue
                onDismiss()
            },
            primaryButtonText = "Save",
            onPrimaryClick = { save(darkMode) }
        )
    }
}