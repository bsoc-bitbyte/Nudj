package com.tpc.nudj.ui.screen.auth.register

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tpc.nudj.R
import com.tpc.nudj.model.enums.Role
import com.tpc.nudj.ui.components.EmailTextField
import com.tpc.nudj.ui.components.LoadingIndicator
import com.tpc.nudj.ui.components.PasswordTextField
import com.tpc.nudj.ui.components.PrimaryButton
import com.tpc.nudj.ui.components.SecondaryButton
import com.tpc.nudj.ui.components.TertiaryButton
import com.tpc.nudj.ui.theme.LocalAppColors
import com.tpc.nudj.ui.theme.NudjTheme
import com.tpc.nudj.viewmodels.auth.register.RegisterViewModel

@Composable
fun RegisterScreen(
    viewmodel: RegisterViewModel = hiltViewModel(),
    onNavigateToLogin: () -> Unit,
) {
    val uiState by viewmodel.registerUiState.collectAsStateWithLifecycle()

    LoadingIndicator(
        isLoading = uiState.isLoading
    ) {
        RegisterScreenLayout(
            uiState = uiState,
            onEmailInput = { email -> viewmodel.onEmailChange(email) },
            onPasswordInput = { pass -> viewmodel.onPasswordChange(pass) },
            onConfirmPasswordInput = { pass -> viewmodel.onConfirmPasswordChange(pass) },
            onPasswordVisibilityToggle = { viewmodel.onPasswordVisibilityToggle() },
            onConfirmPasswordVisibilityToggle = { viewmodel.onConfirmPasswordVisibilityToggle() },
            onRoleSelected = { role -> viewmodel.onRoleChange(role) },
            onSignUpClick = viewmodel::onRegisterClick,
            onGoogleClick = viewmodel::onGoogleClick,
            onLoginClick = onNavigateToLogin
        )
    }
}

@Composable
fun RegisterScreenLayout(
    uiState: RegisterUiState,
    onEmailInput: (String) -> Unit,
    onPasswordInput: (String) -> Unit,
    onConfirmPasswordInput: (String) -> Unit,
    onPasswordVisibilityToggle: () -> Unit,
    onConfirmPasswordVisibilityToggle: () -> Unit,
    onRoleSelected: (Role) -> Unit,
    onSignUpClick: () -> Unit,
    onGoogleClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    val dividerAndTextColor = if (isSystemInDarkTheme()) Color.White else Color.Black
    val currentRole = uiState.role

    Scaffold(
        containerColor = LocalAppColors.current.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(0.3f))

            Image(
                painter = painterResource(id = R.drawable.nudj_logo),
                contentDescription = "NUDJ Icon",
                modifier = Modifier.size(76.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Image(
                painter = painterResource(id = R.drawable.nudj),
                contentDescription = "NUDJ Text",
                modifier = Modifier.height(36.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
            ) {
                val buttonModifier = Modifier.weight(1f).fillMaxHeight()

                RoleSelectionButton(
                    text = "Student",
                    isSelected = currentRole == Role.USER,
                    onClick = { onRoleSelected(Role.USER) },
                    modifier = buttonModifier
                )

                Spacer(modifier = Modifier.width(16.dp))

                RoleSelectionButton(
                    text = "Admin",
                    isSelected = currentRole == Role.ADMIN,
                    onClick = { onRoleSelected(Role.ADMIN) },
                    modifier = buttonModifier
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "CREATE ACCOUNT",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(14.dp))

            Surface(
                color = LocalAppColors.current.surfaceColor,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    EmailTextField(
                        value = uiState.email,
                        onValueChange = onEmailInput,
                        placeholder = "Institute mail id"
                    )

                    PasswordTextField(
                        value = uiState.password,
                        onValueChange = onPasswordInput,
                        placeholder = "Password",
                        passwordVisible = uiState.isPasswordVisible,
                        onPasswordVisibilityToggle = onPasswordVisibilityToggle
                    )

                    PasswordTextField(
                        value = uiState.confirmPassword,
                        onValueChange = onConfirmPasswordInput,
                        placeholder = "Confirm Password",
                        passwordVisible = uiState.isConfirmPasswordVisible,
                        onPasswordVisibilityToggle = onConfirmPasswordVisibilityToggle
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            PrimaryButton(
                text = "Create Account",
                onClick = onSignUpClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.width(110.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                HorizontalDivider(modifier = Modifier.weight(1f), color = dividerAndTextColor, thickness = 1.dp)
                Text(
                    text = "OR",
                    modifier = Modifier.padding(horizontal = 8.dp),
                    color = dividerAndTextColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                HorizontalDivider(modifier = Modifier.weight(1f), color = dividerAndTextColor, thickness = 1.dp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedButton(
                onClick = onGoogleClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, Color.LightGray),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = "Google Logo",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Continue with google",
                    color = Color.Black,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.weight(0.6f))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                Text(
                    text = "Already have an account? ",
                    fontSize = 13.sp,
                    color = dividerAndTextColor
                )
                TertiaryButton(
                    text = "Login",
                    onClick = onLoginClick
                )
            }
        }
    }
}

@Composable
private fun RoleSelectionButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (isSelected) {
        PrimaryButton(text = text, onClick = onClick, modifier = modifier)
    } else {
        SecondaryButton(text = text, onClick = onClick, modifier = modifier)
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
            onPasswordVisibilityToggle = {},
            onConfirmPasswordVisibilityToggle = {},
            onRoleSelected = {},
            onSignUpClick = {},
            onGoogleClick = {},
            onLoginClick = {}
        )
    }
}
