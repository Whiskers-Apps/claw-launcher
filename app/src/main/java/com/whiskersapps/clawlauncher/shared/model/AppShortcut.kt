package com.whiskersapps.clawlauncher.shared.model

import android.graphics.Bitmap

data class AppShortcut(
    val label: String = "",
    val packageName: String = "",
    val icon: Bitmap,
    val shortcuts: List<Shortcut> = emptyList()
){
    data class Shortcut(
        val id: String = "",
        val label: String = "",
        val icon: Bitmap? = null
    )
}
