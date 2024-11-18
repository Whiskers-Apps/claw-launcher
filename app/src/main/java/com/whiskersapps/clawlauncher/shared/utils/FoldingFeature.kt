package com.whiskersapps.clawlauncher.shared.utils

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun isFoldable(context: Context): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) context.packageManager.hasSystemFeature(
        PackageManager.FEATURE_SENSOR_HINGE_ANGLE
    ) else
        false
}