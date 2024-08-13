package com.whiskersapps.clawlauncher.shared.view.theme

import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.whiskersapps.clawlauncher.shared.model.Settings

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

@Composable
fun ClawLauncherTheme(
    settings: Settings,
    content: @Composable () -> Unit
) {



    val context = LocalContext.current
    val supportsMonet = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    val darkMode = when(settings.darkMode){
        "light" -> AppCompatDelegate.MODE_NIGHT_NO
        "dark" -> AppCompatDelegate.MODE_NIGHT_YES
        else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    }

    AppCompatDelegate.setDefaultNightMode(darkMode)

    val useDarkTheme = settings.darkMode == "system" && isSystemInDarkTheme() || settings.darkMode == "dark"
    val useLightTheme = settings.darkMode == "system" && !isSystemInDarkTheme() || settings.darkMode == "light"


    val scheme = when {
        useDarkTheme && supportsMonet -> dynamicDarkColorScheme(context)
        useLightTheme && supportsMonet -> dynamicLightColorScheme(context)
        useDarkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = scheme,
        typography = Typography,
        content = content
    )
}