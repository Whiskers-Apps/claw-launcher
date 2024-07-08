package com.lighttigerxiv.clawlauncher.shared.model

import android.graphics.Bitmap

data class AppShortcut(
    val name: String,
    val packageName: String,
    val icon: Bitmap
)
