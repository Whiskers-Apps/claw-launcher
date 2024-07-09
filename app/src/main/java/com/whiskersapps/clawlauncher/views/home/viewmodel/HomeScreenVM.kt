package com.whiskersapps.clawlauncher.views.home.viewmodel

import android.app.Application
import android.app.WallpaperManager
import android.graphics.Bitmap
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModel
import com.whiskersapps.clawlauncher.shared.data.AppsRepository
import com.whiskersapps.clawlauncher.shared.model.AppShortcut
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class HomeScreenUIState(
    val wallpaper: Bitmap?
)

@HiltViewModel
class HomeScreenVM @Inject constructor(
    private val app: Application
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeScreenUIState?>(null)
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update {
            HomeScreenUIState(
                wallpaper = WallpaperManager.getInstance(app).drawable?.toBitmap()
            )
        }
    }

}