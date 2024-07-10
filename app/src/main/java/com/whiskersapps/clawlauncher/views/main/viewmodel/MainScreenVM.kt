package com.whiskersapps.clawlauncher.views.main.viewmodel

import android.app.Application
import android.app.WallpaperManager
import android.graphics.Bitmap
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer
import java.security.MessageDigest
import java.util.Base64
import javax.inject.Inject


data class MainScreenUiState(
    val wallpaper: Bitmap?
)

@HiltViewModel
class MainScreenVM @Inject constructor(
    private val app: Application
) : ViewModel() {

    private val _uiState = MutableStateFlow<MainScreenUiState?>(null)
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update {
            MainScreenUiState(
                wallpaper = WallpaperManager.getInstance(app).drawable?.toBitmap()
            )
        }

        viewModelScope.launch(Dispatchers.IO) {

            var currentHash = hashBitmap(WallpaperManager.getInstance(app).drawable?.toBitmap())

            while (true) {
                val newHash = hashBitmap(WallpaperManager.getInstance(app).drawable?.toBitmap())

                if (newHash != currentHash) {
                    currentHash = newHash

                    _uiState.update {
                        it?.copy(wallpaper = WallpaperManager.getInstance(app).drawable?.toBitmap())
                    }
                }

                delay(5000)
            }
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun hashBitmap(bitmap: Bitmap?): String {
        if (bitmap == null)
            return ""

        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream)

        return MessageDigest.getInstance("SHA-256")
            .digest(stream.toByteArray())
            .toHexString()
    }
}