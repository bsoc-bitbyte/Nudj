package com.tpc.nudj.ui.screen.auth.emailVerification

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.foundation.clickable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tpc.nudj.R
import com.tpc.nudj.ui.components.LoadingIndicator
import com.tpc.nudj.ui.components.NudjTopAppBar
import com.tpc.nudj.ui.components.PrimaryButton
import com.tpc.nudj.ui.components.TertiaryButton
import com.tpc.nudj.ui.theme.LocalAppColors
import com.tpc.nudj.ui.theme.NudjTheme
import com.tpc.nudj.viewmodels.auth.emailVerification.EmailVerificationViewModel
import kotlinx.coroutines.launch

@Composable
fun EmailVerificationScreen(
    viewModel: EmailVerificationViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LoadingIndicator(
        isLoading = uiState.isLoading
    ) {
        EmailVerificationScreenLayout(
            uiState = uiState,
            onBackClick = onNavigateBack,
            onResendEmailClick = { viewModel.onResendEmailClick() }
        )
    }
}

@Composable
fun EmailVerificationScreenLayout(
    uiState: EmailVerificationUiState,
    onBackClick: () -> Unit,
    onResendEmailClick: () -> Unit
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        containerColor = LocalAppColors.current.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.meditating_mascot),
                contentDescription = "Email Sent Illustration",
                modifier = Modifier.size(280.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = uiState.formattedTime,
                style = MaterialTheme.typography.displayMedium,
                color = LocalAppColors.current.primaryButtonColor
            )

            Spacer(modifier = Modifier.height(56.dp))

            PrimaryButton(
                text = "Check Inbox",
                onClick = {
                    val inboxIntent = Intent(Intent.ACTION_MAIN).apply {
                        addCategory(Intent.CATEGORY_APP_EMAIL)
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }

                    val chooser = Intent.createChooser(inboxIntent, "Open Inbox via Gmail")

                    try {
                        context.startActivity(chooser)
                    } catch (e: Exception){
                        scope.launch {
                            snackBarHostState.showSnackbar("No Email app found")
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
            )


            Spacer(modifier = Modifier.height(24.dp) )

            TertiaryButton(
                text = "Resend Email",
                onClick = onResendEmailClick,
                enabled = uiState.isResendEnabled
            )

            Spacer(modifier = Modifier.height(16.dp))

            if(!uiState.isResendEnabled){
                Text(
                    text = "Resend in ${uiState.timerInSeconds}s",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

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
            onResendEmailClick = {}
        )
    }
}