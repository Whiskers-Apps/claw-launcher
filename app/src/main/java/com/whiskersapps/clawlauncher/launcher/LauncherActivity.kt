package com.whiskersapps.clawlauncher.launcher

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import com.whiskersapps.clawlauncher.launcher.foldable.FoldableRepo
import com.whiskersapps.clawlauncher.settings.di.SettingsRepo
import com.whiskersapps.clawlauncher.shared.view.theme.ClawLauncherTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.getViewModel
import androidx.core.graphics.toColorInt


class LauncherActivity : FragmentActivity() {
    private val settingsRepo = get<SettingsRepo>()
    private val foldableRepo = get<FoldableRepo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch(Dispatchers.IO) {
            settingsRepo.settings.collect {
                lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    WindowInfoTracker.getOrCreate(this@LauncherActivity)
                        .windowLayoutInfo(this@LauncherActivity)
                        .collect { layoutInfo ->
                            val foldingFeature =
                                layoutInfo.displayFeatures.filterIsInstance<FoldingFeature>()
                                    .firstOrNull()

                            foldableRepo.setIsFoldable(foldingFeature)
                        }
                }
            }
        }

        setContent {
            val vm = getViewModel<LauncherActivityVM>()
            val settings = vm.settings.collectAsState().value


            if (settings != null) {
                ClawLauncherTheme(settings) {

                    enableEdgeToEdge()

                    LauncherScreenRoot()
                }
            }
        }
    }
}