package com.whiskersapps.clawlauncher.settings.lock

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whiskersapps.clawlauncher.launcher.lock.ScreenLock
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask

class LockScreenSettingsScreenVM constructor(
    app: Application
) : ViewModel() {
    companion object {
        data class State(
            val accessibilityServiceEnabled: Boolean = false,
        )

        sealed class Action {
            data object OnNavigateBack : Action()
            data object OnOpenAccessibilitySettings : Action()
            data object OnLockScreen : Action()
        }
    }

    private val screenLock = ScreenLock(app)

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    private val timer = Timer()

    init {
        viewModelScope.launch {
            timer.schedule(object : TimerTask() {
                override fun run() {
                    _state.update {
                        it.copy(
                            accessibilityServiceEnabled = screenLock.isServiceEnabled()
                        )
                    }
                }
            }, 0, 5000)
        }
    }

    fun onAction(action: Action) {
        when (action) {
            Action.OnNavigateBack -> {}
            Action.OnOpenAccessibilitySettings -> {
                openAccessibilitySettings()
            }

            Action.OnLockScreen -> {
                lockScreen()
            }
        }
    }

    private fun openAccessibilitySettings() {
        screenLock.openAccessibilitySettings()
    }

    private fun lockScreen() {
        screenLock.lockScreen()
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }
}