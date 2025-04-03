package com.whiskersapps.clawlauncher.launcher.home

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whiskersapps.clawlauncher.launcher.lock.ScreenLock
import com.whiskersapps.clawlauncher.settings.SettingsActivity
import com.whiskersapps.clawlauncher.settings.di.SettingsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HomeScreenVM(
    private val settingsRepo: SettingsRepo,
    private val app: Application
) : ViewModel() {

    private val _state = MutableStateFlow(HomeScreenState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.Main) {
            settingsRepo.settings.collect { settings ->
                _state.update {
                    it.copy(
                        loading = false,
                        clockPlacement = settings.clockPlacement,
                        enableSwipeUp = settings.swipeUpToSearch,
                        showSearchBar = settings.showHomeSearchBar,
                        showPlaceholder = settings.showHomeSearchBarPlaceholder,
                        searchBarRadius = settings.homeSearchBarRadius.toFloat(),
                        tintClock = settings.tintClock,
                        pillShapeClock = settings.pillShapeClock
                    )
                }
            }
        }

        viewModelScope.launch(Dispatchers.Main) {
            while (true) {
                val calendar = Calendar.getInstance()
                val locale = Locale.getDefault()
                val formatter = SimpleDateFormat("EEEE - dd/MM/yyyy", locale)
                val hours = String.format(locale, "%02d", calendar.get(Calendar.HOUR_OF_DAY))
                val minutes = String.format(locale, "%02d", calendar.get(Calendar.MINUTE))
                val clockText = "$hours:$minutes"
                val dateText = formatter.format(calendar.time)

                _state.update {
                    it.copy(
                        clock = clockText,
                        date = dateText
                    )
                }

                delay(1000)
            }
        }
    }

    fun onAction(homeScreenAction: HomeScreenAction) {
        when (homeScreenAction) {
            HomeScreenAction.OnChangeWallpaper -> {
                openWallpaperSetter()
            }

            HomeScreenAction.OnOpenSettings -> {
                openSettings()
            }

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


            HomeScreenAction.OnOpenCalendar -> onOpenCalendar()

            HomeScreenAction.OnLockScreen -> lockScreen()


            HomeScreenAction.OnOpenLockSettings -> {}

            HomeScreenAction.ResetOpenLockSettings -> {
            }

            HomeScreenAction.ShowLockScreenDialog -> {
                setShowLockScreenDialog(true)
            }

            HomeScreenAction.CloseLockScreenDialog -> {
                setShowLockScreenDialog(false)
            }
        }
    }

    private fun openWallpaperSetter() {
        setShowMenuDialog(false)

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

    private fun setShowMenuDialog(show: Boolean) {
        _state.update { it.copy(showMenuDialog = show) }
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


    private fun lockScreen() {
        val screenLock = ScreenLock(app)

        if (!screenLock.isServiceEnabled()) {
            setShowLockScreenDialog(true)
            return
        }

        screenLock.lockScreen()
    }


    private fun setShowLockScreenDialog(show: Boolean) {
        viewModelScope.launch {
            _state.update {
                it.copy(showLockScreenDialog = show)
            }
        }
    }

    private fun openSettings() {
        setShowMenuDialog(false)

        val intent = Intent(app, SettingsActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        app.startActivity(intent)
    }
}