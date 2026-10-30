package com.lewisfoto.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val SeaDeep = Color(0xFF0B3D4A)
private val SeaMid = Color(0xFF14657A)
private val SeaLight = Color(0xFF2E8CA3)
private val Sand = Color(0xFFE8DCC3)
private val Ink = Color(0xFF1E2A2E)

private val LightColors = lightColorScheme(
    primary = SeaMid,
    onPrimary = Color.White,
    primaryContainer = SeaLight,
    onPrimaryContainer = Color.White,
    secondaryContainer = Sand,
    onSecondaryContainer = Ink,
    background = Sand,
    onBackground = Ink,
    surface = Color.White,
    onSurface = Ink,
    surfaceVariant = Color(0xFFEFF3F0),
    onSurfaceVariant = Color(0xFF44555A)
)

private val DarkColors = darkColorScheme(
    primary = SeaLight,
    onPrimary = Ink,
    primaryContainer = SeaDeep,
    onPrimaryContainer = Color.White,
    background = Color(0xFF0A2229),
    onBackground = Sand,
    surface = Color(0xFF10333C),
    onSurface = Sand,
    surfaceVariant = Color(0xFF16414D),
    onSurfaceVariant = Color(0xFFB9CDD2)
)

@Composable
fun LewisFotoTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        content = content
    )
}
