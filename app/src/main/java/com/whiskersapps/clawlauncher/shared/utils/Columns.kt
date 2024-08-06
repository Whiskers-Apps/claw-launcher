package com.whiskersapps.clawlauncher.shared.utils

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun getColsCount(
    cols: Int,
    landscapeCols: Int,
    unfoldedCols: Int,
    unfoldedLandscapeCols: Int
): Int {

    val context = LocalContext.current



    // println("Cols: $cols; landscapeCols: $landscapeCols; unfoldedCols: $unfoldedCols; unfoldedLandscapeCols: $unfoldedLandscapeCols")
    //println("Widths: (${isCompactWidth()}, ${isMediumWidth()}, ${isExpandedWidth()})")
    //println("Heights: (${isCompactHeight()}, ${isMediumHeight()}, ${isExpandedHeight()})")
    //println("Is foldable: ${isFoldable(context)}")

    return if (isFoldable(context)) {
        getSizedCols(cols = unfoldedCols, landscapeCols = unfoldedLandscapeCols )
    } else {
        getSizedCols(cols = cols, landscapeCols = landscapeCols )
    }
}

@Composable
fun getSizedCols(cols: Int, landscapeCols: Int): Int{

    val context = LocalContext.current
    val configuration = context.resources.configuration
    val inPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    return if(inPortrait) cols else landscapeCols
}

