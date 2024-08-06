package com.whiskersapps.clawlauncher.shared.model

import android.graphics.Bitmap

data class AppShortcut(
    val label: String = "",
    val packageName: String = "",
    val icon: Icon
){
    data class Icon(
        val stock: Bitmap,
        val adaptive: Adaptive? = null
    ){
        data class Adaptive(
            val background: Bitmap,
            val foreground: Bitmap
        )
    }
}
