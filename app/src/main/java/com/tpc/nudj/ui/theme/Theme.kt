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
    val topBarColor: Color
)
private val LightColorScheme = AppColors(
    background = LightThemeBackgroundBlue,
    primaryButtonColor = LightThemeDarkBlue,
    secondaryButtonColor = Color.White,
    tertiaryButtonColor = LightThemeSeaBlue,
    primaryButtonTextColor = Color.White,
    secondaryButtonTextColor = LightThemeDarkBlue,
    surfaceColor = LightThemeCardLightBlue,
    buttonBorderColor = LightThemeGray,
    textFieldColor = Color.White,
    textFieldBorderColor = LightThemeGray,
    topBarColor = Color(0xFF8CA1AF)
)

private val DarkColorScheme = AppColors(
    background = DarkThemeBackgroundBlue,
    primaryButtonColor = LightThemeDarkBlue,
    secondaryButtonColor = Color.White,
    tertiaryButtonColor = DarkThemeSeaBlue,
    primaryButtonTextColor = Color.White,
    secondaryButtonTextColor = LightThemeDarkBlue,
    surfaceColor = LightThemeCardLightBlue,
    buttonBorderColor = LightThemeGray,
    textFieldColor = Color.White,
    textFieldBorderColor = LightThemeGray,
    topBarColor = Color(0xFF8CA1AF)
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