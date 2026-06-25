package com.tpc.nudj.ui.screen.auth.login

import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.graphics.Color
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
import com.tpc.nudj.model.enums.Role
import com.tpc.nudj.ui.components.LoadingIndicator
import com.tpc.nudj.ui.components.NudjTopAppBar
import com.tpc.nudj.ui.components.SecondaryButton
import com.tpc.nudj.ui.theme.LocalAppColors


@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel()
) {
    Scaffold(containerColor = LocalAppColors.current.background) { paddingValues ->
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
                onRoleSelected = { role ->
                    viewModel.onRoleSelected(role)
                },
                onCreateAccount = viewModel::onCreateAccount,
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
    onGoogleClick: () -> Unit,
    onRoleSelected: (Role) -> Unit,
    onCreateAccount : () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.nudjlogo),
            contentDescription = "Nudj Logo",
            modifier = Modifier.size(90.dp)
        )

        Image(
            painter = painterResource(R.drawable.nudj),
            contentDescription = "Nudj",
            modifier = Modifier
                .height(50.dp)
                .width(100.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (uiState.role == Role.USER) {
                PrimaryButton(
                    text = "Student",
                    onClick = {onRoleSelected(Role.USER)},
                    hasBorder = true,
                    modifier = Modifier.weight(1f)
                )

            } else {
                SecondaryButton(
                    text = "Student",
                    onClick = {onRoleSelected(Role.USER)},
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier= Modifier.weight(0.1f))

            if (uiState.role == Role.ADMIN) {
                PrimaryButton(
                    text = "Admin",
                    onClick = {onRoleSelected(Role.ADMIN)},
                    hasBorder = true,
                    modifier = Modifier.weight(1f)
                )
            } else {
                SecondaryButton(
                    text = "Admin",
                    onClick = {onRoleSelected(Role.ADMIN)},
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "LOGIN",
            style = MaterialTheme.typography.titleLarge,
            color = LocalAppColors.current.secondaryButtonTextColor
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = LocalAppColors.current.surfaceColor
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                EmailTextField(
                    value = uiState.email,
                    onValueChange = onEmailInput,
                    placeholder = "Enter your email"
                )

                Spacer(modifier = Modifier.height(16.dp))

                PasswordTextField(
                    value = uiState.password,
                    onValueChange = onPasswordInput,
                    passwordVisible = uiState.passwordVisible,
                    placeholder = "Enter your password",
                    onPasswordVisibilityToggle = onPasswordVisibilityToggle
                )
            }
        }

        TertiaryButton(
            text = "Forgot Password?",
            onClick = onForgotPasswordClick,
            modifier = Modifier.align(Alignment.End)
        )

        Spacer(modifier = Modifier.height(24.dp))

        PrimaryButton(
            text = "Login",
            onClick = onLoginClick,
            enabled = !uiState.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 112.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                thickness = 1.5.dp,
                color = if(isSystemInDarkTheme()) Color.White else Color.Black
            )

            Text(
                text = "OR",
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = if(isSystemInDarkTheme()) Color.White else Color.Black
            )

            HorizontalDivider(
                modifier = Modifier.weight(1f),
                thickness = 1.5.dp,
                color = if(isSystemInDarkTheme()) Color.White else Color.Black
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedButton(
            onClick = onGoogleClick,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.google),
                contentDescription = "Google Icon",
                modifier = Modifier
                    .size(16.dp)
            )

            Text(
                text = "Continue with Google",
                style = MaterialTheme.typography.bodyMedium,
                color = LocalAppColors.current.secondaryButtonTextColor,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f).wrapContentWidth(Alignment.CenterHorizontally)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Don't have an account?",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )

            TertiaryButton(
                text = "Create Account",
                onClick = onCreateAccount
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true , uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun LoginScreenLayoutPreview() {
    NudjTheme{
        LoginScreenLayout(
            modifier = Modifier,
            uiState = LoginUiState(),
            onEmailInput = {},
            onPasswordInput = {},
            onForgotPasswordClick = {},
            onLoginClick = {},
            onGoogleClick = {},
            onPasswordVisibilityToggle = {},
            onRoleSelected = {},
            onCreateAccount = {}
        )
    }
}