package com.tpc.nudj.ui.components // CHANGED: Moved the package to ui.components as requested

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.saveable.rememberSaveable // CHANGED: Imported rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
// CHANGED: Deleted the incorrect 'androidx.lint.kotlin.metadata.Visibility' import here
import com.tpc.nudj.ui.theme.NudjTheme


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
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 4.dp, start = 4.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { if (placeholder.isNotEmpty()) Text(text = placeholder, style = MaterialTheme.typography.bodyLarge) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            visualTransformation = if (isPassword) PasswordVisualTransformation(mask = '*') else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            textStyle = MaterialTheme.typography.bodyLarge,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            )
        )
    }
}


@Composable
fun GeneralTextField(value: String,
                     onValueChange: (String) -> Unit,
                     label: String,
                     placeholder: String = ""){
    NudjTextField(
        value = value,
        onValueChange = onValueChange,
        label = label,
        placeholder = placeholder
    )
}

@Composable
fun EmailTextField(value: String, onValueChange: (String) -> Unit){
    NudjTextField(value = value,
        onValueChange = onValueChange,
        label = "Email",
        keyboardType = KeyboardType.Email)
}

@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    passwordVisible: Boolean,
    onPasswordVisibilityToggle: () -> Unit
) {
    // CHANGED: Completely removed the internal 'remember' state to hoist logic to the parent

    NudjTextField(
        value = value,
        onValueChange = onValueChange,
        label = "Password",
        isPassword = !passwordVisible,
        keyboardType = KeyboardType.Password,
        trailingIcon = {
            val image = if (passwordVisible) {
                Icons.Filled.Visibility
            } else {
                Icons.Filled.VisibilityOff
            }

            // CHANGED: Replaced the local state mutation with the passed lambda function
            IconButton(onClick = onPasswordVisibilityToggle) {
                Icon(imageVector = image, contentDescription = "Toggle password visibility")
            }
        }
    )
}

@Composable
fun TextFieldPreview() {
    // CHANGED: Swapped 'remember' for 'rememberSaveable' for all preview states
    var emailText by rememberSaveable { mutableStateOf("") }
    var passwordText by rememberSaveable { mutableStateOf("") }
    var clubText by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        EmailTextField(value = emailText, onValueChange = { emailText = it })

        // CHANGED: Passed the new state and toggle function
        PasswordTextField(
            value = passwordText,
            onValueChange = { passwordText = it },
            passwordVisible = passwordVisible,
            onPasswordVisibilityToggle = { passwordVisible = !passwordVisible }
        )

        GeneralTextField(value = clubText, onValueChange = { clubText = it }, label = "Club Name")
    }
}


@Preview(name = "Light Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun TextFieldsPreviewLight() {
    NudjTheme {
        Surface(color = MaterialTheme.colorScheme.background) { TextFieldPreview() }
    }
}

@Preview(name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TextFieldsPreviewDark() {
    NudjTheme {
        Surface(color = MaterialTheme.colorScheme.background) { TextFieldPreview() }
    }
}