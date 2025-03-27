package com.whiskersapps.clawlauncher.launcher.lock

import android.accessibilityservice.AccessibilityServiceInfo
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
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
     * Locks the device screen
     */
    fun lockScreen() {
        if (isServiceEnabled()) {
            val intent =
                Intent("${context.packageName}.LOCK", null, context, ScreenLockService::class.java)
            context.startService(intent)
        }
    }
}

