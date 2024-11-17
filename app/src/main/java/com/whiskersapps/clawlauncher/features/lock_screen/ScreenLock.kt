package com.whiskersapps.clawlauncher.features.lock_screen

import android.accessibilityservice.AccessibilityServiceInfo
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.PowerManager
import android.provider.Settings
import android.view.accessibility.AccessibilityManager

/**
 * A class for managing the screen lock feature
 */
class ScreenLock(
    private val context: Context
) {
    /**
     * Checks if the screen lock accessibility service is enabled
     */
    @SuppressLint("ServiceCast")
    fun isServiceEnabled(): Boolean {
        val accessibilityManager =
            context.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager

        return accessibilityManager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK)
            .any { service ->
                service.resolveInfo.serviceInfo.packageName == context.packageName
            }
    }

    /**
     * Opens the accessibility settings screen
     */
    fun openAccessibilitySettings() {
        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        context.startActivity(intent)
    }

    /**
     * Opens battery optimization settings screen
     */
    fun openBatteryOptimizationSettings(){
        val intent = Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        context.startActivity(intent)
    }

    /**
     * Checks if the app is being battery optimized
     */
    fun isBatteryOptimized(): Boolean{
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        return !powerManager.isIgnoringBatteryOptimizations(context.packageName)
    }

    /**
     * Locks the device screen
     */
    fun lockScreen() {
        val intent =
            Intent("${context.packageName}.LOCK", null, context, ScreenLockService::class.java)
        context.startService(intent)
    }
}

