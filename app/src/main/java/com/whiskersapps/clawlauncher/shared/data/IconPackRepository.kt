package com.whiskersapps.clawlauncher.shared.data

import android.app.Application
import com.iamverycute.iconpackmanager.IconPackManager
import com.whiskersapps.clawlauncher.shared.model.IconPack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class IconPackRepository(
    private val app: Application,
    private val settingsRepository: SettingsRepository,
    private val appsRepository: AppsRepository
) {
    companion object {
        data class Data(
            val iconPacks: List<IconPack> = emptyList(),
            val currentIconPack: IconPack? = null
        )
    }

    private val _data = MutableStateFlow(Data())
    val data = _data.asStateFlow()

    private fun fetchIconPacks() {
        val iconPackManager = IconPackManager(app)
        val iconPacks = ArrayList<IconPack>()

        iconPackManager.isSupportedIconPacks().forEach { (_, iconPack) ->
            val app = appsRepository.apps.value.find { it.packageName == iconPack.getPackageName() }

            app?.let {
                iconPacks.add(
                    IconPack(
                        packageName = iconPack.getPackageName(),
                        name = app.label,
                        icon = app.icon.stock
                    )
                )
            }
        }

        _data.update { it.copy(iconPacks = iconPacks) }
    }

    init {
        CoroutineScope(Dispatchers.IO).launch {
            appsRepository.apps.collect {
                fetchIconPacks()
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            settingsRepository.settingsFlow.collect { settings ->
                _data.update { it.copy(currentIconPack = it.iconPacks.find { iconPack -> iconPack.packageName == settings.iconPack }) }
            }
        }
    }
}