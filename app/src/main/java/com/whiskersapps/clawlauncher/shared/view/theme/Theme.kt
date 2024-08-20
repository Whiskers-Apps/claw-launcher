package com.whiskersapps.clawlauncher.shared.view.theme

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.lighttigerxiv.whiskers_palette_kt.WhiskersColor
import com.lighttigerxiv.whiskers_palette_kt.getColor
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

    val useDarkTheme =
        settings.darkMode == "system" && isSystemInDarkTheme() || settings.darkMode == "dark"
    val useLightTheme =
        settings.darkMode == "system" && !isSystemInDarkTheme() || settings.darkMode == "light"

    val useDarkMonet = isAtLeastAndroid12() && settings.theme == "monet" && useDarkTheme
    val useLightMonet = isAtLeastAndroid12() && settings.theme == "monet" && useLightTheme
    val useCustom = settings.theme != "monet"

    val scheme = if (useDarkMonet) {
        dynamicDarkColorScheme(context)
    } else if (useLightMonet) {
        dynamicLightColorScheme(context)
    } else if (useCustom) {

        val lightTheme = CUSTOM_THEMES.find { it.id == settings.theme }!!
        val darkTheme = CUSTOM_THEMES.find { it.id == settings.darkTheme }!!

        if (useDarkTheme) {
            darkColorScheme(
                background = darkTheme.background,
                surfaceVariant = darkTheme.secondaryBackground,
                onBackground = darkTheme.text,
                onSurfaceVariant = darkTheme.text,
                primary = darkTheme.accent,
                onPrimary = darkTheme.onAccent
            )
        } else {
            lightColorScheme(
                background = lightTheme.background,
                surfaceVariant = lightTheme.secondaryBackground,
                onBackground = lightTheme.text,
                onSurfaceVariant = lightTheme.text,
                primary = lightTheme.accent,
                onPrimary = lightTheme.onAccent
            )
        }

    } else {
        if (useDarkTheme) {

            val theme = CUSTOM_THEMES.find { it.id == "panther-banana" }!!

            darkColorScheme(
                background = theme.background,
                surfaceVariant = theme.secondaryBackground,
                onBackground = theme.text,
                onSurfaceVariant = theme.text,
                primary = theme.accent,
                onPrimary = theme.onAccent
            )
        } else {

            val theme = CUSTOM_THEMES.find { it.id == "tiger-banana" }!!

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

    MaterialTheme(
        colorScheme = scheme,
        typography = Typography,
        content = content
    )
}