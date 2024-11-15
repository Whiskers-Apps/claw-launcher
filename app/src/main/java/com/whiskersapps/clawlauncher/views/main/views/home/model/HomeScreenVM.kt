package com.whiskersapps.clawlauncher.views.main.views.home.model

import android.accessibilityservice.AccessibilityServiceInfo
import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.util.Log
import android.view.accessibility.AccessibilityManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whiskersapps.clawlauncher.features.lock_screen.ScreenLockService
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
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.settings.collect { settings ->
                _state.update {
                    it.copy(
                        loading = false,
                        showSearchBar = settings.showHomeSearchBar,
                        showPlaceholder = settings.showHomeSearchBarPlaceholder,
                        showSearchBarSettings = settings.showHomeSearchBarSettings,
                        searchBarRadius = settings.homeSearchBarRadius.toFloat(),
                        swipeUpToSearch = settings.swipeUpToSearch,
                        tintClock = settings.tintClock
                    )
                }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
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

            is HomeScreenAction.SetSearchBarRadius -> setSearchBarRadius(
                action.radius.toInt().toFloat()
            )

            is HomeScreenAction.SaveSearchBarRadius -> saveSearchBarRadius(action.radius.toInt())

            is HomeScreenAction.SetShowSearchBar -> setShowSearchBar(action.show)

            is HomeScreenAction.SetShowSearchBarPlaceholder -> setShowPlaceholder(action.show)

            is HomeScreenAction.SetShowSettings -> setShowSearchBarSettings(action.show)

            is HomeScreenAction.SetSwipeUpToSearch -> setSwipeUpToSearch(action.swipeUp)

            HomeScreenAction.OnOpenCalendar -> onOpenCalendar()

            is HomeScreenAction.SetTintIcon -> setTintIcon(action.tint)

            HomeScreenAction.OnLockScreen -> lockScreen()

            HomeScreenAction.OnCloseLockAccessibilityDialog -> closeLockAccessibilityDialog()

            HomeScreenAction.OnOpenAccessibilitySettings -> openAccessibilitySettings()
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

    private fun setShowSearchBar(show: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.setShowHomeSearchBar(show)
        }
    }

    private fun setSearchBarRadius(radius: Float) {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.setHomeSearchBarRadius(radius.toInt())
        }
    }

    private fun setShowSearchBarSettings(show: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.setShowHomeSearchBarSettings(show)
        }
    }

    private fun saveSearchBarRadius(radius: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.setHomeSearchBarRadius(radius)
        }
    }

    private fun setShowPlaceholder(show: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.setShowHomeSearchBarPlaceholder(show)
        }
    }

    private fun setSwipeUpToSearch(swipeUp: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.setSwipeUpToSearch(swipeUp)
        }
    }

    private fun onOpenCalendar() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val intent = Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_APP_CALENDAR)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

                if (intent.resolveActivity(app.packageManager) != null) {
                    app.startActivity(intent)
                }
            }
        } catch (e: Exception) {
            println("Error opening calendar. Exception: ${e.message}")
        }
    }

    private fun setTintIcon(tint: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.setTintClock(tint)
        }
    }

    private fun lockScreen() {
        if (!isLockScreenServiceEnabled()) {
            _state.update {
                it.copy(showLockAccessibilityDialog = true)
            }
            return
        }

        val intent = Intent("${app.packageName}.LOCK", null, app, ScreenLockService::class.java)
        app.startService(intent)
    }

    private fun openAccessibilitySettings(){

        closeLockAccessibilityDialog()

        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        app.startActivity(intent)
    }

    @SuppressLint("ServiceCast")
    private fun isLockScreenServiceEnabled(): Boolean {
        val accessibilityManager =
            app.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager

        return accessibilityManager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK)
            .any { service ->
                service.resolveInfo.serviceInfo.packageName == app.packageName
            }
    }

    private fun closeLockAccessibilityDialog() {
        _state.update {
            it.copy(showLockAccessibilityDialog = false)
        }
    }
}