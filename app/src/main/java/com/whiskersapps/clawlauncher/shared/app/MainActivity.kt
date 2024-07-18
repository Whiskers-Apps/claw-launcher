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
import com.whiskersapps.clawlauncher.views.main.views.settings.view.views.about.view.AboutScreenRoot
import com.whiskersapps.clawlauncher.views.main.views.settings.view.views.bookmarks.view.BookmarksScreenRoot
import com.whiskersapps.clawlauncher.views.main.views.settings.view.views.search_engines.view.SearchEnginesScreenRoot
import com.whiskersapps.clawlauncher.views.setup.layout.ui.LayoutScreen
import com.whiskersapps.clawlauncher.views.setup.layout.ui.LayoutScreenRoot
import com.whiskersapps.clawlauncher.views.setup.welcome.view.WelcomeScreenRoot
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            val settingsScreenVM = hiltViewModel<SettingsScreenVM>()
            val settingsUiState = settingsScreenVM.uiState.collectAsState().value

            if (!settingsUiState.loading) {
                ClawLauncherTheme(settings = settingsUiState.settings) {

                    val navController = rememberNavController()
                    val bgColor = MaterialTheme.colorScheme.background
                    var backgroundColor by remember { mutableStateOf(Color.Transparent) }

                    navController.addOnDestinationChangedListener { _, destination, _ ->

                        backgroundColor = if (destination.route == Routes.Main.HOME) {
                            Color.Transparent
                        } else {
                            bgColor
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(backgroundColor)
                    ) {
                        NavHost(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(backgroundColor),
                            navController = navController,
                            startDestination = if (settingsUiState.settings.setupCompleted) Routes.Main.ROUTE else Routes.Setup.ROUTE
                        ) {
                            navigation(
                                startDestination = Routes.Setup.WELCOME,
                                route = Routes.Setup.ROUTE
                            ) {
                                composable(Routes.Setup.WELCOME) {
                                    WelcomeScreenRoot(navController = navController)
                                }

                                composable(Routes.Setup.LAYOUT) {
                                    LayoutScreenRoot(navController = navController)
                                }
                            }

                            navigation(
                                startDestination = Routes.Main.HOME,
                                route = Routes.Main.ROUTE
                            ) {
                                composable(Routes.Main.HOME) {
                                    MainScreenRoot(navController = navController)
                                }
                            }

                            navigation(
                                startDestination = Routes.Main.Settings.MAIN,
                                route = Routes.Main.Settings.ROUTE
                            ) {
                                composable(Routes.Main.Settings.MAIN) {
                                    SettingsScreenRoot(navController = navController)
                                }

                                composable(Routes.Main.Settings.STYLE) {

                                }

                                composable(Routes.Main.Settings.HOME) {

                                }

                                composable(Routes.Main.Settings.APPS) {

                                }

                                composable(Routes.Main.Settings.SEARCH) {

                                }

                                composable(Routes.Main.Settings.BOOKMARKS) {
                                    BookmarksScreenRoot(navController = navController)
                                }

                                composable(Routes.Main.Settings.SEARCH_ENGINES) {
                                    SearchEnginesScreenRoot(navController = navController)
                                }

                                composable(Routes.Main.Settings.ABOUT) {
                                    AboutScreenRoot(navController = navController)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}