package com.whiskersapps.clawlauncher.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whiskersapps.clawlauncher.settings.di.SettingsRepo
import com.whiskersapps.clawlauncher.shared.model.Settings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookmarksShareActivityVM(
    private val settingsRepo: SettingsRepo,
) : ViewModel() {
    private val _settings = MutableStateFlow<Settings?>(null)
    val settings = _settings.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.Main) {
            settingsRepo.settingsFlow.collect { updatedSettings ->
                _settings.update { updatedSettings }
            }
        }
    }
}