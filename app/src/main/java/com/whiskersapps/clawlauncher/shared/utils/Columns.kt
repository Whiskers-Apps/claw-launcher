package com.whiskersapps.clawlauncher.shared.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun getColsCount(
    cols: Int,
    landscapeCols: Int,
    unfoldedCols: Int,
    unfoldedLandscapeCols: Int
): Int {

    val context = LocalContext.current

    /*
        println("Cols: $cols; landscapeCols: $landscapeCols; unfoldedCols: $unfoldedCols; unfoldedLandscapeCols: $unfoldedLandscapeCols")
     */

    // Tested on Pixel Fold Emulator ...

    if (isFoldable(context)) {

        // If unfolded in portrait
        if(isMediumWidth() && isMediumHeight()){
            return unfoldedCols
        }

        // If unfolded in landscape
        if(isMediumHeight() && isExpandedWidth()){
            return unfoldedLandscapeCols
        }
        // If folded in portrait
        return if(isCompactWidth() && isMediumHeight()){
            cols
        }
        // If folded in landscape
        else{
            landscapeCols
        }

    } else {

        //if in landscape
        return if(isExpandedWidth() || isCompactHeight() || isMediumHeight()){
            landscapeCols
        // if in portrait
        }else{
            cols
        }
    }
}