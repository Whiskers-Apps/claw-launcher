package com.whiskersapps.clawlauncher.views.main.views.settings.views.style.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.whiskersapps.clawlauncher.R
import com.whiskersapps.clawlauncher.shared.model.IconPack
import com.whiskersapps.clawlauncher.shared.view.composables.DialogFooter
import com.whiskersapps.clawlauncher.shared.view.composables.DialogHeader

@Composable
fun IconPackDialog(
    onDismiss: () -> Unit,
    iconPacks: List<IconPack>,
    setIconPack: (String) -> Unit,
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(24.dp)
        ) {
            Column {
                DialogHeader(
                    icon = R.drawable.android,
                    title = "Icon Pack"
                )

                LazyVerticalGrid(columns = GridCells.Fixed(4)) {
                    item{
                        Column(
                            modifier = Modifier.clickable { setIconPack("system") },
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .padding(8.dp),
                                painter = painterResource(id = R.drawable.android),
                                contentDescription = "android icon",

                                )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "System",
                                color = MaterialTheme.colorScheme.onBackground,
                                fontSize = 12.sp
                            )
                        }
                    }
                    items(items = iconPacks, key = { it.packageName }) { iconPack ->
                        Column(
                            modifier = Modifier.clickable { setIconPack(iconPack.packageName) },
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape),
                                bitmap = remember { iconPack.icon.asImageBitmap() },
                                contentDescription = "${iconPack.name} icon"
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = iconPack.name,
                                color = MaterialTheme.colorScheme.onBackground,
                                fontSize = 12.sp
                            )
                        }
                    }
                }

                DialogFooter(onDismiss = { onDismiss() })
            }
        }
    }
}