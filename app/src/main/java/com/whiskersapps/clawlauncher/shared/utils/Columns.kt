package com.whiskersapps.clawlauncher.shared.utils

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun inPortrait(): Boolean {
    val context = LocalContext.current
    val configuration = context.resources.configuration
    return configuration.orientation == Configuration.ORIENTATION_PORTRAIT
}