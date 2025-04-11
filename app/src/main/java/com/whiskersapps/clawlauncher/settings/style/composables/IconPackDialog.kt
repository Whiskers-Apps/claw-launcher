package com.whiskersapps.clawlauncher.settings.style.composables

import android.graphics.Bitmap
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.whiskersapps.clawlauncher.R
import com.whiskersapps.clawlauncher.shared.model.App
import com.whiskersapps.clawlauncher.shared.view.composables.DialogFooter
import com.whiskersapps.clawlauncher.shared.view.composables.DialogHeader
import com.whiskersapps.clawlauncher.shared.view.composables.settingPadding
import com.whiskersapps.clawlauncher.shared.view.theme.SMALL_LABEL_STYLE

@Composable
fun IconPackDialog(
    onDismiss: () -> Unit,
    iconPacks: List<App>,
    onIconPackSelected: (String) -> Unit,
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            modifier = Modifier
                .clip(RoundedCornerShape(24.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(top = 24.dp, bottom = 24.dp)
        ) {
            Column {
                DialogHeader(
                    icon = R.drawable.android,
                    title = "Icon Pack"
                )

                LazyColumn {
                    item {
                        IconPackItem(
                            name = stringResource(R.string.StyleSettings_system),
                            onClick = {
                                onIconPackSelected("")
                            }
                        )
                    }
                    items(items = iconPacks, key = { it.packageName }) { iconPack ->
                        IconPackItem(
                            icon = iconPack.icons.stock.default,
                            name = iconPack.name,
                            onClick = {
                                onIconPackSelected(iconPack.packageName)
                            }
                        )
                    }
                }

                Box(modifier = Modifier.settingPadding()) {
                    DialogFooter(onDismiss = { onDismiss() })
                }
            }
        }
    }
}

@Composable
fun IconPackItem(
    icon: Bitmap? = null,
    name: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(top = 8.dp, bottom = 8.dp, start = 24.dp, end = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            Image(
                modifier = Modifier
                    .size(42.dp),
                bitmap = remember { icon }.asImageBitmap(),
                contentDescription = null
            )
        } else {
            Icon(
                modifier = Modifier
                    .size(42.dp),
                painter = painterResource(R.drawable.android),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = name,
            color = MaterialTheme.colorScheme.onBackground,
            style = SMALL_LABEL_STYLE,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis
        )
    }
}