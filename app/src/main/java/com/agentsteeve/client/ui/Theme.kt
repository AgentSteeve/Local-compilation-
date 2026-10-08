package com.agentsteeve.client.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val InkBlack   = Color(0xFF0B1220)
val Surface    = Color(0xFF121A2A)
val Border     = Color(0xFF1E2A3E)
val Cyan       = Color(0xFF22D3EE)
val CyanDim    = Color(0xFF3A4A5E)
val TextMain   = Color(0xFFE6EDF5)
val TextMuted  = Color(0xFF8A9BB0)

private val AgentColors = darkColorScheme(
    primary        = Cyan,
    onPrimary      = InkBlack,
    background     = InkBlack,
    onBackground   = TextMain,
    surface        = Surface,
    onSurface      = TextMain,
    surfaceVariant = Border,
    onSurfaceVariant = TextMuted,
    outline        = Border
)

@Composable
fun AgentTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = AgentColors,
        content = content
    )
}
