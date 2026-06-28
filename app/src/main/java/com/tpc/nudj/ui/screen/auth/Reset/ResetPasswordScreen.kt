package com.tpc.nudj.ui.screen.auth.Reset

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tpc.nudj.ui.components.LoadingIndicator
import com.tpc.nudj.ui.theme.LocalAppColors
import com.tpc.nudj.viewmodels.auth.ResetPassword.ResetPasswordViewModel
import com.tpc.nudj.ui.components.NudjLogo
import com.tpc.nudj.ui.components.PasswordTextField
import com.tpc.nudj.ui.components.PrimaryButton
import com.tpc.nudj.ui.components.TertiaryButton
import com.tpc.nudj.ui.theme.NudjTheme

@Composable
fun ResetPasswordScreen(
    viewModel: ResetPasswordViewModel = hiltViewModel(),
    onLoginClick :() ->Unit
) {
    Scaffold(
        containerColor = LocalAppColors.current.background
    ) { paddingValues ->

        val uiState by viewModel.resetPasswordUiState.collectAsState()
        LoadingIndicator(isLoading = uiState.isLoading) {
            ResetPasswordScreenLayout(
                modifier = Modifier.padding(paddingValues),
                uiState = uiState,
                onPasswordInput = viewModel::onPasswordChange,
                onConfirmPasswordInput = viewModel::onConfirmPasswordChange,
                onPasswordVisibilityToggle = viewModel::togglePasswordVisibility,
                onConfirmPasswordVisibilityToggle = viewModel::toggleConfirmPasswordVisibility,
                onSubmitClick = viewModel::onSubmitClick,
                onLoginClick = onLoginClick
            )
        }
    }
}@Composable
fun ResetPasswordScreenLayout(
    uiState: ResetPasswordUiState,
    modifier: Modifier = Modifier,
    onPasswordInput: (String) -> Unit,
    onConfirmPasswordInput: (String) -> Unit,
    onPasswordVisibilityToggle: () -> Unit,
    onConfirmPasswordVisibilityToggle: () -> Unit,
    onSubmitClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(56.dp))
        NudjLogo()

        Spacer(modifier = Modifier.height(96.dp))

        Text(
            text = "Reset Password",
            style = MaterialTheme.typography.titleLarge,
            color = LocalAppColors.current.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

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
                PasswordTextField(
                    value = uiState.password,
                    onValueChange = onPasswordInput,
                    passwordVisible = uiState.passwordVisible,
                    onPasswordVisibilityToggle =
                        onPasswordVisibilityToggle,
                    placeholder = "New Password"
                )

                Spacer(modifier = Modifier.height(16.dp))

                PasswordTextField(
                    value = uiState.confirmPassword,
                    onValueChange = onConfirmPasswordInput,
                    passwordVisible = uiState.confirmPasswordVisible,
                    onPasswordVisibilityToggle = onConfirmPasswordVisibilityToggle,
                    placeholder = "Confirm Password"
                )
            }
        }

        Spacer(modifier = Modifier.height(64.dp))

        PrimaryButton(
            text = "Submit",
            onClick = onSubmitClick,
            enabled = !uiState.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.padding(bottom = 40.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Already have an account?",
                style = MaterialTheme.typography.bodyMedium,
                color = LocalAppColors.current.onBackground
            )

            TertiaryButton(
                text = "Login",
                onClick = onLoginClick
            )
        }
    }
}
@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ResetPasswordScreenLayoutPreview() {
    NudjTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = LocalAppColors.current.background
        ) {
            ResetPasswordScreenLayout(
                uiState = ResetPasswordUiState(),
                onPasswordInput = {},
                onConfirmPasswordInput = {},
                onPasswordVisibilityToggle = {},
                onConfirmPasswordVisibilityToggle = {},
                onSubmitClick = {},
                onLoginClick = {}
            )
        }
    }
}