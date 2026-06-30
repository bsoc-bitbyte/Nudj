package com.tpc.nudj.ui.components

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ValueElement
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tpc.nudj.ui.theme.AppColors
import com.tpc.nudj.ui.theme.DarkThemeBackgroundBlue
import com.tpc.nudj.ui.theme.LightThemeCardLightBlue
import kotlin.math.exp

@Composable
fun BatchYearDropDownMenu(

    expanded: Boolean = false,
    onValueChange: (String) -> Unit,
    batchYear: String,
    options: List<String>,
    placeholder: String,
    trailingIcon: ImageVector,

    leadingIcon: ImageVector,

    ) {
    var selectedOption by remember {
        mutableStateOf(batchYear)
    }

    var expanded by remember {
        mutableStateOf(expanded)
    }


    NudjTextField(
        value = selectedOption,
        onValueChange = onValueChange,
        placeholder = placeholder,
        trailingIcon = {
            IconButton(
                onClick = { expanded = !expanded }

            ) {
                Icon(
                    imageVector = trailingIcon,
                    contentDescription = "trailingIcon"
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .background(color = LightThemeCardLightBlue)
                        .padding(10.dp),
                    shape = RoundedCornerShape(15.dp),
                ) {
                    options.forEach { option ->
                        DropdownMenuItem(
                            text = {
                                Text(option)
                            },

                            onClick = { selectedOption = option }
                        )
                        HorizontalDivider(color = DarkThemeBackgroundBlue)

                    }


                }
            }

        },
        leadingIcon = {
            Icon(
                imageVector = leadingIcon,
                contentDescription = "leadingIcon",
            )
        },
        modifier = Modifier.padding(10.dp)

    )


}





