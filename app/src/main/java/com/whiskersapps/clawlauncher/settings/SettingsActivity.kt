package com.whiskersapps.clawlauncher.settings

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.whiskersapps.clawlauncher.shared.model.Routes
import com.whiskersapps.clawlauncher.shared.view.theme.ClawLauncherTheme
import com.whiskersapps.clawlauncher.settings.about.AboutScreenRoot
import com.whiskersapps.clawlauncher.settings.apps.AppsSettingsScreenRoot
import com.whiskersapps.clawlauncher.views.main.views.settings.views.bookmarks.view.BookmarksScreenRoot
import com.whiskersapps.clawlauncher.views.main.views.settings.views.home.view.HomeSettingsScreenRoot
import com.whiskersapps.clawlauncher.views.main.views.settings.views.search_engines.view.SearchEnginesScreenRoot
import com.whiskersapps.clawlauncher.views.main.views.settings.views.security.view.SecuritySettingsScreenRoot
import com.whiskersapps.clawlauncher.settings.lock.LockScreenSettingsScreenRoot
import com.whiskersapps.clawlauncher.settings.style.StyleSettingsScreenRoot
import org.koin.androidx.viewmodel.ext.android.getViewModel

class SettingsActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            val settingsActivityVM = getViewModel<SettingsActivityVM>()
            val settings = settingsActivityVM.settings.collectAsState().value

            if (settings != null) {
                ClawLauncherTheme(
                    settings = settings
                ) {
                    Column(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxSize()
                            .systemBarsPadding()
                    ) {
                        val navController = rememberNavController()

                        NavHost(
                            navController = navController,
                            startDestination = Routes.Settings.MAIN
                        ) {
                            composable(Routes.Settings.MAIN) {
                                SettingsScreenRoot(navController)
                            }

                            composable(Routes.Settings.STYLE) {
                                StyleSettingsScreenRoot(navController)
                            }

                            composable(Routes.Settings.HOME) {
                                HomeSettingsScreenRoot(navController)
                            }

                            composable(Routes.Settings.APPS) {
                                AppsSettingsScreenRoot(navController)
                            }

                            composable(Routes.Settings.BOOKMARKS) {
                                BookmarksScreenRoot(navController)
                            }

                            composable(Routes.Settings.SEARCH_ENGINES) {
                                SearchEnginesScreenRoot(navController)
                            }

                            composable(Routes.Settings.SECURITY) {
                                SecuritySettingsScreenRoot(navController)
                            }

                            composable(
                                "${Routes.Settings.LOCK}/{home}",
                                arguments = listOf(navArgument("home") {
                                    type = NavType.BoolType
                                })
                            ) { backstack ->
                                val goHome = backstack.arguments?.getBoolean("home") == true
                                LockScreenSettingsScreenRoot(
                                    navController = navController,
                                    goHome = goHome
                                )
                            }

                            composable(Routes.Settings.ABOUT) {
                                AboutScreenRoot(navController)
                            }
                        }
                    }
                }
            }
        }
    }
}