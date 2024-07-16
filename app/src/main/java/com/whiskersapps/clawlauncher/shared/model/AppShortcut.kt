package com.whiskersapps.clawlauncher.shared.model

import android.graphics.Bitmap

data class AppShortcut(
    val name: String = "",
    val packageName: String = "",
    val isAdaptive: Boolean = false,
    val icon: Bitmap,
    val background: Bitmap? = null,
    val foreground: Bitmap? = null
)
