package com.whiskersapps.clawlauncher.features.lock_screen

import android.accessibilityservice.AccessibilityService
import android.app.Service
import android.content.Intent
import android.content.ServiceConnection
import android.util.Log
import android.view.accessibility.AccessibilityEvent

class ScreenLockService : AccessibilityService() {
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {}

    override fun onInterrupt() {}

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Log.d("Accessibility Action", intent?.action.toString())

        performGlobalAction(GLOBAL_ACTION_LOCK_SCREEN)

        return Service.START_STICKY_COMPATIBILITY
    }
}