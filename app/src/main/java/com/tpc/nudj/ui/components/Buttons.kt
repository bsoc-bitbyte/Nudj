package com.tpc.nudj.ui.components
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tpc.nudj.ui.theme.NudjTheme
import com.tpc.nudj.ui.theme.Purple80

// Primary Button
@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isDarkModeEnabled: Boolean
) {
    val isSystemDark = isSystemInDarkTheme()
    val useDarkTheme = isDarkModeEnabled && isSystemDark
    val buttonColor =
        if (useDarkTheme) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary
    val TextColor =
        if (useDarkTheme) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onPrimary
    val disabledBackgroundColor = buttonColor.copy(alpha = 0.6f)
    val disabledTextColor = TextColor.copy(alpha = 0.6f)

    Button(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor,
            contentColor = TextColor,
            disabledContainerColor = disabledBackgroundColor,
            disabledContentColor = disabledTextColor
        ),

        ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )
    }
}


// Secondary Button
@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isDarkModeEnabled: Boolean
) {
    val isSystemDark = isSystemInDarkTheme()
    val useDarkTheme = isDarkModeEnabled && isSystemDark
    val buttonColor =
        if (useDarkTheme) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary
    val disabledTextColor = buttonColor.copy(alpha = 0.6f)


    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier,
        shape = RoundedCornerShape(50),
        border = BorderStroke(
            1.dp,
            buttonColor
        ),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = buttonColor,
            disabledContentColor = disabledTextColor
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

// Tertiary Button
@Composable
fun TertiaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isDarkModeEnabled: Boolean
) {
    val isSystemDark = isSystemInDarkTheme()
    val useDarkTheme = isDarkModeEnabled && isSystemDark

    val textColor =
        if (useDarkTheme) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary
    val disabledTextColor = textColor.copy(alpha = 0.6f)
    TextButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier,
        colors = ButtonDefaults.textButtonColors(
            containerColor = Color.Transparent,
            contentColor = textColor,
            disabledContentColor = disabledTextColor
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(
                textDecoration = TextDecoration.Underline
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLightButton() {
    NudjTheme {
        PrimaryButton(
            text = "Save",
            onClick = {},
            isDarkModeEnabled = false
        )
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewDarkButton() {
    NudjTheme {
        PrimaryButton(
            text = "Save",
            onClick = {},
            isDarkModeEnabled = true
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SecondaryButtonLightPreview() {
    NudjTheme {
        SecondaryButton(
            text = "Edit",
            onClick = {},
            enabled = true,
            isDarkModeEnabled = false
        )
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SecondaryButtonDarkPreview() {
    NudjTheme {
        SecondaryButton(
            text = "Edit",
            onClick = {},
            enabled = true,
            isDarkModeEnabled = true
        )
    }
}

@Preview
@Composable
fun TertiaryButtonLightPreview() {
    NudjTheme {
        Surface {
            TertiaryButton(
                text = "Resend Email",
                onClick = {},
                enabled = true,
                isDarkModeEnabled = false
            )
        }
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun TertiaryButtonDarkPreview() {
    NudjTheme {
        TertiaryButton(
            text = "Resend Email",
            onClick = {},
            enabled = true,
            isDarkModeEnabled = true
        )
    }
}