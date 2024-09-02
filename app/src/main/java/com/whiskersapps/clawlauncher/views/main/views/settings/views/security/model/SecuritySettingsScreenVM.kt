package com.whiskersapps.clawlauncher.views.main.views.settings.views.security.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whiskersapps.clawlauncher.shared.data.AppsRepository
import com.whiskersapps.clawlauncher.shared.data.SettingsRepository
import com.whiskersapps.clawlauncher.views.main.views.settings.views.security.intent.SecuritySettingsScreenAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SecuritySettingsScreenVM @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val appsRepository: AppsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SecuritySettingsScreenState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.settings.collect { settings ->
                _state.update {
                    it.copy(
                        loading = false,
                        settings = settings,
                        apps = appsRepository.apps.value,
                        hiddenAppsDialog = it.hiddenAppsDialog.copy(selectedApps = settings.hiddenApps),
                        secureAppsDialog = it.secureAppsDialog.copy(selectedApps = settings.secureApps)
                    )
                }
            }
        }
    }

    fun onAction(action: SecuritySettingsScreenAction) {
        when (action) {
            SecuritySettingsScreenAction.NavigateBack -> {}
            SecuritySettingsScreenAction.OpenHiddenAppsDialog -> showHiddenAppsDialog()
            SecuritySettingsScreenAction.CloseHiddenAppsDialog -> closeHiddenAppsDialog()
            SecuritySettingsScreenAction.OpenSecureAppsDialog -> showSecureAppsDialog()
            SecuritySettingsScreenAction.CloseSecureAppsDialog -> closeSecureAppsDialog()
            is SecuritySettingsScreenAction.ToggleHiddenApp -> toggleHiddenApp(action.packageName)
            SecuritySettingsScreenAction.SaveHiddenApps -> saveHiddenApps()
        }
    }

    private fun showHiddenAppsDialog() {
        _state.update { it.copy(hiddenAppsDialog = it.hiddenAppsDialog.copy(show = true)) }
    }

    private fun toggleHiddenApp(packageName: String) {
        val newSelectedApps = ArrayList(state.value.hiddenAppsDialog.selectedApps)

        if (newSelectedApps.contains(packageName)) {
            newSelectedApps.removeIf { it == packageName }
        } else {
            newSelectedApps.add(packageName)
        }

        _state.update { it.copy(hiddenAppsDialog = it.hiddenAppsDialog.copy(selectedApps = newSelectedApps)) }
    }

    private fun saveHiddenApps() {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.setHiddenApps(state.value.hiddenAppsDialog.selectedApps)
            closeHiddenAppsDialog()
        }
    }

    private fun closeHiddenAppsDialog() {
        _state.update {
            it.copy(
                hiddenAppsDialog = it.hiddenAppsDialog.copy(
                    show = false,
                    selectedApps = state.value.settings.hiddenApps
                )
            )
        }
    }

    private fun showSecureAppsDialog() {
        _state.update { it.copy(secureAppsDialog = it.secureAppsDialog.copy(show = true)) }
    }

    private fun closeSecureAppsDialog() {
        _state.update { it.copy(secureAppsDialog = it.secureAppsDialog.copy(show = false)) }
    }
}