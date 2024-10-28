package com.whiskersapps.clawlauncher.views.main.views.home.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Clock(
    clock: String,
    date: String,
    onClick: () -> Unit
) {

    val textShadow = Shadow(
        Color.Black,
        offset = Offset(2f, 2f)
    )

    Box(
        modifier = Modifier
            .height(IntrinsicSize.Max)
            .fillMaxWidth()
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onClick()
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = clock,
                color = Color.White,
                fontSize = 69.sp,
                fontWeight = FontWeight.SemiBold,
                style = TextStyle(shadow = textShadow)
            )

            Text(
                text = date,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                style = TextStyle(shadow = textShadow)
            )
        }
    }
}