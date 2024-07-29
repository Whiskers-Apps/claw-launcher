package com.whiskersapps.clawlauncher.shared.data

import android.app.Application
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.AdaptiveIconDrawable
import android.net.Uri
import android.provider.Settings
import androidx.core.graphics.drawable.toBitmap
import com.iamverycute.iconpackmanager.IconPackManager
import com.whiskersapps.clawlauncher.shared.model.AppShortcut
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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

    private data class Pack(
        val name: String,
        val packageName: String
    )

    private fun updateShortcuts() {

        val settings = settingsRepository.settings

        val newAppShortcuts = ArrayList<AppShortcut>()
        val iconPackManager = IconPackManager(app)
        val iconPackMap = iconPackManager.isSupportedIconPacks()
            .filterValues { it.getPackageName() == settings.iconPack }

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


                var shortcut = AppShortcut(
                    label = info.loadLabel(packageManager).toString(),
                    packageName = info.packageName,
                    icon = AppShortcut.Icon(
                        stock = BitmapFactory.decodeByteArray(
                            stream.toByteArray(),
                            0,
                            stream.toByteArray().size
                        )
                    )
                )

                if (iconPackMap.isNotEmpty()) {
                    iconPackMap[settings.iconPack]?.also { pack ->
                        shortcut =
                            shortcut.copy(
                                icon = shortcut.icon.copy(
                                    themed = pack.loadIcon(info)?.toBitmap()
                                )
                            )
                    }
                } else {
                    if (iconDrawable is AdaptiveIconDrawable) {
                        try {
                            shortcut = shortcut.copy(
                                icon = shortcut.icon.copy(
                                    adaptive = AppShortcut.Icon.Adaptive(
                                        background = iconDrawable.background.toBitmap(),
                                        foreground = iconDrawable.foreground.toBitmap()
                                    )
                                )
                            )

                        } catch (e: Exception) {
                            println(e)
                        }
                    }
                }

                newAppShortcuts.add(shortcut)
            }

            newAppShortcuts.sortBy { it.label.lowercase() }

            _apps.update { newAppShortcuts }
        }
    }

    fun openApp(packageName: String) {
        val intent = packageManager.getLaunchIntentForPackage(packageName)
        intent?.let {
            app.startActivity(it)
        }
    }

    fun getSearchedApps(text: String): List<AppShortcut> {
        return apps.value.filter { it.label.lowercase().contains(text.lowercase()) }
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