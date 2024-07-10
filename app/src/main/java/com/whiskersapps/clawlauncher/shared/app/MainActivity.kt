package com.whiskersapps.clawlauncher.shared.app

import android.graphics.drawable.Drawable
import android.graphics.drawable.DrawableContainer
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
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
import com.whiskersapps.clawlauncher.shared.viewmodel.SettingsVM
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

                    Surface{

                        val navController = rememberNavController()

                        NavHost(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.background),
                            navController = navController,
                            startDestination = if (settings.setupCompleted) Routes.Main.ROUTE else Routes.Setup.ROUTE
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
                                    MainScreen()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}