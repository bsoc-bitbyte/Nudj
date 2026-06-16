package com.tpc.nudj.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color


val LocalAppColors = staticCompositionLocalOf { LightColorScheme }

data class AppColors(
    // Variables for Pages
    val background: Color,
    val primaryButtonColor: Color,
    val secondaryButtonColor: Color,
    val tertiaryButtonColor: Color,
    val primaryButtonTextColor: Color,
    val secondaryButtonTextColor: Color,
    val surfaceColor: Color,
    val buttonBorderColor: Color,
    val textFieldColor: Color,
    val textFieldBorderColor: Color,
)
private val DarkColorScheme = AppColors(
    background = LightBlue,
    primaryButtonColor = DarkBlue,
    secondaryButtonColor = Color.White,
    tertiaryButtonColor = SeaBlue,
    primaryButtonTextColor = Color.White,
    secondaryButtonTextColor = DarkBlue,
    surfaceColor = Gray,
    buttonBorderColor = Gray,
    textFieldColor = Color.White,
    textFieldBorderColor = Gray
)

private val LightColorScheme = AppColors(
    background = LightBlue,
    primaryButtonColor = DarkBlue,
    secondaryButtonColor = Color.White,
    tertiaryButtonColor = SeaBlue,
    primaryButtonTextColor = Color.White,
    secondaryButtonTextColor = DarkBlue,
    surfaceColor = Gray,
    buttonBorderColor = Gray,
    textFieldColor = Color.White,
    textFieldBorderColor = Gray
)

@Composable
fun NudjTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    CompositionLocalProvider(LocalAppColors provides colorScheme) {
        MaterialTheme(
            colorScheme = if (darkTheme) darkColorScheme() else lightColorScheme(),
            typography = appTypography(),
            content = content
        )
    }
}