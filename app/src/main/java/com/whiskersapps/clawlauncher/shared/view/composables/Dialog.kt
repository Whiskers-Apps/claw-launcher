package com.whiskersapps.clawlauncher.shared.view.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.whiskersapps.clawlauncher.shared.utils.modifyWhen

@Composable
fun Dialog(
    show: Boolean,
    onDismiss: () -> Unit,
    fullScreen: Boolean = false,
    content: @Composable ColumnScope.() -> Unit
) {
    if (show) {
        androidx.compose.ui.window.Dialog(
            onDismissRequest = { onDismiss() },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Surface(
                Modifier
                    .modifyWhen(fullScreen) {
                        this.fillMaxSize()
                    }
                    .modifyWhen(!fullScreen) {
                        this.widthIn(max = 650.dp)
                    }
                    .padding(16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp)

            ) {
                Column(modifier = Modifier
                    .modifyWhen(fullScreen){
                        this.fillMaxSize()
                    }
                    .background(MaterialTheme.colorScheme.background)
                    .verticalScroll(rememberScrollState())
                ) {
                    content()
                }
            }
        }
    }
}