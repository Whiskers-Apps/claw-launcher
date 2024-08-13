package com.whiskersapps.clawlauncher.views.setup.welcome.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.whiskersapps.clawlauncher.R
import com.whiskersapps.clawlauncher.shared.model.Routes
import com.whiskersapps.clawlauncher.views.setup.welcome.intent.WelcomeScreenAction
import com.whiskersapps.clawlauncher.views.setup.welcome.model.WelcomeScreenVM

@Composable
fun WelcomeScreenRoot(
    navController: NavController,
    vm: WelcomeScreenVM = hiltViewModel()
) {
    WelcomeScreen(
        onAction = { action ->
            when (action) {
                WelcomeScreenAction.NavigateNext -> navController.navigate(Routes.Setup.SEARCH_ENGINES)
            }
        }
    )
}

@Composable
fun WelcomeScreen(
    onAction: (WelcomeScreenAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f, fill = false),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.size(240.dp),
                painter = painterResource(id = R.drawable.icon_no_padding_svg),
                contentDescription = "app logo",
                tint = MaterialTheme.colorScheme.primary,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(id = R.string.app_name),
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            Button(onClick = { onAction(WelcomeScreenAction.NavigateNext) }) {
                Text(
                    text = stringResource(id = R.string.SetupScreen_next),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}