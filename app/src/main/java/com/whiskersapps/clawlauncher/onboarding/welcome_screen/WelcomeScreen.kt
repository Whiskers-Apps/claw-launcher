package com.whiskersapps.clawlauncher.onboarding.welcome_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.whiskersapps.clawlauncher.R
import com.whiskersapps.clawlauncher.onboarding.composables.OnBoardingButton
import com.whiskersapps.clawlauncher.onboarding.composables.OnBoardingScaffold
import com.whiskersapps.clawlauncher.onboarding.welcome_screen.composables.AppIcon
import com.whiskersapps.clawlauncher.shared.model.Routes

@Composable
fun WelcomeScreenRoot(
    navController: NavController,
) {
    WelcomeScreen {
        when (it) {
            WelcomeScreenAction.NavigateNext -> navController.navigate(Routes.OnBoarding.SEARCH_ENGINES)
        }
    }
}

@Composable
fun WelcomeScreen(
    onAction: (WelcomeScreenAction) -> Unit
) {
    OnBoardingScaffold(
        mainContent = {
            AppIcon()
        },
        navContent = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                OnBoardingButton(stringResource(R.string.SetupScreen_next)) {
                    onAction(WelcomeScreenAction.NavigateNext)
                }
            }
        }
    )
}