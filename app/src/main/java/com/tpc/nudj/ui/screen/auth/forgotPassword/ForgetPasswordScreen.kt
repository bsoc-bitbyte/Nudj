package com.tpc.nudj.ui.screen.auth.forgotPassword

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tpc.nudj.ui.components.EmailTextField
import com.tpc.nudj.ui.components.LoadingIndicator
import com.tpc.nudj.ui.components.NudjLogo
import com.tpc.nudj.ui.components.PrimaryButton
import com.tpc.nudj.ui.components.TertiaryButton
import com.tpc.nudj.ui.theme.LocalAppColors
import com.tpc.nudj.ui.theme.NudjTheme
import com.tpc.nudj.viewmodels.auth.forgotPassword.ForgotPasswordViewModel

@Composable
fun ForgetPasswordScreen(
    viewModel: ForgotPasswordViewModel = hiltViewModel(),
    onLoginClick: () -> Unit

) {
    Scaffold(

    ) { paddingValues ->
        val uiState by viewModel.forgotPasswordUiState.collectAsState()
        LoadingIndicator(isLoading = uiState.isLoading) {

            ForgetPasswordScreenLayout(
                modifier = Modifier.padding(paddingValues),
                uiState = uiState,
                onEmailInput = viewModel::onEmailChange,
                onLoginClick = onLoginClick,
                onSendEmailClick = viewModel::onSendEmailClick

            )
        }
    }
}

@Composable
fun ForgetPasswordScreenLayout(
    modifier: Modifier = Modifier,
    uiState: ForgotPasswordUiState,
    onEmailInput : (String) -> Unit,
    onLoginClick :()-> Unit,
    onSendEmailClick: () -> Unit

){

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LocalAppColors.current.background)
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(72.dp))

        NudjLogo()

        Spacer(modifier = Modifier.height(120.dp))

        Text(
            text = "Enter your email address",
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
            EmailTextField(
                value = uiState.email,
                onValueChange = onEmailInput,
                placeholder = "Institute mail id",
                modifier = Modifier.padding(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        PrimaryButton(
            text = "Reset Password",
            onClick = onSendEmailClick,
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
private fun ForgetPasswordScreenLayoutPreview() {
    NudjTheme {
        ForgetPasswordScreenLayout(
            modifier = Modifier,
            uiState = ForgotPasswordUiState(),
            onEmailInput = {},
            onLoginClick = {},
            onSendEmailClick = {}
        )
    }
}