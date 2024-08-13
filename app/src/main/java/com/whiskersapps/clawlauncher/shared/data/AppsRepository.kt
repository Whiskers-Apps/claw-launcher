package com.whiskersapps.clawlauncher.shared.data

import android.app.Application
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.AdaptiveIconDrawable
import android.net.Uri
import android.provider.Settings
import androidx.core.graphics.drawable.toBitmap
import com.whiskersapps.clawlauncher.shared.model.AppShortcut
import com.whiskersapps.lib.Sniffer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

class AppsRepository(
    private val app: Application,
    private val settingsRepository: SettingsRepository
) {
    private val _apps = MutableStateFlow<List<AppShortcut>>(ArrayList())
    val apps = _apps.asStateFlow()

    private val packageManager = app.packageManager

    init {

        // Listens to package changes and updates the apps list
        CoroutineScope(Dispatchers.IO).launch {
            updateShortcuts()

            var sequenceNumber = 0

            while (true) {

                val changedPackages = packageManager.getChangedPackages(sequenceNumber)

                if (changedPackages != null) {
                    sequenceNumber = changedPackages.sequenceNumber
                    updateShortcuts()
                }

                delay(3000)
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            settingsRepository.settingsFlow.collect {
                updateShortcuts()
            }
        }
    }

    private suspend fun updateShortcuts() {

        /* This fetching for the shortcuts requires to be in the Main thread
        so this workaround is needed so the app doesn't lag when changing settings. 😅
        * */
        withContext(Dispatchers.Main) {

            val asyncShortcuts = async(Dispatchers.IO){
                val newAppShortcuts = ArrayList<AppShortcut>()
                val intent = Intent(Intent.ACTION_MAIN, null).apply {
                    addCategory(Intent.CATEGORY_LAUNCHER)
                }

                packageManager.queryIntentActivities(intent, 0).forEach { appIntent ->
                    if (appIntent.activityInfo.packageName != "com.whiskersapps.clawlauncher") {

                        val info =
                            packageManager.getApplicationInfo(appIntent.activityInfo.packageName, 0)
                        val iconDrawable = packageManager.getApplicationIcon(info)

                        val stream = ByteArrayOutputStream()
                        iconDrawable.toBitmap().compress(Bitmap.CompressFormat.PNG, 10, stream)

                        newAppShortcuts.add(
                            AppShortcut(
                                label = info.loadLabel(packageManager).toString(),
                                packageName = info.packageName,
                                icon = BitmapFactory.decodeByteArray(
                                    stream.toByteArray(),
                                    0,
                                    stream.toByteArray().size
                                )
                            )
                        )
                    }
                }

                newAppShortcuts
            }

            val shortcuts = asyncShortcuts.await()
            shortcuts.sortBy { it.label.lowercase() }

            _apps.update { shortcuts }
        }
    }

    fun openApp(packageName: String) {
        val intent = packageManager.getLaunchIntentForPackage(packageName)
        intent?.let {
            app.startActivity(it)
        }
    }

    fun getSearchedApps(text: String): List<AppShortcut> {
        val sniffer = Sniffer()

        return apps.value.filter { sniffer.matches(it.label, text) }
    }

    fun openAppInfo(packageName: String) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        app.startActivity(intent)
    }

    fun requestUninstall(packageName: String) {
        val packageUri = Uri.parse("package:${packageName}")
        val intent = Intent(Intent.ACTION_DELETE, packageUri)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        app.startActivity(intent)
    }
}