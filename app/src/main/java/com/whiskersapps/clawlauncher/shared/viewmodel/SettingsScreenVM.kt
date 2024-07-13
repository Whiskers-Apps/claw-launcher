package com.whiskersapps.clawlauncher.shared.viewmodel

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whiskersapps.clawlauncher.shared.data.SettingsRepository
import com.whiskersapps.clawlauncher.shared.model.Settings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsScreenVM @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val app: Application
) : ViewModel() {
    private val _settings = MutableStateFlow<Settings?>(null)
    val settings = _settings.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.Main) {
            settingsRepository.settingsFlow.collect { newSettings ->
                _settings.update { newSettings }
            }
        }
    }

    fun openRepoInBrowser() {
        try {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://github.com/Whiskers-Apps/claw-launcher")
            )

            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            app.startActivity(intent)
        } catch (e: Exception) {
            println("Error opening repo. $e")
        }
    }
}