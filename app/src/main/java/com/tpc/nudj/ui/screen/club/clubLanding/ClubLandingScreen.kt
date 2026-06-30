package com.tpc.nudj.ui.screen.club.clubLanding

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tpc.nudj.R
import com.tpc.nudj.ui.components.LoadingIndicator
import com.tpc.nudj.ui.components.NudjLogo
import com.tpc.nudj.ui.components.PrimaryButton
import com.tpc.nudj.ui.components.TertiaryButton
import com.tpc.nudj.ui.theme.LocalAppColors
import com.tpc.nudj.ui.theme.NudjTheme
import com.tpc.nudj.viewmodels.club.clubLanding.ClubLandingViewModel

@Composable
fun ClubLandingScreen(
    viewModel: ClubLandingViewModel = hiltViewModel()
) {
    val clubLandingUiState by viewModel.clubLandingUiState.collectAsStateWithLifecycle()

    LoadingIndicator(
        isLoading = clubLandingUiState.isLoading
    ) {
        ClubLandingScreenLayout(
            clubLandingUiState = clubLandingUiState,
            onCreateClubEventsClick = { viewModel.createClubEvents() },
            onSkipClick = { viewModel.onClickSkip() }
        )
    }
}

@Composable
fun ClubLandingScreenLayout(
    clubLandingUiState: ClubLandingUiState,
    onCreateClubEventsClick: () -> Unit,
    onSkipClick: () -> Unit
) {
    Scaffold(
        containerColor = LocalAppColors.current.background,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(modifier = Modifier.weight(1f))
            Box(modifier = Modifier.scale(1.8f)) {
                NudjLogo()
            }


            Spacer(modifier = Modifier.height(45.dp))

            Text(
                text = "College Events Simplified",
                style = MaterialTheme.typography.bodyMedium,
                color = LocalAppColors.current.onBackground
            )

            Spacer(modifier = Modifier.weight(1f))
            PrimaryButton(
                text = "Create Club/Event",
                onClick = onCreateClubEventsClick,
                modifier = Modifier.fillMaxWidth(0.7f)
            )
            TertiaryButton(
                text = "Skip",
                onClick = onSkipClick,
                modifier = Modifier.fillMaxWidth(0.7f)
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
@Composable
fun ClubLandingScreenPreview() {
    NudjTheme {
        ClubLandingScreenLayout(

            clubLandingUiState = ClubLandingUiState(),
            onCreateClubEventsClick = { },
            onSkipClick = { }
        )
    }
}