package com.whiskersapps.clawlauncher.views.main.views.settings.views.about.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.whiskersapps.clawlauncher.R
import com.whiskersapps.clawlauncher.shared.view.composables.ContentColumn
import com.whiskersapps.clawlauncher.shared.view.composables.NavBar
import com.whiskersapps.clawlauncher.shared.view.composables.sidePadding
import com.whiskersapps.clawlauncher.shared.view.theme.Typography
import com.whiskersapps.clawlauncher.views.main.views.settings.views.about.intent.AboutScreenAction
import com.whiskersapps.clawlauncher.views.main.views.settings.views.about.model.AboutScreenVM
import org.koin.androidx.compose.koinViewModel

@Composable
fun AboutScreenRoot(
    navController: NavController,
    vm: AboutScreenVM = koinViewModel()
) {

    AboutScreen(
        onAction = { action ->
            when (action) {
                AboutScreenAction.NavigateBack -> navController.navigateUp()
                else -> vm.onAction(action)
            }
        }
    )
}

@Composable
fun AboutScreen(
    onAction: (AboutScreenAction) -> Unit
) {

    val context = LocalContext.current
    val versionName = remember {
        context.packageManager.getPackageInfo(context.packageName, 0).versionName
    }

    ContentColumn(
        useSystemBarsPadding = true,
        navigationBar = {
            NavBar(navigateBack = { onAction(AboutScreenAction.NavigateBack) })
        }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.size(140.dp),
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

        Column(modifier = Modifier.sidePadding()) {
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = stringResource(R.string.AboutScreen_version),
                color = MaterialTheme.colorScheme.onBackground,
                style = Typography.titleSmall
            )

            Text(
                text = versionName!!,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = stringResource(R.string.AboutScreen_license),
                color = MaterialTheme.colorScheme.onBackground,
                style = Typography.titleSmall
            )

            Text(
                text = "MIT",
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        Column(
            Modifier
                .fillMaxWidth()
                .clickable { onAction(AboutScreenAction.OpenRepoUrl) }
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.AboutScreen_source_code),
                color = MaterialTheme.colorScheme.onBackground,
                style = Typography.titleSmall
            )

            Text(
                text = "https://github.com/Whiskers-Apps/claw-launcher",
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onAction(AboutScreenAction.OpenDonateLink) }
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.AboutScreen_donate),
                color = MaterialTheme.colorScheme.onBackground,
                style = Typography.titleSmall
            )

            Image(
                modifier = Modifier.width(140.dp),
                painter = painterResource(R.drawable.bmc),
                contentDescription = null
            )
        }

    }
}