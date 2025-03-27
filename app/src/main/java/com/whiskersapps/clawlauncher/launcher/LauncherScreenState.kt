package com.whiskersapps.clawlauncher.launcher

import com.whiskersapps.clawlauncher.shared.model.Settings

sealed class LauncherScreenState {
    data object Loading : LauncherScreenState()

    data class Loaded(
        val settings: Settings,
    ) : LauncherScreenState()
}
