package com.whiskersapps.clawlauncher.shared.view.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import com.whiskersapps.clawlauncher.shared.model.App
import com.whiskersapps.clawlauncher.shared.utils.modifyWhen

@Composable
fun AppIcon(
    app: App,
    size: Dp? = null,
    useThemed: Boolean
) {
    val icon by remember { derivedStateOf { if (useThemed) app.icons.themed!!.default.asImageBitmap() else app.icons.stock.default.asImageBitmap() } }

    Box(
        modifier = Modifier
            .modifyWhen(size != null) {
                this.size(size!!)
            }
            .fillMaxHeight()
    ) {
        Image(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f),
//                .scale(1.05f),
            bitmap = icon,
            contentDescription = "${app.name} icon",
            contentScale = ContentScale.FillBounds,
//            colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) })
        )
    }
}