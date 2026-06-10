package com.tpc.nudj.ui.screen.auth.forgotPassword

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tpc.nudj.ui.components.EmailTextField
import com.tpc.nudj.ui.components.NudjTopAppBar
import com.tpc.nudj.ui.components.PrimaryButton
import com.tpc.nudj.ui.screen.auth.login.LoginScreenLayout
import com.tpc.nudj.ui.screen.auth.login.LoginUiState
import com.tpc.nudj.viewmodels.auth.forgotPassword.ForgotPasswordViewModel

@Composable
fun ForgetPasswordScreen(
    viewModel: ForgotPasswordViewModel = hiltViewModel(),
    onSendEmailClick :()-> Unit ={}
) {
    Scaffold(
        topBar = {
            NudjTopAppBar(
                onBackClick = {}
            )
        }
    ) { paddingValues ->
        val uiState by viewModel.forgotPasswordUiState.collectAsState()
        ForgetPasswordScreenLayout(
            modifier = Modifier.padding(paddingValues),
            uiState = uiState,
            onEmailInput = viewModel::onEmailChange,
            onSendEmailClick = onSendEmailClick

        )
    }
}

@Composable
fun ForgetPasswordScreenLayout(
    modifier: Modifier = Modifier,
    uiState: ForgotPasswordUiState,
    onEmailInput : (String) -> Unit,
    onSendEmailClick: () -> Unit

){
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ){
        Spacer(modifier = Modifier.height(160.dp))
        EmailTextField(
            value = uiState.email,
            onValueChange = onEmailInput
        )
        Spacer(modifier = Modifier.height(24.dp))
        PrimaryButton(
            text = "Send Email",
            onClick = onSendEmailClick,
            modifier = Modifier.fillMaxWidth()
        )
    }

}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ForgetPasswordScreenLayoutPreview() {
    ForgetPasswordScreenLayout(
        modifier = Modifier,
        uiState = ForgotPasswordUiState(),
        onEmailInput = {},
        onSendEmailClick = {}
    )
}