package com.tpc.nudj.ui.screen.auth.emailVerification

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tpc.nudj.R
import com.tpc.nudj.ui.components.NudjTopAppBar
import com.tpc.nudj.ui.components.PrimaryButton
import com.tpc.nudj.ui.components.TertiaryButton
import com.tpc.nudj.ui.theme.NudjTheme
import com.tpc.nudj.viewmodels.auth.emailVerification.EmailVerificationViewModel

@Composable
fun EmailVerificationScreen(
    viewModel: EmailVerificationViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    EmailVerificationScreenLayout(
        uiState = uiState,
        onBackClick = onNavigateBack,
        onCheckInboxClick = { viewModel.onCheckInboxClick() },
        onResendEmailClick = { viewModel.onResendEmailClick() }
    )
}

@Composable
fun EmailVerificationScreenLayout(
    uiState: EmailVerificationUiState,
    onBackClick: () -> Unit,
    onCheckInboxClick: () -> Unit,
    onResendEmailClick: () -> Unit
) {
    Scaffold(
        topBar = {
            NudjTopAppBar(onBackClick = onBackClick)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Sent successfully!",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(48.dp))

            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Email Sent Illustration",
                modifier = Modifier.size(300.dp)
            )

            Spacer(modifier = Modifier.height(64.dp))

            PrimaryButton(
                text = "Check Inbox",
                onClick = onCheckInboxClick,
                modifier = Modifier
                    .padding(bottom = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            TertiaryButton(
                text = "Resend Email?",
                onClick = onResendEmailClick
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewEmailVerificationScreen() {
    NudjTheme {
        EmailVerificationScreenLayout(
            uiState = EmailVerificationUiState(),
            onBackClick = {},
            onCheckInboxClick = {},
            onResendEmailClick = {}
        )
    }
}