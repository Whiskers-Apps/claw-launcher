package com.whiskersapps.clawlauncher.shared.data

import android.app.Application
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.core.graphics.drawable.toBitmap
import com.whiskersapps.clawlauncher.shared.model.AppShortcut
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AppsRepository(
    private val app: Application
) {
    private val _apps = MutableStateFlow<List<AppShortcut>>(ArrayList())
    val apps = _apps.asStateFlow()

    private val packageManager = app.packageManager

    init {
        updateShortcuts()

        // Listens to package changes and updates the apps list
        CoroutineScope(Dispatchers.IO).launch {
            var sequenceNumber = 0

            while (true) {

                val changedPackages = packageManager.getChangedPackages(sequenceNumber)

                if(changedPackages != null){
                    sequenceNumber = changedPackages.sequenceNumber
                    updateShortcuts()
                }

                delay(5000)
            }
        }
    }

    private fun updateShortcuts() {
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