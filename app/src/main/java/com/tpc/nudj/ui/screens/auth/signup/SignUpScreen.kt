package com.tpc.nudj.ui.screens.auth.signup

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tpc.nudj.R
import com.tpc.nudj.ui.components.EmailField
import com.tpc.nudj.ui.components.PasswordField
import com.tpc.nudj.ui.components.PrimaryButton
import com.tpc.nudj.ui.theme.ClashDisplay
import com.tpc.nudj.ui.theme.LocalAppColors
import com.tpc.nudj.ui.theme.NudjTheme
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    onSuccessfulSignIn: () -> Unit,
    goToEmailVerificationScreen: () -> Unit

) {
    val uiState by viewModel.signUpUiState.collectAsState()
    val context = LocalContext.current
    SignUpScreenLayout(
        uiState = uiState,
        onEmailChange = { email ->
            viewModel.onEmailChange(email)
        },
        onPasswordChange = { password ->
            viewModel.onPasswordChange(password)
        },
        onConfirmPasswordChange = { confirmPassword ->
            viewModel.onConfirmPasswordChange(confirmPassword)
        },
        onSignUpClick = {
            viewModel.onClickRegister(
                goToEmailVerificationScreen = goToEmailVerificationScreen
            )
        },
        onGoogleSignUpClick = {
            viewModel.onGoogleClicked(context = context,
                onSuccessfulSignIn = onSuccessfulSignIn
            )
        },
        toastMessageShown = {
            viewModel.toastMessageShown()
        }
    )
}


@Composable
fun SignUpScreenLayout(
    uiState: SignUpUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onSignUpClick: () -> Unit,
    onGoogleSignUpClick: () -> Unit,
    toastMessageShown: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    LaunchedEffect(uiState.toastMessage) {
        if (uiState.toastMessage != null) {
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = uiState.toastMessage,
                    duration = SnackbarDuration.Short
                )
            }
            toastMessageShown()
        }
    }

    Scaffold(
        topBar = {},
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier = Modifier
            .fillMaxSize(),
        containerColor = LocalAppColors.current.background
    ) { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .background(LocalAppColors.current.background)
            ) {

                Text(
                    text = "Nudj",
                    fontSize = 52.sp,
                    color = LocalAppColors.current.appTitle,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontFamily = ClashDisplay
                    ),
                    modifier = Modifier
                        .padding(top = 40.dp)

                )

                Spacer(modifier = Modifier.height(32.dp))

                EmailField(
                    email = uiState.email,
                    onEmailChange = onEmailChange
                )

                PasswordField(
                    password = uiState.password,
                    onPasswordChange = onPasswordChange,
                    label = "Password"
                )

                PasswordField(
                    password = uiState.confirmPassword,
                    onPasswordChange = onConfirmPasswordChange,
                    label = "Confirm Password"
                )

                Spacer(modifier = Modifier.height(24.dp))

                PrimaryButton(
                    text = "Sign Up",
                    onClick = onSignUpClick,
                    modifier = Modifier,
                    enabled = uiState.email.isNotBlank() &&
                            uiState.confirmPassword == uiState.password,
                    isDarkModeEnabled = false
                )

                Text(
                    text = "OR",
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 16.dp)
                )

                IconButton(
                    onClick = {
                        onGoogleSignUpClick()
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.google_icon),
                        contentDescription = "Google SignUp",
                        modifier = Modifier
                            .size(96.dp)
                    )
                }
            }
        }
        // Design the layout.
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SignUpScreenPreview() {
    NudjTheme {
        SignUpScreenLayout(
            uiState = SignUpUiState(
                email = "abc123@iiitdmj.ac.in",
                password = "abc123",
                confirmPassword = "abc123"
            ),
            onEmailChange = {},
            onPasswordChange = {},
            onConfirmPasswordChange = {},
            onSignUpClick = {},
            onGoogleSignUpClick = {},
            toastMessageShown = {}
        )
    }
}
