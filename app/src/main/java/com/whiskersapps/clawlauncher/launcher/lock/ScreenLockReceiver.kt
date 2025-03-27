package com.whiskersapps.clawlauncher.launcher.lock

import android.content.Context
import android.content.Intent
import android.util.Log

class ScreenLockReceiver : android.app.admin.DeviceAdminReceiver() {
    override fun onEnabled(context: Context, intent: Intent) {
        super.onEnabled(context, intent)
        Log.d("Receiver", "Receiver Enabled")
    }

    override fun onDisabled(context: Context, intent: Intent) {
        super.onDisabled(context, intent)
        Log.d("Receiver", "Receiver Disabled")
    }
}