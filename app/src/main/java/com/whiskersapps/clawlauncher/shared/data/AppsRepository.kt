package com.whiskersapps.clawlauncher.shared.data

import android.app.Application
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.core.graphics.drawable.toBitmap
import com.whiskersapps.clawlauncher.shared.model.AppShortcut
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AppsRepository(
    private val app: Application
) {
    private val _apps = MutableStateFlow<List<AppShortcut>>(ArrayList())
    val apps = _apps.asStateFlow()

    private val packageManager = app.packageManager

    init {
        val newAppShortcuts = ArrayList<AppShortcut>()

        val intent = Intent(Intent.ACTION_MAIN, null).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }

        val appsIntents = packageManager.queryIntentActivities(intent, 0)

        appsIntents.forEach { appIntent ->
            newAppShortcuts.add(
                AppShortcut(
                    name = appIntent.loadLabel(packageManager).toString(),
                    packageName = appIntent.activityInfo.packageName,
                    icon = appIntent.loadIcon(packageManager).toBitmap()
                )
            )
        }

        newAppShortcuts.sortBy { it.name.lowercase() }

        _apps.update { newAppShortcuts }
    }

    fun openApp(packageName: String) {
        val intent = packageManager.getLaunchIntentForPackage(packageName)
        intent?.let {
            app.startActivity(it)
        }
    }

    fun getSearchedApps(text: String): List<AppShortcut> {
        return apps.value.filter { it.name.lowercase().contains(text.lowercase()) }
    }
}