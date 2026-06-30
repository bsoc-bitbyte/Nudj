package com.tpc.nudj.ui.screen.auth.clubVerification

import com.tpc.nudj.R
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tpc.nudj.ui.components.LoadingIndicator
import androidx.compose.material3.SnackbarHost
import com.tpc.nudj.viewmodels.auth.clubVerification.ClubVerificationViewModel
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import com.tpc.nudj.ui.components.PrimaryButton
import com.tpc.nudj.ui.theme.LocalAppColors
import com.tpc.nudj.ui.theme.NudjTheme
import kotlinx.coroutines.launch

@Composable
fun ClubVerificationScreen(
    viewModel: ClubVerificationViewModel = hiltViewModel(),
    onNavigationBack: () -> Unit
) {
    val clubVerificationUiState by viewModel.clubVerificationUiState.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LoadingIndicator(
        isLoading = clubVerificationUiState.isLoading
    ) {
        ClubVerificationScreenLayout(
            clubVerificationUiState = clubVerificationUiState,
            onBackClick = onNavigationBack,
            snackbarHostState = snackbarHostState,
            onCheckStatusClick = {
                viewModel.onCheckStatusClick()
                scope.launch {
                    val message = if(clubVerificationUiState.isVerified){
                        "Verified"
                    } else {
                        "Not Verified"
                    }
                    snackbarHostState.showSnackbar(message)
                }
            }
        )
    }
}

@Composable
fun ClubVerificationScreenLayout(
    clubVerificationUiState: ClubVerificationUiState,
    onBackClick: () -> Unit,
    onCheckStatusClick: () -> Unit,
    snackbarHostState: SnackbarHostState
) {
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState)},
        containerColor = LocalAppColors.current.background
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ){
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(10.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "Back Button",
                    tint = LocalAppColors.current.onBackground,
                    modifier = Modifier.size(40.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "Hang tight!",
                    style = MaterialTheme.typography.headlineLarge,
                    color = LocalAppColors.current.onBackground
                )

                Spacer(modifier = Modifier.height(32.dp))

                Image(
                    painter = painterResource(id = R.drawable.meditating_mascot),
                    contentDescription = "Monkey Meditating Graphic",
                    modifier = Modifier.size(300.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Your access is on the way",
                    style = MaterialTheme.typography.titleLarge,
                    color = LocalAppColors.current.onBackground
                )

                Spacer(modifier = Modifier.height(48.dp))

                PrimaryButton(
                    text = "Check Status",
                    onClick = onCheckStatusClick,
                    modifier = Modifier.fillMaxWidth(0.7f)
                )
            }
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
fun ClubVerificationScreenPreview() {
    NudjTheme {
        ClubVerificationScreenLayout(
            clubVerificationUiState = ClubVerificationUiState(),
            onBackClick = {},
            onCheckStatusClick = {},
            snackbarHostState = remember { SnackbarHostState() }
        )
    }
}