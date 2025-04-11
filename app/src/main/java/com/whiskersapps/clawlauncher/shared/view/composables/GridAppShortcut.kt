package com.whiskersapps.clawlauncher.shared.view.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.whiskersapps.clawlauncher.shared.model.App
import com.whiskersapps.clawlauncher.shared.view.theme.Typography


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GridAppShortcut(
    app: App,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    radius: Dp = 8.dp,
    openApp: () -> Unit,
    openInfo: () -> Unit,
    requestUninstall: () -> Unit,
    openShortcut: (App.Shortcut) -> Unit
) {

    var showPopup by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(radius))
            .background(backgroundColor)
            .combinedClickable(
                onClick = { openApp() },
                onLongClick = { showPopup = true },
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AppIcon(app = app)

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = remember { app.name },
            color = MaterialTheme.colorScheme.onBackground,
            style = Typography.labelSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        if (showPopup) {
            AppPopup(
                app = app,
                onDismiss = { showPopup = false },
                onInfoClick = {
                    openInfo()
                    showPopup = false
                },
                onUninstallClick = {
                    requestUninstall()
                    showPopup = false
                },
                onOpenShortcut = { shortcut ->
                    openShortcut(shortcut)
                    showPopup = false
                }
            )
        }
    }
}