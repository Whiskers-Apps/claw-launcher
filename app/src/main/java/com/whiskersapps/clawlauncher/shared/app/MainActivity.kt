package com.whiskersapps.clawlauncher.shared.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.whiskersapps.clawlauncher.shared.model.Routes
import com.whiskersapps.clawlauncher.shared.view.theme.ClawLauncherTheme
import com.whiskersapps.clawlauncher.views.main.view.MainScreenRoot
import com.whiskersapps.clawlauncher.views.main.views.settings.model.SettingsScreenVM
import com.whiskersapps.clawlauncher.views.main.views.settings.view.SettingsScreenRoot
import com.whiskersapps.clawlauncher.views.main.views.settings.views.about.view.AboutScreenRoot
import com.whiskersapps.clawlauncher.views.main.views.settings.views.apps.view.AppsSettingsScreenRoot
import com.whiskersapps.clawlauncher.views.main.views.settings.views.bookmarks.view.BookmarksScreenRoot
import com.whiskersapps.clawlauncher.views.main.views.settings.views.home.view.HomeSettingsScreenRoot
import com.whiskersapps.clawlauncher.views.main.views.settings.views.search_engines.view.SearchEnginesScreenRoot
import com.whiskersapps.clawlauncher.views.main.views.settings.views.security.view.SecuritySettingsScreenRoot
import com.whiskersapps.clawlauncher.views.main.views.settings.views.style.view.StyleSettingsScreenRoot
import com.whiskersapps.clawlauncher.views.setup.search_engines.view.SearchEnginesSetupScreenRoot
import com.whiskersapps.clawlauncher.views.setup.welcome.view.WelcomeScreenRoot
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            val mainVM: MainVM = hiltViewModel()
            val settings = mainVM.settings.collectAsState().value

            if (settings != null) {
                ClawLauncherTheme(settings = settings) {

                    val mainNavController = rememberNavController()

                    NavHost(
                        modifier = Modifier
                            .fillMaxSize(),
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

                            composable(Routes.Main.Settings.SECURITY){
                                SecuritySettingsScreenRoot(navController = mainNavController)
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