 package com.tpc.nudj.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.tpc.nudj.R
import com.tpc.nudj.ui.theme.LocalAppColors
import com.tpc.nudj.ui.theme.NudjTheme


@Composable
fun LoadingScreenLayout(text: String) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = LocalAppColors.current.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
                    .aspectRatio(1.2f),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.meditating_mascot),
                    contentDescription = null,
                )
            }
            Text(
                text = text,
                color = LocalAppColors.current.onBackground,
                style = MaterialTheme.typography.headlineLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoadingScreen(){
    NudjTheme {
        LoadingScreenLayout(
            text = "Hang in there!"
        )
    }
}