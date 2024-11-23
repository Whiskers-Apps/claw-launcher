package com.whiskersapps.clawlauncher.shared.app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import com.whiskersapps.clawlauncher.shared.data.SettingsRepository
import com.whiskersapps.clawlauncher.shared.model.Routes
import com.whiskersapps.clawlauncher.shared.view.theme.ClawLauncherTheme
import com.whiskersapps.clawlauncher.views.main.view.MainScreenRoot
import com.whiskersapps.clawlauncher.views.main.views.settings.view.SettingsScreenRoot
import com.whiskersapps.clawlauncher.views.main.views.settings.views.about.view.AboutScreenRoot
import com.whiskersapps.clawlauncher.views.main.views.settings.views.apps.view.AppsSettingsScreenRoot
import com.whiskersapps.clawlauncher.views.main.views.settings.views.bookmarks.view.BookmarksScreenRoot
import com.whiskersapps.clawlauncher.views.main.views.settings.views.home.view.HomeSettingsScreenRoot
import com.whiskersapps.clawlauncher.views.main.views.settings.views.search_engines.view.SearchEnginesScreenRoot
import com.whiskersapps.clawlauncher.views.main.views.settings.views.security.view.SecuritySettingsScreenRoot
import com.whiskersapps.clawlauncher.views.main.views.settings.views.settings.LockScreenSettingsScreenRoot
import com.whiskersapps.clawlauncher.views.main.views.settings.views.style.view.StyleSettingsScreenRoot
import com.whiskersapps.clawlauncher.views.setup.search_engines.view.SearchEnginesSetupScreenRoot
import com.whiskersapps.clawlauncher.views.setup.welcome.view.WelcomeScreenRoot
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    @Inject
    lateinit var settingsRepository: SettingsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val app = this.application

        lifecycleScope.launch(Dispatchers.IO) {
            settingsRepository.settings.collect {
                lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    WindowInfoTracker.getOrCreate(this@MainActivity)
                        .windowLayoutInfo(this@MainActivity)
                        .collect { layoutInfo ->
                            val feature =
                                layoutInfo.displayFeatures.filterIsInstance<FoldingFeature>()
                                    .firstOrNull()

                            settingsRepository.setGridColsCount(feature)
                        }
                }
            }
        }

        setContent {

            val mainVM: MainVM = hiltViewModel()
            val settings = mainVM.settings.collectAsState().value

            if (settings != null) {
                ClawLauncherTheme(
                    settings = settings
                ) {
                    val mainNavController = rememberNavController()
                    val backgroundColor = MaterialTheme.colorScheme.background
                    var navBackgroundColor by remember { mutableStateOf(if (settings.setupCompleted) Color.Transparent else backgroundColor) }

                    mainNavController.addOnDestinationChangedListener { _, destination, _ ->

                        navBackgroundColor = if (destination.route == Routes.Main.HOME) {
                            Color.Transparent
                        } else {
                            backgroundColor
                        }
                    }

                    NavHost(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(navBackgroundColor),
                        navController = mainNavController,
                        startDestination = if (settings.setupCompleted) Routes.Main.ROUTE else Routes.Setup.ROUTE
                    ) {
                        navigation(
                            startDestination = Routes.Setup.WELCOME,
                            route = Routes.Setup.ROUTE
                        ) {
                            composable(Routes.Setup.WELCOME) {
                                WelcomeScreenRoot(navController = mainNavController)
                            }

                            composable(Routes.Setup.SEARCH_ENGINES) {
                                SearchEnginesSetupScreenRoot(navController = mainNavController)
                            }
                        }

                        navigation(
                            startDestination = Routes.Main.HOME,
                            route = Routes.Main.ROUTE
                        ) {
                            composable(Routes.Main.HOME) {
                                MainScreenRoot(navController = mainNavController)
                            }
                        }

                        navigation(
                            startDestination = Routes.Main.Settings.MAIN,
                            route = Routes.Main.Settings.ROUTE
                        ) {
                            composable(Routes.Main.Settings.MAIN) {
                                SettingsScreenRoot(navController = mainNavController)
                            }

                            composable(Routes.Main.Settings.STYLE) {
                                StyleSettingsScreenRoot(navController = mainNavController)
                            }

                            composable(Routes.Main.Settings.HOME) {
                                HomeSettingsScreenRoot(navController = mainNavController)
                            }

                            composable(Routes.Main.Settings.APPS) {
                                AppsSettingsScreenRoot(navController = mainNavController)
                            }

                            composable(Routes.Main.Settings.BOOKMARKS) {
                                BookmarksScreenRoot(navController = mainNavController)
                            }

                            composable(Routes.Main.Settings.SEARCH_ENGINES) {
                                SearchEnginesScreenRoot(navController = mainNavController)
                            }

                            composable(Routes.Main.Settings.SECURITY) {
                                SecuritySettingsScreenRoot(navController = mainNavController)
                            }

                            composable(
                                "${Routes.Main.Settings.LOCK}/{home}",
                                arguments = listOf(navArgument("home") {
                                    type = NavType.BoolType
                                })
                            ) { backstack ->

                                val goHome = backstack.arguments?.getBoolean("home") ?: false

                                LockScreenSettingsScreenRoot(navController = mainNavController, goHome = goHome)
                            }

                            composable(Routes.Main.Settings.ABOUT) {
                                AboutScreenRoot(navController = mainNavController)
                            }
                        }
                    }
                }
            }
        }
    }
}