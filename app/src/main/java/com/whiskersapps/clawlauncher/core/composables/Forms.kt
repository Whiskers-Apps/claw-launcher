package com.whiskersapps.clawlauncher.core.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.whiskersapps.clawlauncher.shared.view.theme.REGULAR_LABEL_STYLE

@Composable
fun TextForm(
    title: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Column {
        Text(
            text = title,
            style = REGULAR_LABEL_STYLE,
            color = MaterialTheme.colorScheme.onBackground
        )

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = { newValue ->
                onValueChange(newValue)
            },
            placeholder = {
                Text(text = placeholder, color = MaterialTheme.colorScheme.onSurfaceVariant)
            },
            shape = CircleShape,
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            singleLine = true
        )
    }
}