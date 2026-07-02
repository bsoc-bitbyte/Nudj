package com.tpc.nudj

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.tpc.nudj.ui.navigation.ScreenRoute
import com.tpc.nudj.ui.screen.DemoScreen
import com.tpc.nudj.ui.screen.auth.emailVerification.EmailVerificationScreen
import com.tpc.nudj.ui.screen.auth.emailVerified.EmailVerifiedScreen
import com.tpc.nudj.ui.screen.auth.forgotPassword.ForgetPasswordScreen
import com.tpc.nudj.ui.screen.auth.landing.LandingScreen
import com.tpc.nudj.ui.screen.auth.login.LoginScreen
import com.tpc.nudj.ui.screen.auth.register.RegisterScreen
import com.tpc.nudj.ui.screen.auth.reset.ResetPasswordScreen
import com.tpc.nudj.ui.screen.auth.splash.SplashScreen
import com.tpc.nudj.ui.theme.NudjTheme
import com.tpc.nudj.viewmodels.AppViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NudjTheme {
                val appViewModel: AppViewModel = hiltViewModel()
                val authState by appViewModel.authState.collectAsState()

                val backStack = rememberNavBackStack(ScreenRoute.Auth.SplashScreen)

                LaunchedEffect(authState) {
                    when (val state = authState) {
                        is AppViewModel.AuthState.Initial -> {}
                        is AppViewModel.AuthState.Unauthenticated -> {
                            backStack.clear()
                            backStack.add(ScreenRoute.Auth.SplashScreen)
                        }
                        is AppViewModel.AuthState.EmailNotVerified -> {
                            backStack.clear()
                            backStack.add(ScreenRoute.Auth.EmailVerification)
                        }
                        is AppViewModel.AuthState.Authenticated -> {
                            backStack.clear()
                            when (state.destination) {
                                AppViewModel.Destination.StudentDashboard ->
                                    backStack.add(ScreenRoute.App.StudentDashboard)
                                AppViewModel.Destination.StudentDetailsInput ->
                                    backStack.add(ScreenRoute.App.UserDetailsInput)
                                AppViewModel.Destination.ClubDashboard ->
                                    backStack.add(ScreenRoute.App.ClubDashboard)
                                AppViewModel.Destination.ClubPending ->
                                    backStack.add(ScreenRoute.App.ClubVerificationScreen)
                            }
                        }
                    }
                }
                NavDisplay(
                    backStack = backStack,
                    modifier = Modifier.fillMaxSize(),
                    entryDecorators = listOf(
                        rememberSaveableStateHolderNavEntryDecorator(),
                        rememberViewModelStoreNavEntryDecorator(),
                    ),
                    entryProvider = entryProvider {
                        entry<ScreenRoute.Auth.SplashScreen> {
                            SplashScreen(
                                onSplashCompleted = {
                                    backStack.add(ScreenRoute.Auth.Landing)
                                }
                            )
                        }
                        entry<ScreenRoute.Auth.Landing>{
                            LandingScreen(
                                onLandingScreenClick = {
                                    backStack.add(ScreenRoute.Auth.Login)
                                }
                            )
                        }
                        entry<ScreenRoute.Auth.Login> {
                            LoginScreen(
                                navigateToCreateAccount ={
                                    backStack.add(ScreenRoute.Auth.Register)
                                }
                            )
                        }
                        entry<ScreenRoute.Auth.Register> {
                            RegisterScreen(
                                onNavigateToLogin = {
                                    backStack.add(ScreenRoute.Auth.Login)
                                }
                            )
                        }
                        entry<ScreenRoute.Auth.EmailVerification> {
                            EmailVerificationScreen(
                                onNavigateBack = {}
                            )
                        }
                        entry<ScreenRoute.Auth.ForgotPassword> {
                            ForgetPasswordScreen(
                                onLoginClick = {}
                            )
                        }
                        entry<ScreenRoute.Auth.ResetPassword> {
                            ResetPasswordScreen(
                                onLoginClick = {
                                    backStack.add(ScreenRoute.Auth.Login)
                                }
                            )
                        }
                        entry<ScreenRoute.Auth.EmailVerified> {
                            EmailVerifiedScreen()
                        }
                        entry<ScreenRoute.App.StudentDashboard>{
                            DemoScreen(text = "Student Dashboard")
                        }
                        entry<ScreenRoute.App.ClubDashboard>{
                            DemoScreen(text = "Club Dashboard")
                        }
                        entry<ScreenRoute.App.ClubVerificationScreen>{
                            DemoScreen(text = "Club Verification Dashboard")
                        }
                    }
                )
            }
        }
    }
}