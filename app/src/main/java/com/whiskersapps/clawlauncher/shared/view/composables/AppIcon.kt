package com.whiskersapps.clawlauncher.shared.view.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.whiskersapps.clawlauncher.shared.model.AppShortcut
import com.whiskersapps.clawlauncher.shared.utils.modifyWhen

@Composable
fun AppIcon(
    app: AppShortcut
) {
    val stockIcon by remember { derivedStateOf { app.icon.asImageBitmap() } }

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .clip(CircleShape)
            .background(Color.White)
    ) {
        Image(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f),
            bitmap = stockIcon,
            contentDescription = "${app.packageName} icon",
            contentScale = ContentScale.Crop
        )
    }
}