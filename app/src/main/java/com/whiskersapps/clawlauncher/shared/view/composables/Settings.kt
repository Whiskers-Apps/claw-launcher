package com.whiskersapps.clawlauncher.shared.view.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.whiskersapps.clawlauncher.shared.view.theme.Typography
import java.text.DecimalFormat

@Composable
fun SliderSetting(
    title: String,
    description: String,
    min: Float,
    max: Float,
    steps: Int,
    value: Float,
    onValueChange: (value: Float) -> Unit,
    textIsInt: Boolean = false
) {
    Column(Modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = Typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = description,
            style = Typography.labelMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Row {
            Slider(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = true),
                value = value,
                onValueChange = { onValueChange(it) },
                steps = steps,
                valueRange = min..max
            )

            Spacer(modifier = Modifier.width(8.dp))

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Text(text = if (textIsInt) value.toInt().toString() else DecimalFormat("#.#").format(value))
            }
        }
    }
}

@Composable
fun SwitchSetting(
    title: String,
    description: String,
    value: Boolean,
    onValueChange: (value: Boolean) -> Unit
) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
        Column(
            Modifier
                .fillMaxWidth()
                .weight(1f, fill = true)
        ) {
            Text(
                text = title,
                style = Typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = description,
                style = Typography.labelMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Switch(checked = value, onCheckedChange = { onValueChange(it) })
    }
}