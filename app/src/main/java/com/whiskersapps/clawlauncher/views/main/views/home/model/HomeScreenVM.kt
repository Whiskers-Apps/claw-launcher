package com.whiskersapps.clawlauncher.views.main.views.home.model

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whiskersapps.clawlauncher.shared.data.SettingsRepository
import com.whiskersapps.clawlauncher.views.main.views.home.intent.HomeScreenAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeScreenVM @Inject constructor(
    val settingsRepository: SettingsRepository,
    val app: Application
) : ViewModel() {

    private val _state = MutableStateFlow(HomeScreenState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.Main) {
            settingsRepository.settingsFlow.collect { settings ->
                if (state.value.loading) {
                    _state.update {
                        HomeScreenState(
                            loading = false,
                            settings = settings,
                        )
                    }
                } else {
                    _state.update {
                        it.copy(settings = settings)
                    }
                }
            }
        }

        viewModelScope.launch {
            while (true) {

                val calendar = Calendar.getInstance()
                val locale = Locale.getDefault()
                val formatter = SimpleDateFormat("EEEE - dd/MM/yyyy", locale)
                val hours = String.format(locale, "%02d", calendar.get(Calendar.HOUR_OF_DAY))
                val minutes = String.format(locale, "%02d", calendar.get(Calendar.MINUTE))

                _state.update {
                    it.copy(
                        clock = "$hours:$minutes",
                        date = formatter.format(calendar.time)
                    )
                }

                delay(1000)
            }
        }
    }

    fun onAction(action: HomeScreenAction) {
        viewModelScope.launch(Dispatchers.Main) {
            when (action) {
                HomeScreenAction.ChangeWallpaper -> {
                    openWallpaperSetter()
                }

                HomeScreenAction.NavigateToSettings -> {}

                HomeScreenAction.OpenSearchSheet -> {}

                HomeScreenAction.OpenNotificationPanel -> {
                    openNotificationPanel()
                }
                HomeScreenAction.OpenMenuDialog -> {
                    setShowMenuDialog(true)
                }
                HomeScreenAction.CloseMenuDialog -> {
                    setShowMenuDialog(false)
                }
                HomeScreenAction.OpenSettingsDialog -> {
                    setShowSettingsDialog(true)
                }
                HomeScreenAction.CloseSettingsDialog -> {
                    setShowSettingsDialog(false)
                }

                is HomeScreenAction.SetSearchBarRadius -> {
                    settingsRepository.setHomeSearchBarRadius(action.radius.toInt())
                }

                is HomeScreenAction.SetShowSearchBar -> {
                    settingsRepository.setShowHomeSearchBar(action.show)
                }

                is HomeScreenAction.SetShowSearchBarPlaceholder -> {
                    settingsRepository.setShowHomeSearchBarPlaceholder(action.show)
                }

                is HomeScreenAction.SetShowSettings -> {
                    settingsRepository.setShowHomeSearchBarSettings(action.show)
                }
            }
        }
    }

    private fun openWallpaperSetter() {
        try {
            val intent = Intent(Intent.ACTION_SET_WALLPAPER)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            app.startActivity(intent)
        } catch (e: Exception) {
            Log.e("Debug", "Error opening wallpaper selector. $e")
        }
    }

    private fun openNotificationPanel() {
        try {
            Class.forName("android.app.StatusBarManager")
                .getMethod("expandNotificationsPanel")
                .invoke(app.getSystemService("statusbar"))
        } catch (e: Exception) {
            println("Error expanding notifications bar. $e")
        }
    }

    private fun setShowSettingsDialog(show: Boolean) {
        _state.update { it.copy(showSettingsDialog = show) }
    }

    private fun setShowMenuDialog(show: Boolean) {
        _state.update { it.copy(showMenuDialog = show) }
    }
}