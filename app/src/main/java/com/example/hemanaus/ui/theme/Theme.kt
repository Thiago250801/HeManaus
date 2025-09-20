package com.hemoam.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.hemanaus.ui.theme.Shapes
import com.example.hemanaus.ui.theme.Typography

// Cores principais da aplicação - Hemoam (vermelho, branco, azul)
val Red600 = Color(0xFFDC2626)
val Red700 = Color(0xFFB91C1C)
val Red800 = Color(0xFF991B1B)
val Red50 = Color(0xFFFEF2F2)
val Red100 = Color(0xFFFEE2E2)
val Red200 = Color(0xFFFECACA)

val Green600 = Color(0xFF059669)
val Green700 = Color(0xFF047857)
val Green50 = Color(0xFFF0FDF4)
val Green100 = Color(0xFFDCFCE7)

val Blue600 = Color(0xFF2563EB)
val Blue700 = Color(0xFF1D4ED8)
val Blue800 = Color(0xFF1E40AF)
val Blue50 = Color(0xFFEFF6FF)
val Blue100 = Color(0xFFDBEAFE)

val Orange600 = Color(0xFFEA580C)
val Orange50 = Color(0xFFFFF7ED)
val Orange700 = Color(0xFFC2410C)
val Orange800 = Color(0xFF9A3412)
val Orange100 = Color(0xFFFFEDD5)

val Yellow600 = Color(0xFFD97706)
val Yellow800 = Color(0xFF92400E)
val Yellow50 = Color(0xFFFEFBF3)
val Yellow100 = Color(0xFFFEF3C7)

val Gray50 = Color(0xFFF9FAFB)
val Gray100 = Color(0xFFF3F4F6)
val Gray200 = Color(0xFFE5E7EB)
val Gray400 = Color(0xFF9CA3AF)
val Gray500 = Color(0xFF6B7280)
val Gray600 = Color(0xFF4B5563)
val Gray700 = Color(0xFF374151)
val Gray800 = Color(0xFF1F2937)
val Gray900 = Color(0xFF111827)

private val LightColorScheme = lightColorScheme(
    primary = Red600,
    onPrimary = Color.White,
    primaryContainer = Red100,
    onPrimaryContainer = Red700,
    secondary = Blue600,
    onSecondary = Color.White,
    secondaryContainer = Blue100,
    onSecondaryContainer = Blue700,
    tertiary = Green600,
    onTertiary = Color.White,
    tertiaryContainer = Green100,
    onTertiaryContainer = Green700,
    error = Red600,
    onError = Color.White,
    errorContainer = Red100,
    onErrorContainer = Red700,
    background = Color.White,
    onBackground = Gray900,
    surface = Color.White,
    onSurface = Gray900,
    surfaceVariant = Gray50,
    onSurfaceVariant = Gray600,
    outline = Gray200,
    outlineVariant = Gray100
)

private val DarkColorScheme = darkColorScheme(
    primary = Red600,
    onPrimary = Color.White,
    primaryContainer = Red700,
    onPrimaryContainer = Red100,
    secondary = Blue600,
    onSecondary = Color.White,
    secondaryContainer = Blue700,
    onSecondaryContainer = Blue100,
    tertiary = Green600,
    onTertiary = Color.White,
    tertiaryContainer = Green700,
    onTertiaryContainer = Green100,
    error = Red600,
    onError = Color.White,
    errorContainer = Red700,
    onErrorContainer = Red100,
    background = Gray900,
    onBackground = Color.White,
    surface = Gray800,
    onSurface = Color.White,
    surfaceVariant = Gray700,
    onSurfaceVariant = Gray400,
    outline = Gray600,
    outlineVariant = Gray700
)

@Composable
fun HemoamTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}