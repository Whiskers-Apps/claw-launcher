package com.whiskersapps.clawlauncher.shared.view.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow


@Composable
fun WhiskersText(
    text: String,
    style: TextStyle,
    color: Color = MaterialTheme.colorScheme.onBackground,
    maxLines: Int? = null
) {
    Text(
        text = text,
        color = color,
        style = style,
        maxLines = if (maxLines == null) {
            2
        } else {
            maxLines
        },
        overflow = TextOverflow.Ellipsis
    )
}