package com.tpc.nudj.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.tpc.nudj.ui.theme.LocalAppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NudjDropDownMenu(

    expanded: Boolean = false,
    onSelectedOptionChange: (String) -> Unit,
    selectedOption: String,
    options: List<String>,
    placeholder: String,
    trailingIcon: ImageVector,
    leadingIcon: ImageVector,
    onExpandedStateChange: (Boolean) -> Unit,
) {
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = onExpandedStateChange,
    ) {
        NudjTextField(
            value = selectedOption,
            onValueChange = {},
            placeholder = placeholder,

            trailingIcon = {
                    Icon(
                        imageVector = trailingIcon,
                        contentDescription = "trailingIcon"
                    )
            },
            leadingIcon = {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = "leadingIcon",
                )
            },
            modifier = Modifier
                .padding(10.dp)
                .menuAnchor(
                    type = ExposedDropdownMenuAnchorType.PrimaryNotEditable,
                    enabled = true
                ),
            readOnly = true

        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedStateChange(false) },
            modifier = Modifier
                .background(color = LocalAppColors.current.dropDownMenuColor)
                .padding(12.dp)
                .width(100.dp),
            shape = RoundedCornerShape(15.dp),
        ) {
            options.forEach { option ->
                DropdownMenuItem(

                    text = {
                        Text(
                            option,
                            style = MaterialTheme.typography.bodyLarge,
                            color = LocalAppColors.current.blackTextColor
                        )

                    },

                    onClick = {
                        onSelectedOptionChange(option)
                        onExpandedStateChange(false)
                    },
                )
                HorizontalDivider(color = LocalAppColors.current.surfaceColor)

            }
        }
    }
}





