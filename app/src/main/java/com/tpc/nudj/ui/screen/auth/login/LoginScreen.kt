package com.tpc.nudj.ui.screen.auth.login

import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import com.tpc.nudj.R
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tpc.nudj.ui.components.EmailTextField
import com.tpc.nudj.ui.components.PasswordTextField
import com.tpc.nudj.ui.components.PrimaryButton
import com.tpc.nudj.ui.components.TertiaryButton
import com.tpc.nudj.ui.theme.NudjTheme
import com.tpc.nudj.viewmodels.auth.login.LoginViewModel
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.tpc.nudj.ui.components.LoadingIndicator
import com.tpc.nudj.ui.components.NudjTopAppBar


@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),


    ) {
    Scaffold(
        topBar = {
            NudjTopAppBar(
                onBackClick = {}
            )
        }
    ) { paddingValues ->
        val uiState by viewModel.loginUiState.collectAsState()
        LoadingIndicator(isLoading = uiState.isLoading) {
            LoginScreenLayout(
                modifier = Modifier.padding(paddingValues),
                uiState = uiState,
                onEmailInput = { email ->
                    viewModel.onEmailChange(email)
                },
                onPasswordInput = { pass ->
                    viewModel.onPasswordChange(pass)
                },
                onForgotPasswordClick = viewModel::onForgotPasswordClick,
                onLoginClick = viewModel::onLoginClick,
                onGoogleClick = viewModel::onGoogleClick,
                onPasswordVisibilityToggle = viewModel::togglePasswordVisibility,
            )
        }
    }
}
@Composable
fun LoginScreenLayout(
    uiState: LoginUiState,
    modifier: Modifier = Modifier,
    onEmailInput: (String) -> Unit,
    onPasswordInput: (String) -> Unit,
    onPasswordVisibilityToggle: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onLoginClick: () -> Unit,
    onGoogleClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        EmailTextField(
            value = uiState.email,
            onValueChange = onEmailInput,
            placeholder= "Enter your email"
        )
        Spacer(modifier = Modifier.height(16.dp))

        PasswordTextField(
            value = uiState.password,
            onValueChange = onPasswordInput,
            passwordVisible = uiState.passwordVisible,
            placeholder = "Enter your password",
            onPasswordVisibilityToggle = onPasswordVisibilityToggle
        )
        TertiaryButton(
            text = "Forgot Password?",
            onClick = onForgotPasswordClick,
            modifier = Modifier.align(Alignment.Start)


        )


        Spacer(modifier = Modifier.height(24.dp))
        PrimaryButton(
            text = "Login",
            onClick = onLoginClick,
            enabled = !uiState.isLoading,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "OR",
                modifier = Modifier.padding(horizontal = 16.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            HorizontalDivider(
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Image(
            painter = painterResource(id = R.drawable.google),
            contentDescription = "Google Sign In",
            modifier = Modifier
                .size(50.dp)
                .clickable(onClick = onGoogleClick)
        )



    }

}
@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun LoginScreenLayoutPreview() {
    NudjTheme {
        LoginScreenLayout(
            modifier = Modifier,
            uiState = LoginUiState(),
            onEmailInput = {},
            onPasswordInput = {},
            onForgotPasswordClick = {},
            onLoginClick = {},
            onGoogleClick = {},
            onPasswordVisibilityToggle = {}
        )
    }
}