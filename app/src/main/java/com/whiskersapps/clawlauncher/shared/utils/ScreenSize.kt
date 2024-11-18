package com.whiskersapps.clawlauncher.shared.utils

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowWidthSizeClass

/*
Information got at https://developer.android.com/develop/ui/compose/layouts/adaptive/window-size-classes
 */


/**
 * Returns if it's a phone landscape
 */
@Composable
fun isCompactHeight(): Boolean {
    return currentWindowAdaptiveInfo().windowSizeClass.windowHeightSizeClass == WindowHeightSizeClass.COMPACT
}

/**
 * Returns if it's a phone in portrait or a tablet in landscape
 */
@Composable
fun isMediumHeight(): Boolean {
    return currentWindowAdaptiveInfo().windowSizeClass.windowHeightSizeClass == WindowHeightSizeClass.MEDIUM
}

/**
 * Returns if it's a tablet in portrait
 */
@Composable
fun isExpandedHeight(): Boolean {
    return currentWindowAdaptiveInfo().windowSizeClass.windowHeightSizeClass == WindowHeightSizeClass.EXPANDED
}

/**
 * Returns if it's a phone in portrait
 */
@Composable
fun isCompactWidth(): Boolean {
    return currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT
}

/**
 * Returns if it's a expanded foldable like Galaxy Fold 6 in portrait or a tablet in portrait
 */
@Composable
fun isMediumWidth(): Boolean {
    return currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.MEDIUM
}

/**
 * Returns if it's a expandable foldable like Galaxy Fold 6 in landscape or a tablet in landscape
 */
@Composable
fun isExpandedWidth(): Boolean {
    return currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.EXPANDED
}

/**
 * Returns if the screen width is wide enough to split the screen
 */
@Composable
fun isSplitAvailable(): Boolean = isMediumWidth() || isExpandedWidth()