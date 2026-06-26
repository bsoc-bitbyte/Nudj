package com.tpc.nudj.ui.screen.auth.landing

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.tpc.nudj.R
import com.tpc.nudj.model.AuthResult
import com.tpc.nudj.ui.components.NudjTopAppBar
import com.tpc.nudj.ui.components.PrimaryButton
import com.tpc.nudj.ui.theme.LocalAppColors
import com.tpc.nudj.ui.theme.NudjTheme


@Composable
fun LandingScreen(
    onLandingScreenClick: () -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = LocalAppColors.current.background,
    ) { paddingValues ->
        LandingScreenLayout(
            onLandingScreenClick = onLandingScreenClick
        )
    }
}

@Composable
fun LandingScreenLayout(
    onLandingScreenClick: () -> Unit
) {
    val darkTheme : Boolean = isSystemInDarkTheme()
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.7f))
        Image(
            painter = painterResource(if (darkTheme) R.drawable.nudj_logo_dark_theme else R.drawable.nudj_logo),
            contentDescription = "NudjLogo",
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.4f)
        )


        Image(
            painter = painterResource(if (darkTheme) R.drawable.nudj_dark_theme else R.drawable.nudj),
            contentDescription = "Nudj",
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .weight(0.3f)
        )
        Text(
            text = "College Events Simplified.",
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.1f),
            fontWeight = FontWeight.ExtraBold,
            color = if (darkTheme) Color.White else LocalAppColors.current.primaryButtonColor,
            textAlign = TextAlign.Center,

            )
        Spacer(modifier = Modifier.weight(0.4f))
        PrimaryButton(
            text = "Let's get started!",
            onClick = onLandingScreenClick,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .weight(0.15f)


        )
        Spacer(modifier = Modifier.weight(0.2f))
    }
}


@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun LoadingScreenLayoutPreview() {
    NudjTheme {
        LandingScreen(
            onLandingScreenClick = {}
        )
    }
}