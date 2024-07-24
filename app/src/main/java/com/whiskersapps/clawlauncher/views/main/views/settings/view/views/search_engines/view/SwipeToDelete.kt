package com.whiskersapps.clawlauncher.views.main.views.settings.view.views.search_engines.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.whiskersapps.clawlauncher.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeToDelete(
    onDelete: () -> Unit
) {
    var value by remember { mutableFloatStateOf(0f) }
    val source = remember { MutableInteractionSource() }
    val scale = remember { 12f }
    val height = remember { (4 * scale).dp }

    Box(
        contentAlignment = Alignment.Center, modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .clip(CircleShape)
            .border(1.dp, MaterialTheme.colorScheme.error, CircleShape)
    ) {
        Text(text = "Swipe to delete", color = MaterialTheme.colorScheme.error)

        Box(contentAlignment = Alignment.CenterStart) {

            Box(
                Modifier
                    .size(height)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.error)
            )

            Slider(
                value = value,
                onValueChange = { value = it },
                onValueChangeFinished = {
                    if (value == 100f) {
                        onDelete()
                    } else {
                        value = 0f
                    }
                },
                steps = 100,
                valueRange = 0f..100f,
                interactionSource = source,
                track = { sliderState ->
                    SliderDefaults.Track(
                        colors = SliderDefaults.colors(
                            activeTickColor = Color.Transparent,
                            inactiveTickColor = Color.Transparent,
                            activeTrackColor = MaterialTheme.colorScheme.error,
                            inactiveTrackColor = Color.Transparent
                        ),
                        enabled = true,
                        sliderState = sliderState,
                        modifier = Modifier
                            .scale(scaleX = 1f, scaleY = scale)
                    )
                },
                thumb = {
                    Box(
                        Modifier
                            .size(height)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.error),
                        contentAlignment = Alignment.Center
                    ){
                        Icon(
                            modifier = Modifier.size(14.dp),
                            painter = painterResource(id = R.drawable.double_chevron_right),
                            contentDescription = "swipe right icon",
                            tint = MaterialTheme.colorScheme.onError
                        )
                    }
                }
            )
        }
    }
}