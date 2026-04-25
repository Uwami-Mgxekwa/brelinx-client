package com.brelinx.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val BrelinxColorScheme = lightColorScheme(
    primary = BrelinxTeal,
    onPrimary = BrelinxWhite,
    primaryContainer = BrelinxTealLight,
    onPrimaryContainer = BrelinxWhite,
    secondary = BrelinxBlack,
    onSecondary = BrelinxWhite,
    background = BrelinxWhite,
    onBackground = BrelinxBlack,
    surface = BrelinxSurface,
    onSurface = BrelinxBlack,
    surfaceVariant = BrelinxLightGray,
    onSurfaceVariant = BrelinxMediumGray,
    outline = BrelinxDivider,
)

@Composable
fun BrelinxclientTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = BrelinxColorScheme,
        typography = Typography,
        content = content
    )
}
