package com.whiskersapps.clawlauncher.shared.model

import android.graphics.Bitmap

data class App(
    val label: String = "",
    val packageName: String = "",
    val icon: Bitmap,
    val shortcuts: List<Shortcut> = emptyList()
) {
    /** An app shortcut like new tab and private tab on firefox. */
    data class Shortcut(
        /** The shortcut Id.*/
        val id: String = "",
        /** The shortcut label.*/
        val label: String = "",
        /** The shortcut icon bitmap.*/
        val icon: Bitmap? = null
    )
}
