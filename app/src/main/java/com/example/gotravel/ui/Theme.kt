package com.example.gotravel.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = goTravel_theme_light_primary,
    onPrimary = goTravel_theme_light_onPrimary_onSecondary_onTertiary_onError,
    primaryContainer = goTravel_theme_light_primaryContainer,
    onPrimaryContainer = goTravel_theme_light_onPrimaryContainer_onSecondaryContainer_onTertiaryContainer,
    secondary = goTravel_theme_light_secondary,
    onSecondary = goTravel_theme_light_onPrimary_onSecondary_onTertiary_onError,
    secondaryContainer = goTravel_theme_light_secondaryContainer,
    onSecondaryContainer = goTravel_theme_light_onPrimaryContainer_onSecondaryContainer_onTertiaryContainer,
    tertiary = goTravel_theme_light_tertiary,
    onTertiary = goTravel_theme_light_onPrimary_onSecondary_onTertiary_onError,
    tertiaryContainer = goTravel_theme_light_tertiaryContainer,
    onTertiaryContainer = goTravel_theme_light_onPrimaryContainer_onSecondaryContainer_onTertiaryContainer,
    error = goTravel_theme_light_error,
    onError = goTravel_theme_light_onPrimary_onSecondary_onTertiary_onError,
    errorContainer = goTravel_theme_light_errorContainer,
    onErrorContainer = goTravel_theme_light_onErrorContainer,
    surface = goTravel_theme_light_surface,
    onSurface = goTravel_theme_light_onSurface,
    onSurfaceVariant = goTravel_theme_light_onSurfaceVariant,
    outline = goTravel_theme_light_outline,
    outlineVariant = goTravel_theme_light_outlineVariant,

)

private val DarkColors = darkColorScheme(
    primary = Color.Gray
)

@Composable
fun AppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (!useDarkTheme) {
        LightColors
    } else {
        DarkColors
    }
    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}
