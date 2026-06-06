package com.tpc.nudj.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.tpc.nudj.ui.theme.NudjTheme

@Composable
fun MockTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    leadingIcon: @Composable (()->Unit)? = null,
    placeholder: @Composable (()->Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    singleLine: Boolean = true,
) {
    val shape = RoundedCornerShape(16.dp)
    val borderColor = Color(0xFFFF5E00).copy(alpha = 0.5f)
    val textColor = MaterialTheme.colorScheme.onSurface
    val backgroundColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .background(
                shape = shape,
                color = backgroundColor
            ),
        shape = shape,
        readOnly = readOnly,
        leadingIcon = leadingIcon,
        placeholder = placeholder,
        textStyle = androidx.compose.ui.text.TextStyle(fontSize = 20.sp),
        trailingIcon = trailingIcon,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFFFF5E00),
            unfocusedBorderColor = borderColor,
            unfocusedTextColor = textColor,
            focusedTextColor = textColor
        ),
        singleLine = singleLine
    )
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MockTextFieldPreview() {
    NudjTheme {
        var text by remember { mutableStateOf("Sample Text") }
        MockTextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}