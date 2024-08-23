package com.whiskersapps.clawlauncher.shared.view.theme

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.whiskersapps.clawlauncher.shared.model.Settings
import com.whiskersapps.clawlauncher.shared.utils.isAtLeastAndroid12

@Composable
fun ClawLauncherTheme(
    settings: Settings,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current

    val darkMode = when (settings.darkMode) {
        "light" -> AppCompatDelegate.MODE_NIGHT_NO
        "dark" -> AppCompatDelegate.MODE_NIGHT_YES
        else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    }

    AppCompatDelegate.setDefaultNightMode(darkMode)

    val useMonet = settings.theme == "monet"
    val useDarkMonet = settings.darkTheme == "monet"
    val useDarkTheme = useDarkTheme(darkMode = settings.darkMode)

    MaterialTheme(
        colorScheme = if (useMonet && isAtLeastAndroid12() && !useDarkTheme) {
            dynamicLightColorScheme(context)
        } else if (useDarkMonet && isAtLeastAndroid12() && useDarkTheme) {
            dynamicDarkColorScheme(context)
        } else {
            if (useDarkTheme)
                getDarkColorScheme(id = settings.darkTheme)
            else
                getLightColorScheme(id = settings.theme)
        },
        typography = Typography,
        content = content
    )
}

@Composable
fun PreviewTheme(
    useMonet: Boolean,
    useDarkMonet: Boolean,
    dark: Boolean,
    theme: String,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current

    MaterialTheme(
        colorScheme = if (useMonet && isAtLeastAndroid12() && !dark) {
            dynamicLightColorScheme(context)
        } else if (useDarkMonet && isAtLeastAndroid12() && dark) {
            dynamicDarkColorScheme(context)
        } else {
            if (dark) getDarkColorScheme(id = theme) else getLightColorScheme(id = theme)
        },
        typography = Typography,
        content = content
    )
}


@Composable
fun useDarkTheme(darkMode: String) =
    darkMode == "system" && isSystemInDarkTheme() || darkMode == "dark"

@Composable
fun getDarkColorScheme(id: String): ColorScheme {
    val context = LocalContext.current

    return if (id == "monet" && isAtLeastAndroid12()) {
        dynamicDarkColorScheme(context)
    } else {
        val theme = CUSTOM_THEMES.find { it.id == id }!!

        darkColorScheme(
            background = theme.background,
            surfaceVariant = theme.secondaryBackground,
            onBackground = theme.text,
            onSurfaceVariant = theme.text,
            primary = theme.accent,
            onPrimary = theme.onAccent
        )
    }
}

@Composable
fun getLightColorScheme(id: String): ColorScheme {
    val context = LocalContext.current

    return if (id == "monet" && isAtLeastAndroid12()) {
        dynamicLightColorScheme(context)
    } else {
        val theme = CUSTOM_THEMES.find { it.id == id }!!

        lightColorScheme(
            background = theme.background,
            surfaceVariant = theme.secondaryBackground,
            onBackground = theme.text,
            onSurfaceVariant = theme.text,
            primary = theme.accent,
            onPrimary = theme.onAccent
        )
    }
}