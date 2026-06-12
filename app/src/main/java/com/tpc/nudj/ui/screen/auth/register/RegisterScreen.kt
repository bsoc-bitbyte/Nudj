package com.tpc.nudj.ui.screen.auth.register

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tpc.nudj.R
import com.tpc.nudj.ui.components.EmailTextField
import com.tpc.nudj.ui.components.NudjTopAppBar
import com.tpc.nudj.ui.components.PasswordTextField
import com.tpc.nudj.ui.components.PrimaryButton
import com.tpc.nudj.ui.theme.NudjTheme
import com.tpc.nudj.viewmodels.auth.register.RegisterViewModel

@Composable
fun RegisterScreen(
    viewmodel: RegisterViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val uiState by viewmodel.registerUiState.collectAsStateWithLifecycle()

    RegisterScreenLayout(
        uiState = uiState,
        onEmailInput = { email -> viewmodel.onEmailChange(email) },
        onPasswordInput = { pass -> viewmodel.onPasswordChange(pass) },
        onConfirmPasswordInput = { pass -> viewmodel.onConfirmPasswordChange(pass) },
        onSignUpClick = {},
        onGoogleClick =  {},
        onBackClick = onNavigateBack
    )
}

@Composable
fun RegisterScreenLayout(
    uiState: RegisterUiState,
    onEmailInput: (String) -> Unit,
    onPasswordInput: (String) -> Unit,
    onConfirmPasswordInput: (String) -> Unit,
    onSignUpClick: () -> Unit,
    onGoogleClick: () -> Unit,
    onBackClick: () -> Unit
) {
    var passwordVisible by rememberSaveable{ mutableStateOf(false) }
    var confirmPasswordVisible by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            NudjTopAppBar(onBackClick = onBackClick)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {


            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                EmailTextField(
                    value = uiState.email,
                    onValueChange = onEmailInput
                )

                PasswordTextField(
                    value = uiState.password,
                    onValueChange = onPasswordInput,
                    label = "Password",
                    passwordVisible = passwordVisible,
                    onPasswordVisibilityToggle = { passwordVisible = !passwordVisible }
                )

                PasswordTextField(
                    value = uiState.confirmPassword,
                    onValueChange = onConfirmPasswordInput,
                    label = "Confirm Password",
                    passwordVisible = confirmPasswordVisible,
                    onPasswordVisibilityToggle = { confirmPasswordVisible = !confirmPasswordVisible }
                )
            }


            Column(
                modifier = Modifier.padding(top = 40.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PrimaryButton(
                    text = "Sign up",
                    onClick = onSignUpClick,
                    modifier = Modifier.padding(16.dp)
                )

                Text("OR", color = MaterialTheme.colorScheme.onSurfaceVariant)


                IconButton(
                    onClick = onGoogleClick,
                    modifier = Modifier.size(56.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.google),
                        contentDescription = "Continue with Google",
                        tint = Color.Unspecified
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewRegisterScreen() {
    NudjTheme {
        RegisterScreenLayout(
            uiState = RegisterUiState(),
            onEmailInput = {},
            onPasswordInput = {},
            onConfirmPasswordInput = {},
            onSignUpClick = {},
            onGoogleClick = {},
            onBackClick = {}
        )
    }
}