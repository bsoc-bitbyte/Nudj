package com.tpc.nudj.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tpc.nudj.ui.theme.NudjTheme
import com.tpc.nudj.ui.theme.LocalAppColors

// Primary Button
@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    hasBorder: Boolean = false,
) {
    val shape = RoundedCornerShape(8.dp)
    val contentPadding = if (hasBorder) {
        PaddingValues(horizontal = 24.dp, vertical = 10.dp)
    } else {
        ButtonDefaults.ContentPadding
    }

    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        contentPadding = contentPadding,
        shape = shape,
        border = if (hasBorder) BorderStroke(1.5.dp, LocalAppColors.current.buttonBorderColor)
        else null,
        colors = ButtonDefaults.buttonColors(
            containerColor = LocalAppColors.current.primaryButtonColor,
            contentColor = LocalAppColors.current.primaryButtonTextColor,
            disabledContainerColor = LocalAppColors.current.primaryButtonColor.copy(alpha = 0.6f),
            disabledContentColor = LocalAppColors.current.primaryButtonTextColor.copy(alpha = 0.6f)
        ),

        ) {
        Text(
            text = text, style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center
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
) {
    val contentPadding = PaddingValues(horizontal = 24.dp, vertical = 10.dp)
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier,
        shape = RoundedCornerShape(size = 8.dp),
        border = BorderStroke(1.5.dp, LocalAppColors.current.buttonBorderColor),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = LocalAppColors.current.secondaryButtonColor,
            contentColor = LocalAppColors.current.secondaryButtonTextColor,
            disabledContentColor = LocalAppColors.current.secondaryButtonTextColor.copy(alpha = 0.6f)
        ),
        contentPadding = contentPadding,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
        )
    }
}

// Tertiary Button
@Composable
fun TertiaryButton(
    text: String, onClick: () -> Unit, modifier: Modifier = Modifier, enabled: Boolean = true
) {
    TextButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier,
        colors = ButtonDefaults.textButtonColors(
            containerColor = Color.Transparent,
            contentColor = LocalAppColors.current.tertiaryButtonColor,
            disabledContentColor = LocalAppColors.current.tertiaryButtonColor.copy(alpha = 0.6f)
        )
    ) {
        Text(
            text = text, style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Medium,
                fontStyle = FontStyle.Italic,
                textDecoration = TextDecoration.Underline
            ), textAlign = TextAlign.Center, modifier = Modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PrimaryButtonUnborderedPreview() {
    NudjTheme {
        PrimaryButton(
            text = "Login", onClick = {}, hasBorder = false, modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PrimaryButtonPreview() {
    NudjTheme {
        PrimaryButton(
            text = "Student", onClick = {}, hasBorder = true
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SecondaryButtonPreview() {
    NudjTheme {
        SecondaryButton(
            text = "Edit", onClick = {})
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TertiaryButtonPreview() {
    NudjTheme {
        TertiaryButton(
            text = "Resend Email", onClick = {})
    }
}
