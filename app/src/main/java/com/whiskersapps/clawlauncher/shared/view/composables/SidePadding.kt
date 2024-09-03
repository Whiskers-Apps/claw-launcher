package com.whiskersapps.clawlauncher.shared.view.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.sidePadding(): Modifier {
    return this.padding(start = 16.dp, end = 16.dp)
}