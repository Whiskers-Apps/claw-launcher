package com.whiskersapps.clawlauncher.launcher.lock

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.view.accessibility.AccessibilityEvent

class ScreenLockService : AccessibilityService() {
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {}

    override fun onInterrupt() {}

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val performAction = intent?.action.toString() == "com.whiskersapps.clawlauncher.LOCK"

        if (performAction) {
            performGlobalAction(GLOBAL_ACTION_LOCK_SCREEN)
        }

        return START_STICKY
    }
}