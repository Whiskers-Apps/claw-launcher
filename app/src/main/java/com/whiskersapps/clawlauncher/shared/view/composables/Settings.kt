package com.whiskersapps.clawlauncher.shared.view.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
    onValueChangeFinished: (Float) -> Unit = {}
) {
    Column(Modifier.fillMaxWidth().padding(16.dp)) {
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
                onValueChangeFinished = { onValueChangeFinished(value) },
                steps = steps,
                valueRange = min..max,
                colors = SliderDefaults.colors(
                    inactiveTickColor = Color.Transparent,
                    activeTickColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.width(8.dp))

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = value.toInt().toString(),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
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
    Row(
        Modifier
            .fillMaxWidth()
            .clickable { onValueChange(!value) }
            .padding(16.dp),
        verticalAlignment = Alignment.Top
    ) {
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

@Composable
fun SimpleSetting(
    title: String,
    value: String,
    onClick: () -> Unit
) {
    Column(
        Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Text(
            text = title,
            style = Typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = value,
            style = Typography.labelMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}