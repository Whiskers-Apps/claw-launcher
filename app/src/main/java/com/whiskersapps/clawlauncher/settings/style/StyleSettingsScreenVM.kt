package com.whiskersapps.clawlauncher.settings.style

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whiskersapps.clawlauncher.R
import com.whiskersapps.clawlauncher.icon_packs.IconPacksRepo
import com.whiskersapps.clawlauncher.launcher.apps.di.AppsRepo
import com.whiskersapps.clawlauncher.settings.di.SettingsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StyleSettingsScreenVM(
    private val app: Application,
    private val settingsRepo: SettingsRepo,
    private val appsRepo: AppsRepo,
    private val iconPacksRepo: IconPacksRepo
) : ViewModel() {

    private val _state = MutableStateFlow(StyleSettingsScreenState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepo.settings.collect { settings ->
                _state.update {
                    val iconPacks = iconPacksRepo.getIconPacks()

                    val iconPack = if (settings.iconPack.isNotEmpty()) {
                        iconPacks.firstOrNull { pack -> pack.packageName == settings.iconPack }?.name
                            ?: app.getString(R.string.StyleSettings_system)
                    } else {
                        app.getString(R.string.StyleSettings_system)
                    }

                    it.copy(
                        loading = false,
                        loadingSettings = false,
                        darkMode = settings.darkMode,
                        theme = settings.theme,
                        darkTheme = settings.darkTheme,
                        iconPack = iconPack,
                        iconPacks = iconPacks
                    )
                }
            }
        }
    }

    fun onAction(intent: StyleSettingsScreenIntent) {
        when (intent) {
            StyleSettingsScreenIntent.BackClicked -> {}

            StyleSettingsScreenIntent.OpenDarkModeDialog -> setShowDarkModeDialog(true)

            StyleSettingsScreenIntent.CloseDarkModeDialog -> setShowDarkModeDialog(false)

            is StyleSettingsScreenIntent.SetDarkMode -> setDarkMode(intent.darkMode)

            StyleSettingsScreenIntent.CloseThemeDialog -> closeThemeDialog()

            StyleSettingsScreenIntent.OpenThemeDialog -> openThemeDialog()

            StyleSettingsScreenIntent.CloseDarkThemeDialog -> closeDarkThemeDialog()

            StyleSettingsScreenIntent.OpenDarkThemeDialog -> openDarkThemeDialog()

            is StyleSettingsScreenIntent.SelectThemeDialogTab -> selectThemeDialogTab(intent.index)

            is StyleSettingsScreenIntent.SetTheme -> setTheme(intent.themeId)

            is StyleSettingsScreenIntent.SetDarkTheme -> setDarkTheme(intent.themeId)

            StyleSettingsScreenIntent.IconPackClicked -> {
                openIconPackDialog()
            }

            StyleSettingsScreenIntent.IconPackDialogClosed -> {
                closeIconPackDialog()
            }

            is StyleSettingsScreenIntent.IconPackSelected -> {
                closeIconPackDialog()
                selectIconPack(intent.iconPack)
            }
        }
    }

    private fun setDarkMode(darkMode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepo.setDarkMode(darkMode)
            setShowDarkModeDialog(false)
        }
    }

    private fun setShowDarkModeDialog(show: Boolean) {
        _state.update { it.copy(showDarkModeDialog = show) }
    }

    private fun openThemeDialog() {
        _state.update { it.copy(showThemeDialog = true) }
    }

    private fun closeThemeDialog() {
        _state.update { it.copy(showThemeDialog = false) }
    }

    private fun openDarkThemeDialog() {
        _state.update { it.copy(showDarkThemeDialog = true) }
    }

    private fun closeDarkThemeDialog() {
        _state.update { it.copy(showDarkThemeDialog = false) }
    }

    private fun selectThemeDialogTab(index: Int) {
        _state.update { it.copy(themeDialogTabIndex = index) }
    }

    private fun setTheme(theme: String) {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepo.setTheme(theme)
        }
    }

    private fun setDarkTheme(theme: String) {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepo.setDarkTheme(theme)
        }
    }

    private fun openIconPackDialog() {
        _state.update {
            it.copy(showIconPackDialog = true)
        }
    }

    private fun closeIconPackDialog() {
        _state.update {
            it.copy(showIconPackDialog = false)
        }
    }

    private fun selectIconPack(iconPack: String) {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepo.setIconPack(iconPack)

            appsRepo.fetchApps()
        }
    }
}