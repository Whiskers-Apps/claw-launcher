package com.whiskersapps.clawlauncher.onboarding.composables

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OnBoardingButton(
    text: String,
    secondary: Boolean = false,
    onClick: () -> Unit
) {
    if (secondary) {
        OutlinedButton(
            onClick = { onClick() }
        ) {
            Text(
                text = text,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    } else {
        Button(
            onClick = { onClick() }
        ) {
            Text(
                text = text,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }


}
