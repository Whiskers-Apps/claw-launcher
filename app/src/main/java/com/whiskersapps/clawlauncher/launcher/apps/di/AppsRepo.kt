package com.whiskersapps.clawlauncher.launcher.apps.di

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.LauncherApps
import android.content.pm.LauncherApps.ShortcutQuery.FLAG_MATCH_DYNAMIC
import android.content.pm.LauncherApps.ShortcutQuery.FLAG_MATCH_MANIFEST
import android.content.pm.LauncherApps.ShortcutQuery.FLAG_MATCH_PINNED
import android.net.Uri
import android.os.Process
import android.provider.Settings
import android.util.DisplayMetrics
import androidx.core.graphics.drawable.toBitmap
import androidx.core.net.toUri
import com.whiskersapps.clawlauncher.icon_packs.IconPacksRepo
import com.whiskersapps.clawlauncher.settings.di.SettingsRepo
import com.whiskersapps.clawlauncher.shared.model.App
import com.whiskersapps.clawlauncher.shared.model.App.Shortcut
import com.whiskersapps.lib.Sniffer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppsRepo(
    private val app: Application,
    private val settingsRepo: SettingsRepo,
    private val iconPacksRepo: IconPacksRepo,
) {
    private val _apps = MutableStateFlow<List<App>>(ArrayList())
    val apps = _apps.asStateFlow()

    var allApps = emptyList<App>()

    private val packageManager = app.packageManager

    private val launcherApps = app.getSystemService(Context.LAUNCHER_APPS_SERVICE) as LauncherApps

    init {
        // Listens to package changes and updates the apps list
        CoroutineScope(Dispatchers.Main).launch {
            var sequenceNumber = 0

            while (true) {
                val changedPackages = packageManager.getChangedPackages(sequenceNumber)

                if (changedPackages != null) {
                    sequenceNumber = changedPackages.sequenceNumber
                    fetchApps()
                }

                delay(3000)
            }
        }
    }

    fun fetchApps() {
        CoroutineScope(Dispatchers.Main).launch {
            val launcherIntent = Intent(Intent.ACTION_MAIN, null).apply {
                addCategory(Intent.CATEGORY_LAUNCHER)
            }

            val newApps = packageManager.queryIntentActivities(launcherIntent, 0).map { appIntent ->
                val packageName = appIntent.activityInfo.packageName
                val appInfo = packageManager.getApplicationInfo(packageName, 0)

                val name = appInfo.loadLabel(packageManager).toString()

                val shortcutQuery = LauncherApps.ShortcutQuery().apply {
                    setQueryFlags(FLAG_MATCH_DYNAMIC or FLAG_MATCH_MANIFEST or FLAG_MATCH_PINNED)
                    setPackage(packageName)
                }

                val shortcuts: List<Shortcut> = try {
                    launcherApps.getShortcuts(shortcutQuery, Process.myUserHandle())
                        ?.map { shortcut ->

                            val icon = launcherApps.getShortcutIconDrawable(
                                shortcut,
                                DisplayMetrics.DENSITY_MEDIUM
                            )

                            Shortcut(
                                id = shortcut.id,
                                label = shortcut.shortLabel.toString(),
                                icon = icon?.toBitmap()
                            )
                        } ?: emptyList()
                } catch (e: Exception) {
                    emptyList()
                }

                App(
                    name = name,
                    packageName = packageName,
                    icons = iconPacksRepo.getAppIcons(packageName),
                    shortcuts = shortcuts
                )
            }
                .filter { it.packageName != "com.whiskersapps.clawlauncher" }
                .sortedBy { it.name.lowercase() }
                .distinctBy { it.packageName }

            allApps = newApps

            val blacklistedApps = settingsRepo.settings.value.hiddenApps
            val displayableApps = newApps.filterNot { blacklistedApps.contains(it.packageName) }

            _apps.update { displayableApps }

            iconPacksRepo.fetchIconPacks()
        }
    }

    fun openApp(packageName: String) {
        val intent = packageManager.getLaunchIntentForPackage(packageName)
        intent?.let {
            app.startActivity(it)
        }
    }

    fun openShortcut(packageName: String, shortcut: Shortcut) {
        launcherApps.startShortcut(
            packageName,
            shortcut.id,
            null,
            null,
            Process.myUserHandle()
        )
    }

    fun getSearchedApps(text: String): List<App> {
        val sniffer = Sniffer()
        return apps.value.filter { sniffer.matches(it.name, text) }
    }

    fun openAppInfo(packageName: String) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        app.startActivity(intent)
    }

    fun requestUninstall(packageName: String) {
        val packageUri = "package:${packageName}".toUri()
        val intent = Intent(Intent.ACTION_DELETE, packageUri)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        app.startActivity(intent)
    }
}