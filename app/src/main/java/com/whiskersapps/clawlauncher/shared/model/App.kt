package com.whiskersapps.clawlauncher.shared.model

import android.graphics.Bitmap

/** A class that represents an app. */
data class App(
    /** The app name. */
    val name: String,

    /** The app package name. */
    val packageName: String,

    /** The app icon bitmap. */
    val icons: Icons,

    /** The app shortcuts. */
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

    data class Icons(
        val stock: Icon,
        val themed: Icon?
    )

    data class Icon(
        val default: Bitmap,
        val adaptive: Boolean,
        val foreground: Bitmap?,
        val background: Bitmap?
    )
}
