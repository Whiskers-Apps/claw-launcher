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
import com.whiskersapps.clawlauncher.views.main.view.MainScreen
import com.whiskersapps.clawlauncher.views.setup.layout.ui.LayoutScreen
import com.whiskersapps.clawlauncher.views.setup.permissions.ui.PermissionsScreen
import com.whiskersapps.clawlauncher.views.setup.permissions.ui.isAtLeastAndroid13
import com.whiskersapps.clawlauncher.views.setup.welcome.ui.WelcomeScreen
import com.whiskersapps.clawlauncher.shared.model.Routes
import com.whiskersapps.clawlauncher.shared.view.theme.ClawLauncherTheme
import com.whiskersapps.clawlauncher.views.main.views.settings.viewmodel.SettingsScreenVM
import com.whiskersapps.clawlauncher.views.main.views.settings.view.SettingsScreen
import com.whiskersapps.clawlauncher.views.main.views.settings.views.about.view.AboutScreen
import com.whiskersapps.clawlauncher.views.main.views.settings.views.about.view.AboutScreenRoot
import com.whiskersapps.clawlauncher.views.main.views.settings.views.bookmarks.view.BookmarksScreen
import com.whiskersapps.clawlauncher.views.main.views.settings.views.bookmarks.view.BookmarksScreenRoot
import com.whiskersapps.clawlauncher.views.main.views.settings.views.search_engines.view.SearchEnginesScreen
import com.whiskersapps.clawlauncher.views.main.views.settings.views.search_engines.view.SearchEnginesScreenRoot
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
                                    WelcomeScreen {
                                        navController.navigate(Routes.Setup.PERMISSIONS)
                                    }
                                }

                                composable(Routes.Setup.PERMISSIONS) {
                                    PermissionsScreen(
                                        navigateBack = { navController.navigateUp() },
                                        navigateToLayout = { navController.navigate(Routes.Setup.LAYOUT) },
                                        isAtLeastAndroid13 = isAtLeastAndroid13()
                                    )
                                }

                                composable(Routes.Setup.LAYOUT) {
                                    LayoutScreen(
                                        navigateBack = { navController.navigateUp() },
                                        navigateToHome = {
                                            navController.navigate(Routes.Main.ROUTE) {
                                                popUpTo(Routes.Main.ROUTE) {
                                                    inclusive = true
                                                }
                                            }
                                        }
                                    )
                                }
                            }

                            navigation(
                                startDestination = Routes.Main.HOME,
                                route = Routes.Main.ROUTE
                            ) {
                                composable(Routes.Main.HOME) {
                                    MainScreen(
                                        navigateToSettings = { navController.navigate(Routes.Main.Settings.ROUTE) }
                                    )
                                }
                            }

                            navigation(
                                startDestination = Routes.Main.Settings.MAIN,
                                route = Routes.Main.Settings.ROUTE
                            ) {
                                composable(Routes.Main.Settings.MAIN) {
                                    SettingsScreen(
                                        navigateBack = { navController.navigateUp() },
                                        navigateToStyleSettings = {
                                            navController.navigate(Routes.Main.Settings.STYLE)
                                        },
                                        navigateToHomeSettings = {
                                            navController.navigate(Routes.Main.Settings.HOME)
                                        },
                                        navigateToAppsSettings = {
                                            navController.navigate(Routes.Main.Settings.APPS)
                                        },
                                        navigateToSearchSettings = {
                                            navController.navigate(Routes.Main.Settings.SEARCH)
                                        },
                                        navigateToBookmarksSettings = {
                                            navController.navigate(Routes.Main.Settings.BOOKMARKS)
                                        },
                                        navigateToSearchEnginesSettings = {
                                            navController.navigate(Routes.Main.Settings.SEARCH_ENGINES)
                                        },
                                        navigateToAboutSettings = {
                                            navController.navigate(Routes.Main.Settings.ABOUT)
                                        }
                                    )
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