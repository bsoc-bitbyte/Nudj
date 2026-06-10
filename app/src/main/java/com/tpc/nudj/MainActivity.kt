package com.tpc.nudj

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.tpc.nudj.ui.navigation.ScreenRoute
import com.tpc.nudj.ui.screen.DemoScreen
import com.tpc.nudj.ui.screen.auth.forgotPassword.ForgetPasswordScreen
import com.tpc.nudj.ui.screen.auth.login.LoginScreen
import com.tpc.nudj.ui.screen.auth.login.LoginScreenLayout
import com.tpc.nudj.ui.screen.auth.login.LoginUiState
import com.tpc.nudj.ui.theme.NudjTheme
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NudjTheme {
                val backStack = rememberNavBackStack(ScreenRoute.App.DemoScreen)
                NavDisplay(
                    backStack = backStack,
                    entryProvider = entryProvider {
                        entry<ScreenRoute.App.DemoScreen> {
                            DemoScreen()
                        }
                    }
                )
            }
        }
    }
}