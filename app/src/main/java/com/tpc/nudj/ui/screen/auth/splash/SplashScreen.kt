package com.tpc.nudj.ui.screen.auth.splash

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tpc.nudj.R
import com.tpc.nudj.ui.theme.LocalAppColors
import com.tpc.nudj.ui.theme.NudjTheme
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun SplashScreen(
    onSplashCompleted: () -> Unit
) {
    LaunchedEffect(key1 = true) {
        delay(2000L.milliseconds)
        onSplashCompleted()
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = LocalAppColors.current.background

    ) { paddingValues ->
        SplashScreenLayout()
    }
}


@Composable
fun SplashScreenLayout() {
    val darkTheme : Boolean = isSystemInDarkTheme()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalAppColors.current.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(R.drawable.splash_screen_monkey),
            contentDescription = "Nudj monkey",
            modifier = Modifier
                .fillMaxWidth()
                .weight(3f),
            contentScale = ContentScale.Fit

        )

        Image(
            painter = painterResource(if (darkTheme) R.drawable.welcome_dark_theme else R.drawable.welcome),
            contentDescription = "Welcome",
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(80.dp),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.weight(0.7f))
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SplashScreenLayoutPreview() {
    NudjTheme {
        SplashScreen(
            onSplashCompleted = {}
        )
    }
}

