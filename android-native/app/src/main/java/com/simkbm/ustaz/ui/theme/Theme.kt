package com.simkbm.ustaz.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = Emerald600,
    onPrimary = androidx.compose.ui.graphics.Color.White,
    primaryContainer = Emerald100,
    onPrimaryContainer = Emerald700,
    secondary = Teal600,
    onSecondary = androidx.compose.ui.graphics.Color.White,
    secondaryContainer = Emerald50,
    onSecondaryContainer = Teal700,
    tertiary = Amber600,
    onTertiary = androidx.compose.ui.graphics.Color.White,
    tertiaryContainer = Amber50,
    onTertiaryContainer = Amber700,
    error = Rose600,
    onError = androidx.compose.ui.graphics.Color.White,
    errorContainer = Rose50,
    onErrorContainer = Rose700,
    background = Slate50,
    onBackground = Slate900,
    surface = androidx.compose.ui.graphics.Color.White,
    onSurface = Slate900,
    surfaceVariant = Slate100,
    onSurfaceVariant = Slate600,
    outline = Slate300,
    outlineVariant = Slate200,
)

private val DarkColorScheme = darkColorScheme(
    primary = Emerald600,
    onPrimary = androidx.compose.ui.graphics.Color.White,
    primaryContainer = Emerald700,
    onPrimaryContainer = Emerald100,
    secondary = Teal600,
    onSecondary = androidx.compose.ui.graphics.Color.White,
    secondaryContainer = Teal700,
    onSecondaryContainer = Emerald50,
    tertiary = Amber500,
    onTertiary = androidx.compose.ui.graphics.Color.White,
    tertiaryContainer = Amber700,
    onTertiaryContainer = Amber50,
    error = Rose500,
    onError = androidx.compose.ui.graphics.Color.White,
    errorContainer = Rose700,
    onErrorContainer = Rose50,
    background = Slate900,
    onBackground = Slate50,
    surface = Slate800,
    onSurface = Slate50,
    surfaceVariant = Slate700,
    onSurfaceVariant = Slate300,
    outline = Slate500,
    outlineVariant = Slate600,
)

@Composable
fun SIMKBMTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
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

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
