package com.whiskersapps.clawlauncher.shared.view.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.whiskersapps.clawlauncher.shared.utils.modifyWhen

@Composable
fun ContentColumn(
    useSystemBarsPadding: Boolean = false,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    scrollable: Boolean = true,
    loading: Boolean = false,
    navigationBar: @Composable RowScope.() -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .modifyWhen(useSystemBarsPadding){
                this.systemBarsPadding()
            }
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = 650.dp)
                    .fillMaxSize()
                    .modifyWhen(scrollable) {
                        this.verticalScroll(rememberScrollState())
                    }
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    navigationBar()
                }

                if (!loading) {
                    content()
                }
            }
        }
    }
}