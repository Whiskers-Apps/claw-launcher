package com.lighttigerxiv.clawlauncher.shared.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.lighttigerxiv.clawlauncher.features.home.ui.HomeScreen
import com.lighttigerxiv.clawlauncher.features.setup.screens.layout.ui.LayoutScreen
import com.lighttigerxiv.clawlauncher.features.setup.screens.welcome.ui.WelcomeScreen
import com.lighttigerxiv.clawlauncher.shared.model.Routes
import com.lighttigerxiv.clawlauncher.shared.ui.theme.ClawLauncherTheme
import com.lighttigerxiv.clawlauncher.shared.viewmodel.SettingsVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val settingsVM = hiltViewModel<SettingsVM>()
            val settings = settingsVM.settings.collectAsState().value

            settings?.let {
                ClawLauncherTheme(settings = settings) {

                    Surface(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {

                        val navController = rememberNavController()

                        NavHost(
                            modifier = Modifier
                                .statusBarsPadding()
                                .fillMaxSize(),
                            navController = navController,
                            startDestination = if (settings.setupCompleted) Routes.Main.ROUTE else Routes.Setup.ROUTE
                        ) {
                            navigation(
                                startDestination = Routes.Setup.WELCOME,
                                route = Routes.Setup.ROUTE
                            ) {
                                composable(Routes.Setup.WELCOME) {
                                    WelcomeScreen {
                                        navController.navigate(Routes.Setup.LAYOUT)
                                    }
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
                                    HomeScreen()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}