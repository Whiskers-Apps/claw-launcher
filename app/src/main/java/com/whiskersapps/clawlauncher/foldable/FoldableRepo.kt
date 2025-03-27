package com.whiskersapps.clawlauncher.foldable

import androidx.window.layout.FoldingFeature
import com.whiskersapps.clawlauncher.settings.SettingsRepo
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.DEFAULT_LANDSCAPE_COLS
import com.whiskersapps.clawlauncher.shared.model.Settings.Companion.DEFAULT_PORTRAIT_COLS
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FoldableRepo(
    private val settingsRepo: SettingsRepo
) {
    companion object {
        data class Grid(
            val portrait: Int = DEFAULT_PORTRAIT_COLS,
            val landscape: Int = DEFAULT_LANDSCAPE_COLS
        )
    }

    private val _grid = MutableStateFlow(Grid())
    val grid = _grid.asStateFlow()

    private var isFoldable = false

    init {
        CoroutineScope(Dispatchers.IO).launch {
            settingsRepo.settingsFlow.collect { updatedSettings ->
                setGrid()
            }
        }
    }

    private fun setGrid() {
        val settings = settingsRepo.settings.value

        _grid.update {
            Grid(
                portrait = if (isFoldable) settings.unfoldedPortraitCols else settings.portraitCols,
                landscape = if (isFoldable) settings.unfoldedLandscapeCols else settings.landscapeCols
            )
        }
    }

    fun setIsFoldable(foldingFeature: FoldingFeature?) {
        isFoldable = foldingFeature?.state == FoldingFeature.State.FLAT
                || foldingFeature?.state == FoldingFeature.State.HALF_OPENED

        setGrid()
    }
}