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
    val onBackground: Color,
    val primaryButtonColor: Color,
    val secondaryButtonColor: Color,
    val tertiaryButtonColor: Color,
    val primaryButtonTextColor: Color,
    val secondaryButtonTextColor: Color,
    val surfaceColor: Color,
    val buttonBorderColor: Color,
    val textFieldColor: Color,
    val textFieldBorderColor: Color,
    val topBarColor: Color,
    val textFieldCardColor: Color,
    val dropDownMenuColor: Color,
    val blackTextColor: Color,
    )

private val LightColorScheme = AppColors(
    background = LightThemeBackgroundBlue,
    onBackground = LightThemeDarkBlue,
    primaryButtonColor = LightThemeDarkBlue,
    secondaryButtonColor = Color.White,
    tertiaryButtonColor = LightThemeSeaBlue,
    primaryButtonTextColor = Color.White,
    secondaryButtonTextColor = LightThemeDarkBlue,
    surfaceColor = LightThemeCardLightBlue,
    buttonBorderColor = LightThemeGray,
    textFieldColor = Color.White,
    textFieldBorderColor = DarkThemeSecondaryButtonColor,
    topBarColor = Color(0xFF8CA1AF),
    textFieldCardColor = LightThemeCardLightBlue,
    dropDownMenuColor = Color.White,
    blackTextColor = Black,
)

private val DarkColorScheme = AppColors(
    background = DarkThemeBackgroundBlue,
    onBackground = DarkThemeSecondaryButtonColor,
    primaryButtonColor = DarkThemeDarkBlue,
    secondaryButtonColor = DarkThemeSecondaryButtonColor,
    tertiaryButtonColor = DarkThemeSeaBlue,
    primaryButtonTextColor = Color.White,
    secondaryButtonTextColor = LightThemeDarkBlue,
    surfaceColor = DarkThemeDarkBlue,
    buttonBorderColor = LightThemeGray,
    textFieldColor = DarkThemeSecondaryButtonColor,
    textFieldBorderColor = DarkThemeSecondaryButtonColor,
    topBarColor = Color(0xFF8CA1AF),
    textFieldCardColor = DarkThemeDarkBlue,
    dropDownMenuColor = DarkThemeSecondaryButtonColor,
    blackTextColor = Black,
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