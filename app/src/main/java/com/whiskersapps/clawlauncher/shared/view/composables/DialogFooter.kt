package com.whiskersapps.clawlauncher.shared.view.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.whiskersapps.clawlauncher.shared.utils.modifyWhen

@Composable
fun DialogFooter(
    primaryButtonText: String = "",
    onDismiss: () -> Unit,
    enabled: Boolean = true,
    onPrimaryClick: () -> Unit = {},
) {
    Row {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            OutlinedButton(
                modifier = Modifier.modifyWhen(primaryButtonText.isEmpty()){
                    this.fillMaxWidth()
                },
                onClick = { onDismiss() },
                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent)
            ) {
                Text(text = "Cancel")
            }

            if (primaryButtonText.isNotEmpty()) {
                Spacer(modifier = Modifier.width(8.dp))

                Button(onClick = { onPrimaryClick() }, enabled = enabled) {
                    Text(text = primaryButtonText)
                }
            }
        }
    }
}