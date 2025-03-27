package com.whiskersapps.clawlauncher.launcher

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import com.whiskersapps.clawlauncher.foldable.FoldableRepo
import com.whiskersapps.clawlauncher.settings.SettingsRepo
import com.whiskersapps.clawlauncher.shared.view.theme.ClawLauncherTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LauncherActivity : FragmentActivity() {
    @Inject
    lateinit var settingsRepo: SettingsRepo

    @Inject
    lateinit var foldableRepo: FoldableRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

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
            val vm = hiltViewModel<LauncherVM>()
            val settings = vm.settings.collectAsState().value

            if (settings != null) {
                ClawLauncherTheme(settings) {
                    LauncherScreenRoot()
                }
            }
        }
    }
}