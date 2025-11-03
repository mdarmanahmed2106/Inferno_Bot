package com.example.inferno_bot.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = AccentPurple,
    onPrimary = TextPrimary,
    primaryContainer = UserBubble,
    onPrimaryContainer = TextPrimary,
    secondary = AccentCyan,
    onSecondary = TextPrimary,
    secondaryContainer = BotBubble,
    onSecondaryContainer = TextPrimary,
    tertiary = AccentPink,
    background = DarkBackground,
    onBackground = TextPrimary,
    surface = DarkSurface,
    onSurface = TextPrimary,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = TextSecondary,
    outline = DarkBorder,
    error = ErrorRed
)

@Composable
fun InfernoBotTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}