package com.whiskersapps.clawlauncher.shared.view.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import com.whiskersapps.clawlauncher.shared.model.AppShortcut
import com.whiskersapps.clawlauncher.shared.utils.modifyWhen

@Composable
fun AppIcon(
    app: AppShortcut,
    shape: RoundedCornerShape = CircleShape,
) {
    val stockIcon by remember { derivedStateOf { app.icon.stock.asImageBitmap() } }
    val themedIcon by remember { derivedStateOf { app.icon.themed?.asImageBitmap() } }
    val background by remember { derivedStateOf { app.icon.adaptive?.background?.asImageBitmap() } }
    val foreground by remember { derivedStateOf { app.icon.adaptive?.foreground?.asImageBitmap() } }

    if (app.icon.adaptive != null) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .clip(shape)
        ) {
            Image(
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f),
                bitmap = background!!,
                contentDescription = "${app.packageName} background",
                contentScale = ContentScale.FillBounds
            )

            Image(
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f)
                    .scale(1.4f),
                bitmap = foreground!!,
                contentDescription = "${app.packageName} foreground",
                contentScale = ContentScale.Crop
            )
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .modifyWhen(app.icon.themed == null) {
                    this
                        .clip(shape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                }
        ) {
            Image(
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f),
                bitmap = if (app.icon.themed != null) themedIcon!! else stockIcon,
                contentDescription = "${app.packageName} icon",
                contentScale = ContentScale.Crop
            )
        }
    }
}