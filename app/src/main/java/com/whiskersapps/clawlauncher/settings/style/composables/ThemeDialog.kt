package com.whiskersapps.clawlauncher.settings.style.composables

import androidx.compose.runtime.Composable
import com.whiskersapps.clawlauncher.settings.style.StyleSettingsScreenIntent
import com.whiskersapps.clawlauncher.settings.style.StyleSettingsScreenState

@Composable
fun ThemeDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    showDarkThemes: Boolean = false,
    state: StyleSettingsScreenState,
    onAction: (StyleSettingsScreenIntent) -> Unit
) {
//    PreviewTheme(
//        useMonet = state.settings.theme == "monet" && !showDarkThemes,
//        useDarkMonet = state.settings.darkTheme == "monet" && showDarkThemes,
//        dark = showDarkThemes,
//        theme = if (showDarkThemes) state.settings.darkTheme else state.settings.theme
//    ) {
//
//        Dialog(
//            show = show,
//            fullScreen = true,
//            onDismiss = { onDismiss() }
//        ) {
//            NavBar(
//                navigateBack = { onDismiss() },
//                useCloseIcon = true
//            )
//
//            if (state.isAtLeastAndroid12) {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp)
//                        .clip(RoundedCornerShape(16.dp))
//                        .background(MaterialTheme.colorScheme.surfaceVariant)
//                        .clickable {
//                            if (showDarkThemes) {
//                                onAction(StyleSettingsScreenAction.SetDarkTheme("monet"))
//                            } else {
//                                onAction(StyleSettingsScreenAction.SetTheme("monet"))
//                            }
//                        }
//                        .padding(16.dp),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//
//                    Icon(
//                        modifier = Modifier.size(24.dp),
//                        painter = painterResource(R.drawable.palette),
//                        contentDescription = null,
//                        tint = MaterialTheme.colorScheme.onSurfaceVariant
//                    )
//
//                    Spacer(Modifier.width(16.dp))
//
//                    Text(
//                        text = "Material You Colors",
//                        color = MaterialTheme.colorScheme.onSurfaceVariant
//                    )
//                }
//            }
//
//            if (showDarkThemes) {
//                LazyRow(
//                    modifier = Modifier.padding(16.dp),
//                    horizontalArrangement = Arrangement.spacedBy(8.dp)
//                ) {
//                    items(PANTHER_THEMES, key = { it.id }) { theme ->
//                        ThemeCard(
//                            name = theme.name,
//                            backgroundColor = theme.background,
//                            secondaryBackgroundColor = theme.secondaryBackground,
//                            textColor = theme.text,
//                            accentColor = theme.accent,
//                            onAccentColor = theme.onAccent,
//                            onSetTheme = { onAction(StyleSettingsScreenAction.SetDarkTheme(theme.id)) }
//                        )
//                    }
//                }
//            } else {
//                LazyRow(
//                    modifier = Modifier.padding(16.dp),
//                    horizontalArrangement = Arrangement.spacedBy(8.dp)
//                ) {
//                    items(TIGER_THEMES, key = { it.id }) { theme ->
//                        ThemeCard(
//                            name = theme.name,
//                            backgroundColor = theme.background,
//                            secondaryBackgroundColor = theme.secondaryBackground,
//                            textColor = theme.text,
//                            accentColor = theme.accent,
//                            onAccentColor = theme.onAccent,
//                            onSetTheme = { onAction(StyleSettingsScreenAction.SetTheme(theme.id)) }
//                        )
//                    }
//                }
//            }
//        }
//    }
}