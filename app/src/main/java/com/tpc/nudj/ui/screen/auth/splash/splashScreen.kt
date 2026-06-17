package com.tpc.nudj.ui.screen.auth.splash

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tpc.nudj.R
import com.tpc.nudj.ui.theme.LocalAppColors
import com.tpc.nudj.ui.theme.NudjTheme

@Composable
fun SplashScreenLayout(

){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalAppColors.current.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.weight(0.5f))
        Image(
            painter = painterResource(R.drawable.splashscreenmonkey),
            contentDescription = "Nudj monkey",
            modifier = Modifier
                .fillMaxWidth()
                .weight(3f),
            contentScale = ContentScale.Fit

        )

        Image(
            painter = painterResource(R.drawable.splashscreenwelcome),
            contentDescription = "Welcome",
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(80.dp),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.weight(0.3f))
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SplashScreenLayoutPreview() {
    NudjTheme {
       SplashScreenLayout()

    }
}

