package com.whiskersapps.clawlauncher.views.main.views.settings.views.style.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.whiskersapps.clawlauncher.shared.view.composables.Dialog
import com.whiskersapps.clawlauncher.shared.view.composables.NavBar
import com.whiskersapps.clawlauncher.shared.view.theme.PANTHER_THEMES
import com.whiskersapps.clawlauncher.shared.view.theme.TIGER_THEMES
import com.whiskersapps.clawlauncher.views.main.views.settings.views.style.intent.StyleSettingsScreenAction
import com.whiskersapps.clawlauncher.views.main.views.settings.views.style.model.StyleSettingsScreenState

@Composable
fun ThemeDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    showDarkThemes: Boolean = false,
    state: StyleSettingsScreenState,
    onAction: (StyleSettingsScreenAction) -> Unit
) {

    Dialog(
        show = show,
        fullScreen = true,
        onDismiss = { onDismiss() },
    ) {
        NavBar(
            navigateBack = { onDismiss() },
            useCloseIcon = true
        )

        if (state.isAtLeastAndroid12) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .clickable {
                        if (showDarkThemes) {
                            onAction(StyleSettingsScreenAction.SetDarkTheme("monet"))
                        }else{
                            onAction(StyleSettingsScreenAction.SetTheme("monet"))
                        }
                    }
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = state.settings.theme == "monet",
                    onClick = {
                        if (showDarkThemes) {
                            onAction(StyleSettingsScreenAction.SetDarkTheme("monet"))
                        }else{
                            onAction(StyleSettingsScreenAction.SetTheme("monet"))
                        }
                    }
                )

                Text(
                    text = "Material You Colors",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (showDarkThemes) {
            LazyRow {
                items(PANTHER_THEMES, key = { it.id }) { theme ->
                    ThemeCard(
                        name = theme.name,
                        backgroundColor = theme.background,
                        secondaryBackgroundColor = theme.secondaryBackground,
                        textColor = theme.text,
                        accentColor = theme.accent,
                        onAccentColor = theme.onAccent,
                        onSetTheme = { onAction(StyleSettingsScreenAction.SetDarkTheme(theme.id)) }
                    )
                }
            }
        } else {
            LazyRow {
                items(TIGER_THEMES, key = { it.id }) { theme ->
                    ThemeCard(
                        name = theme.name,
                        backgroundColor = theme.background,
                        secondaryBackgroundColor = theme.secondaryBackground,
                        textColor = theme.text,
                        accentColor = theme.accent,
                        onAccentColor = theme.onAccent,
                        onSetTheme = { onAction(StyleSettingsScreenAction.SetTheme(theme.id)) }
                    )
                }
            }
        }
    }
}