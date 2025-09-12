package com.example.hemanaus.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Cores principais da aplicação
val Red600 = Color(0xFFDC2626)
val Red700 = Color(0xFFB91C1C)
val Red50 = Color(0xFFFEF2F2)
val Red100 = Color(0xFFFEE2E2)

val Green600 = Color(0xFF059669)
val Green700 = Color(0xFF047857)
val Green50 = Color(0xFFF0FDF4)
val Green100 = Color(0xFFDCFCE7)

val Blue600 = Color(0xFF2563EB)
val Blue700 = Color(0xFF1D4ED8)
val Blue50 = Color(0xFFEFF6FF)
val Blue100 = Color(0xFFDBEAFE)

val Orange600 = Color(0xFFEA580C)
val Orange50 = Color(0xFFFFF7ED)
val Orange100 = Color(0xFFFFEDD5)

val Yellow600 = Color(0xFFD97706)
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

@Composable
fun HeManausTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
        shapes = Shapes
    )
}