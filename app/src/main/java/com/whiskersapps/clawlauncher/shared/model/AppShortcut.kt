package com.whiskersapps.clawlauncher.shared.model

import android.graphics.Bitmap

data class AppShortcut(
    val label: String = "",
    val packageName: String = "",
    val icon: Bitmap
)
