package com.whiskersapps.clawlauncher.launcher.home.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whiskersapps.clawlauncher.shared.utils.modifyWhen

@Composable
fun Clock(
    clock: String,
    date: String,
    tint: Boolean,
    pillShape: Boolean,
    onClick: () -> Unit
) {
    val textShadow = Shadow(
        Color.Black,
        offset = Offset(2f, 2f)
    )

    val textColor = if (tint) {
        MaterialTheme.colorScheme.primary
    } else {
        if (pillShape) {
            MaterialTheme.colorScheme.onBackground
        } else {
            Color.White
        }
    }

    Box(
        modifier = Modifier
            .height(IntrinsicSize.Max)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .clip(CircleShape)
                .modifyWhen(pillShape) {
                    this.background(MaterialTheme.colorScheme.background)
                }
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onClick()
                }
                .padding(start = 48.dp, end = 48.dp, top = 16.dp, bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = clock,
                color = textColor,
                fontSize = if (pillShape) 48.sp else 64.sp,
                fontWeight = FontWeight.SemiBold,
                style = if (pillShape) TextStyle() else TextStyle(shadow = textShadow)
            )

            Text(
                text = date,
                color = textColor,
                fontSize = if (pillShape) 14.sp else 24.sp,
                fontWeight = FontWeight.SemiBold,
                style = if (pillShape) TextStyle() else TextStyle(shadow = textShadow)
            )
        }
    }
}