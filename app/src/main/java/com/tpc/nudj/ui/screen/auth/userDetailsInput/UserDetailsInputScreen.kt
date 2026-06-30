package com.tpc.nudj.ui.screen.auth.userDetailsInput


import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tpc.nudj.R
import com.tpc.nudj.ui.components.BatchYearDropDownMenu
import com.tpc.nudj.ui.components.LoadingIndicator
import com.tpc.nudj.ui.components.NudjTextField
import com.tpc.nudj.ui.components.PrimaryButton
import com.tpc.nudj.ui.components.TertiaryButton
import com.tpc.nudj.ui.theme.DarkThemeBackgroundBlue
import com.tpc.nudj.ui.theme.DarkThemeDarkBlue
import com.tpc.nudj.ui.theme.LightThemeBackgroundBlue
import com.tpc.nudj.ui.theme.LightThemeCardLightBlue
import com.tpc.nudj.ui.theme.NudjTheme
import com.tpc.nudj.viewmodels.auth.userDetailsInput.UserDetailsInputViewModel

@Composable
fun UserDetailsInputScreen(
    viewModel: UserDetailsInputViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = if (isSystemInDarkTheme()) DarkThemeBackgroundBlue else LightThemeBackgroundBlue
    ) { paddingValues ->
        val uiState by viewModel.userDetailsInputUiState.collectAsState()
        LoadingIndicator(isLoading = uiState.isLoading) {
            UserDetailsInputScreenLayout(
                uiState = uiState,
                onStudentNameInput = { studentName -> viewModel.onStudentNameChage(studentName) },
                onrollNoInput = { rollNO -> viewModel.onrollNoChange(rollNO) },
                onbatchYearInput = { batchYear -> viewModel.onBatchYearChange(batchYear) },
                onSkipButtonClick = viewModel::onSkipButtonClick,
                onSubmitButtonClick = viewModel::onSubmitButtonClick,
            )
        }
    }
}

@Composable
fun UserDetailsInputScreenLayout(
    uiState: UserDetailsInputScreenUiState,
    onStudentNameInput: (String) -> Unit,
    onrollNoInput: (String) -> Unit,
    onbatchYearInput: (String) -> Unit,
    onSubmitButtonClick: () -> Unit,
    onSkipButtonClick: () -> Unit,
) {
    var isDark = isSystemInDarkTheme()
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(if (isDark) R.drawable.nudj_logo_dark_theme else R.drawable.nudj_logo),
            contentDescription = "nudjLogo",
            modifier = Modifier.size(100.dp)
        )
        Image(
            painter = painterResource(if (isDark) R.drawable.nudj_dark_theme else R.drawable.nudj),
            contentDescription = "Nudj",
            modifier = Modifier
                .height(50.dp)
                .width(100.dp)

        )

        Spacer(modifier = Modifier.height(32.dp))
        Image(
            painter = painterResource(if (isDark) R.drawable.user_info_theme_dark else R.drawable.user_info_theme_light),
            contentDescription = "Userinfo",
            modifier = Modifier
                .height(100.dp)
                .width(150.dp)
        )

        Card(
            elevation = CardDefaults.cardElevation(
                16.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = if (isDark) DarkThemeDarkBlue else LightThemeCardLightBlue
            ),
            modifier = Modifier.padding(horizontal = 10.dp),


            ) {
            NudjTextField(
                value = uiState.studentName,
                onValueChange = onStudentNameInput,
                placeholder = "student name",
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "person"
                    )
                },
                modifier = Modifier.padding(10.dp)
            )
            NudjTextField(
                value = uiState.rollNo,
                onValueChange = onrollNoInput,
                placeholder = "Roll no.",
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.MilitaryTech,
                        contentDescription = "militaryTech"
                    )
                },
                modifier = Modifier.padding(10.dp)
            )

            BatchYearDropDownMenu(
                false,
                onbatchYearInput,
                uiState.batchYear,
                listOf("2023", "2024", "2025", "2026"),
                "Batch Year",
                trailingIcon = Icons.Default.KeyboardArrowDown,
                leadingIcon = Icons.Default.School

            )
        }

        Spacer(modifier = Modifier.weight(1f))
        PrimaryButton(
            text = "Submit",

            onClick = onSubmitButtonClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        TertiaryButton(
            text = "skip",
            onClick = onSkipButtonClick,
            modifier = Modifier
                .align(alignment = Alignment.End)
                .padding(end = 20.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
    }

}


@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun UerDetailsInputScreenLayoutPreview() {
    NudjTheme {
        UserDetailsInputScreen()
    }
}

