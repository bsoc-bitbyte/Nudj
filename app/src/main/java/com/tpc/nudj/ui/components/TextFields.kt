package com.tpc.nudj.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tpc.nudj.ui.theme.NudjTheme
import com.tpc.nudj.ui.theme.LocalAppColors

@Composable
fun NudjTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {

    val colors = LocalAppColors.current

    Column(modifier = modifier.fillMaxWidth()) {

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder.ifEmpty { label },
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            textStyle = MaterialTheme.typography.bodyLarge,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = colors.textFieldColor,
                unfocusedContainerColor = colors.textFieldColor,
                focusedBorderColor = colors.textFieldBorderColor,
                unfocusedBorderColor = colors.textFieldBorderColor,
            )
        )
    }
}

@Composable
fun EmailTextField(value: String, onValueChange: (String) -> Unit,
                   placeholder: String = "") {
    NudjTextField(
        value = value,
        onValueChange = onValueChange,
        label = "Insititute Mail id",
        keyboardType = KeyboardType.Email,
        placeholder = placeholder,
    )
}

@Composable
fun PasswordTextField(
    value: String,
    label: String = "Password",
    onValueChange: (String) -> Unit,
    passwordVisible: Boolean,
    onPasswordVisibilityToggle: () -> Unit,
    placeholder: String = ""
) {
    NudjTextField(
        value = value,
        onValueChange = onValueChange,
        label = label,
        placeholder = placeholder,
        isPassword = !passwordVisible,
        keyboardType = KeyboardType.Password,
        trailingIcon = {
            val image = if (passwordVisible) {
                Icons.Filled.Visibility
            } else {
                Icons.Filled.VisibilityOff
            }

            IconButton(onClick = onPasswordVisibilityToggle) {
                Icon(imageVector = image, contentDescription = "Toggle password visibility")
            }
        }
    )
}

@Preview(name = "Light Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun NudjTextFieldPreview() {
    var text by rememberSaveable { mutableStateOf("") }
    val colors = LocalAppColors.current

    NudjTheme {
        Surface(color = colors.background) {
            Box(modifier = Modifier.padding(16.dp)) {
                NudjTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = "Club Name"
                )
            }
        }
    }
}

@Preview(name = "Light Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun EmailTextFieldPreview() {
    var emailText by rememberSaveable { mutableStateOf("") }
    val colors = LocalAppColors.current

    NudjTheme {
        Surface(color = colors.background) {
            Box(modifier = Modifier.padding(16.dp)) {
                EmailTextField(value = emailText, onValueChange = { emailText = it })
            }
        }
    }
}

@Preview(name = "Light Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PasswordTextFieldPreview() {
    var passwordText by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    val colors = LocalAppColors.current

    NudjTheme {
        Surface(color = colors.background) {
            Box(modifier = Modifier.padding(16.dp)) {
                PasswordTextField(
                    value = passwordText,
                    onValueChange = { passwordText = it },
                    passwordVisible = passwordVisible,
                    onPasswordVisibilityToggle = { passwordVisible = !passwordVisible }
                )
            }
        }
    }
}