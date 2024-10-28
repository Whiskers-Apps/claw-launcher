package com.whiskersapps.clawlauncher.shared.data

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.LauncherApps
import android.content.pm.LauncherApps.ShortcutQuery.FLAG_MATCH_DYNAMIC
import android.content.pm.LauncherApps.ShortcutQuery.FLAG_MATCH_MANIFEST
import android.content.pm.LauncherApps.ShortcutQuery.FLAG_MATCH_PINNED
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Process
import android.provider.Settings
import android.util.DisplayMetrics
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

    var allApps = ArrayList<AppShortcut>()

    private val packageManager = app.packageManager

    private val launcherApps = app.getSystemService(Context.LAUNCHER_APPS_SERVICE) as LauncherApps

    init {
        // Listens to package changes and updates the apps list
        CoroutineScope(Dispatchers.IO).launch {
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
            settingsRepository.settings.collect {
                updateShortcuts()
            }
        }
    }

    private suspend fun updateShortcuts() {
        /* This fetching for the shortcuts requires to be in the Main thread
        so this workaround is needed so the app doesn't lag when changing settings. 😅
        * */
        withContext(Dispatchers.Main) {

            val asyncShortcuts = async(Dispatchers.IO) {

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

                        val shortcutQuery = LauncherApps.ShortcutQuery().apply {
                            setQueryFlags(FLAG_MATCH_DYNAMIC or FLAG_MATCH_MANIFEST or FLAG_MATCH_PINNED)
                            setPackage(info.packageName)
                        }

                        val shortcuts: List<AppShortcut.Shortcut> = try {
                            launcherApps.getShortcuts(shortcutQuery, Process.myUserHandle())
                                ?.map { shortcut ->

                                    val icon  = launcherApps.getShortcutIconDrawable(shortcut, DisplayMetrics.DENSITY_LOW)

                                    AppShortcut.Shortcut(
                                        id = shortcut.id,
                                        label = shortcut.shortLabel.toString(),
                                        icon = icon?.toBitmap()
                                    )
                                } ?: emptyList()
                        } catch (e: Exception) {
                            emptyList()
                        }

                        newAppShortcuts.add(
                            AppShortcut(
                                label = info.loadLabel(packageManager).toString(),
                                packageName = info.packageName,
                                icon = BitmapFactory.decodeByteArray(
                                    stream.toByteArray(),
                                    0,
                                    stream.toByteArray().size
                                ),
                                shortcuts = if(shortcuts.size > 4) shortcuts.subList(0, 4) else shortcuts
                            )
                        )
                    }
                }

                newAppShortcuts
            }

            val shortcuts = asyncShortcuts.await()
            shortcuts.sortBy { it.label.lowercase() }

            val hiddenApps = settingsRepository.settings.value.hiddenApps
            val newApps = shortcuts.filterNot { hiddenApps.contains(it.packageName) }

            _apps.update { newApps }
            allApps = shortcuts
        }
    }

    fun openApp(packageName: String) {
        val intent = packageManager.getLaunchIntentForPackage(packageName)
        intent?.let {
            app.startActivity(it)
        }
    }

    fun openShortcut(packageName: String, shortcut: AppShortcut.Shortcut){
        launcherApps.startShortcut(
            packageName,
            shortcut.id,
            null,
            null,
            Process.myUserHandle()
        )
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