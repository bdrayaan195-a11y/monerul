package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme =
    darkColorScheme(
        primary = PoliceGold,
        onPrimary = DarkBackground,
        primaryContainer = PoliceNavyLight,
        onPrimaryContainer = Color.White,
        secondary = EmergencyRed,
        onSecondary = Color.White,
        tertiary = EmeraldGreen,
        background = DarkBackground,
        surface = DarkSurface,
        surfaceVariant = DarkSurfaceVariant,
        onBackground = Color.White,
        onSurface = Color.White
    )

private val LightColorScheme =
    lightColorScheme(
        primary = PoliceNavyPrimary,
        onPrimary = LightOnPrimary,
        primaryContainer = PoliceNavyLight,
        onPrimaryContainer = Color.White,
        secondary = EmergencyRed,
        onSecondary = Color.White,
        tertiary = PoliceGold,
        background = LightBackground,
        surface = LightSurface,
        surfaceVariant = LightSurfaceVariant,
        onBackground = PoliceNavyPrimary,
        onSurface = PoliceNavyPrimary
    )

@Composable
fun KhilparaAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    KhilparaAppTheme(darkTheme = darkTheme, dynamicColor = dynamicColor, content = content)
}
