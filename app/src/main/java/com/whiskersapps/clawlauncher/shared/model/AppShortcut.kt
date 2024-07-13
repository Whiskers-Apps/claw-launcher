package com.whiskersapps.clawlauncher.shared.model

import android.graphics.Bitmap

data class AppShortcut(
    val name: String,
    val packageName: String,
    var icon: Bitmap
)
