package com.tpc.nudj

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.tpc.nudj.ui.navigation.Screens
import com.tpc.nudj.ui.screens.auth.detailsInput.DetailsConfirmationScreen
import com.tpc.nudj.ui.screens.auth.detailsInput.DetailsInputScreen
import com.tpc.nudj.ui.screens.auth.emailVerification.EmailVerificationScreen
import com.tpc.nudj.ui.screens.auth.forgotPassword.ForgetPasswordScreen
import com.tpc.nudj.ui.screens.auth.forgotPassword.ResetLinkConfirmationScreen
import com.tpc.nudj.ui.screens.auth.login.LoginScreen
import com.tpc.nudj.ui.screens.auth.signup.SignUpScreen
import com.tpc.nudj.ui.screens.auth.signup.SignUpViewModel
import com.tpc.nudj.ui.screens.dashboard.DashboardScreen
import com.tpc.nudj.ui.theme.NudjTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NudjTheme {
                var backstack = rememberNavBackStack(Screens.SignUpScreen)

                NavDisplay(
                    backStack = backstack,
                    modifier = Modifier.fillMaxSize(),
                    entryDecorators = listOf(
                        rememberSavedStateNavEntryDecorator(),
                        rememberViewModelStoreNavEntryDecorator(),
                    ),
                    entryProvider = entryProvider {
                        entry<Screens.LoginScreen> {
                            LoginScreen(
                                onSignInComplete = {
                                    //TODO: Update logic
                                    backstack.add(Screens.UserDetailsScreen)
                                },
                                onNavigateToForgotPassword = {
                                    backstack.add(Screens.ForgotPasswordScreen)
                                },
                            )
                        }
                        entry<Screens.SignUpScreen> {
                            SignUpScreen(
                                viewModel = hiltViewModel(),
                                onSuccessfulSignIn = {
                                    backstack.add(Screens.DashboardScreen)
                                    backstack.remove(Screens.SignUpScreen)
                                },
                                goToEmailVerificationScreen = {
                                    backstack.add(Screens.EmailVerificationScreen)
                                    backstack.remove(Screens.SignUpScreen)
                                }
                            )
                        }
                        entry<Screens.EmailVerificationScreen> {
                            EmailVerificationScreen(
                                viewModel = hiltViewModel(),
                                goToLoginScreen = {
                                    backstack.add(Screens.LoginScreen)
                                    backstack.remove(Screens.EmailVerificationScreen)
                                }
                            )
                        }
                        entry<Screens.ForgotPasswordScreen> {
                            ForgetPasswordScreen(
                                onNavigateToResetConfirmation = {
                                    backstack.add(Screens.ResetConfirmationScreen)
                                }
                            )
                        }
                        entry<Screens.ResetConfirmationScreen> {
                            ResetLinkConfirmationScreen()
                        }
                        entry<Screens.DashboardScreen> {
                            DashboardScreen()
                        }
                        entry<Screens.UserDetailsScreen> {
                            DetailsInputScreen(onNavigateToConfirmationScreen = {
                                backstack.add(Screens.UserDetailsConfirmationScreen)
                            })
                        }
                        entry<Screens.UserDetailsConfirmationScreen> {
                            DetailsConfirmationScreen()
                        }
                        entry<Screens.ClubDashboardScreen> {
                            // Placeholder for Club Dashboard Screen
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("Club Dashboard Screen", style = MaterialTheme.typography.headlineLarge)
                            }
                        }
                    }
                )
            }
        }
    }
}
