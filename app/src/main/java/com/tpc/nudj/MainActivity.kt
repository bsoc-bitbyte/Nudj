package com.tpc.nudj

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.tpc.nudj.ui.navigation.Screens
import com.tpc.nudj.ui.screens.auth.PreHomeScreen.PreHomeScreen
import com.tpc.nudj.ui.screens.auth.clubRegistration.ClubRegistrationScreen
import com.tpc.nudj.ui.screens.auth.detailsInput.DetailsConfirmationScreen
import com.tpc.nudj.ui.screens.auth.detailsInput.DetailsInputScreen
import com.tpc.nudj.ui.screens.auth.emailVerification.EmailVerificationScreen
import com.tpc.nudj.ui.screens.auth.forgotPassword.ForgetPasswordScreen
import com.tpc.nudj.ui.screens.auth.landing.LandingScreen
import com.tpc.nudj.ui.screens.auth.login.LoginScreen
import com.tpc.nudj.ui.screens.auth.signup.SignUpScreen
import com.tpc.nudj.ui.screens.clubDashboard.ClubDashboardScreen
import com.tpc.nudj.ui.screens.dashboard.DashboardScreen
import com.tpc.nudj.ui.screens.detailsFetch.UserDetailsFetchScreen
import com.tpc.nudj.ui.screens.eventDetailsScreen.EventDetailsScreen
import com.tpc.nudj.ui.screens.eventRegistration.EventRegistrationScreen
import com.tpc.nudj.ui.screens.myClubs.MyClubs
import com.tpc.nudj.ui.theme.NudjTheme
import com.tpc.nudj.ui.viewmodels.AppViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val latestIntent = mutableStateOf<Intent?>(null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        latestIntent.value = intent
        enableEdgeToEdge()
        setContent {
            NudjTheme {
                val appViewModel: AppViewModel = hiltViewModel()
                val authState by appViewModel.authState.collectAsState()
                val backstack = rememberNavBackStack(
                    when (authState) {
                        is AppViewModel.AuthState.Authenticated -> Screens.UserDetailsFetchLoadingScreen
                        else -> Screens.Auth.LandingScreen
                    }
                )
                val currentIntent = latestIntent.value
                LaunchedEffect(currentIntent) {
                    val data = currentIntent?.data
                    val mode = data?.getQueryParameter("mode")
                    val oobCode = data?.getQueryParameter("oobCode")

                    if (!mode.isNullOrEmpty() && !oobCode.isNullOrEmpty()) {
                        EmailVerificationCredentials.mode = mode
                        EmailVerificationCredentials.oobCode = oobCode
                    }
                }


                LaunchedEffect(authState) {
                    when (authState) {
                        is AppViewModel.AuthState.Unauthenticated -> {
                            if (backstack.isNotEmpty() &&
                                backstack.last() !is Screens.Auth
                            ) {
                                backstack.add(Screens.Auth.LandingScreen)
                                backstack.removeIf { it !is Screens.Auth.LandingScreen }
                            }
                        }

                        else -> {}
                    }
                }

                NavDisplay(
                    backStack = backstack,
                    modifier = Modifier.fillMaxSize(),
                    entryDecorators = listOf(
                        rememberSavedStateNavEntryDecorator(),
                        rememberViewModelStoreNavEntryDecorator(),
                    ),
                    entryProvider = entryProvider {
                        entry<Screens.Auth.PreHomeScreen> {
                            PreHomeScreen(
                                onCompleted = {
                                    backstack.add(Screens.DashboardScreen)
                                    backstack.remove(Screens.Auth.PreHomeScreen)
                                }
                            )
                        }
                        entry<Screens.Auth.LandingScreen> {
                            LandingScreen(
                                onClickLogin = {
                                    backstack.add(Screens.Auth.LoginScreen)
                                },
                                onClickSignup = {
                                    backstack.add(Screens.Auth.SignUpScreen)
                                }
                            )
                        }
                        entry<Screens.UserDetailsFetchLoadingScreen> {
                            UserDetailsFetchScreen(
                                text = "Hang in there!",
                                onNormalUser = {
                                    backstack.add(Screens.DashboardScreen)
                                    backstack.remove(Screens.UserDetailsFetchLoadingScreen)
                                },
                                onClubUser = {
                                    backstack.add(Screens.ClubDashboardScreen)
                                    backstack.remove(Screens.UserDetailsFetchLoadingScreen)
                                },
                                onUserNotFound = {
                                    backstack.add(Screens.UserDetailsScreen)
                                    backstack.remove(Screens.UserDetailsFetchLoadingScreen)
                                },
                                onEmailNotVerified = {
                                    backstack.clear()
                                    backstack.add(Screens.Auth.EmailVerificationScreen)
                                }
                            )
                        }
                        entry<Screens.Auth.LoginScreen> {
                            LoginScreen(
                                onNavigateToUserDetailsFetchLoadingScreen = {
                                    backstack.add(Screens.UserDetailsFetchLoadingScreen)
                                    backstack.remove(Screens.Auth.LoginScreen)
                                },
                                onNavigateToForgotPassword = {
                                    backstack.add(Screens.Auth.ForgotPasswordScreen)
                                },
                                onNavigateToEmailVerification = {
                                    backstack.add(Screens.Auth.EmailVerificationScreen)
                                }
                            )
                        }
                        entry<Screens.Auth.SignUpScreen> {
                            SignUpScreen(
                                viewModel = hiltViewModel(),
                                onSuccessfulSignIn = {
                                    backstack.add(Screens.DashboardScreen)
                                    backstack.remove(Screens.Auth.SignUpScreen)
                                },
                                goToEmailVerificationScreen = {
                                    backstack.add(Screens.Auth.EmailVerificationScreen)
                                    backstack.remove(Screens.Auth.SignUpScreen)
                                }
                            )
                        }
                        entry<Screens.Auth.EmailVerificationScreen> {
                            EmailVerificationScreen(
                                viewModel = hiltViewModel(),
                                toDetailsFetchScreen = {
                                    backstack.add(Screens.UserDetailsFetchLoadingScreen)
                                    backstack.remove(Screens.Auth.EmailVerificationScreen)
                                },
                                toResetPasswordScreen = {
                                    // Reset Password Screen will come here
                                }
                            )
                        }
                        entry<Screens.Auth.ForgotPasswordScreen> {
                            ForgetPasswordScreen(
                                onNavigateToVerifyEmail = {
                                    backstack.add(Screens.Auth.EmailVerificationScreen)
                                }
                            )
                        }
                        entry<Screens.DashboardScreen> {
                            DashboardScreen(
                                onNavigateToMyClubs = {
                                    backstack.add(Screens.MyClubsScreen)
                                }
                            )
                        }
                        entry<Screens.UserDetailsScreen> {
                            DetailsInputScreen(
                                onNavigateToConfirmationScreen = { confirmationScreen ->
                                    backstack.add(confirmationScreen)
                                },
                                onNavigateToClubRegistration = {
                                    backstack.add(Screens.ClubRegistrationScreen)
                                }
                            )
                        }
                        entry<Screens.UserDetailsConfirmationScreen> { entry ->
                            val navArgs = entry
                            DetailsConfirmationScreen(
                                navArgs = navArgs,
                                onEditClick = {
                                    if (backstack.contains(Screens.UserDetailsScreen)) {
                                        backstack.removeIf { it is Screens.UserDetailsConfirmationScreen }
                                    }
                                },
                                onSaveSuccess = {
                                    backstack.add(Screens.Auth.PreHomeScreen)
                                    backstack.removeIf { it is Screens.UserDetailsConfirmationScreen }
                                    backstack.remove(Screens.UserDetailsScreen)
                                }
                            )
                        }
                        entry<Screens.ClubRegistrationScreen> {
                            ClubRegistrationScreen(
                                toDashboardScreen = {
                                    backstack.add(Screens.ClubDashboardScreen)
                                },
                                onBackClicked = {
                                    backstack.removeLastOrNull()
                                }
                            )
                        }
                        entry<Screens.ClubDashboardScreen> {
                            // Placeholder for Club Dashboard Screen
                            ClubDashboardScreen(
                                onCreateEventClicked = {
                                    backstack += Screens.EventRegistrationScreen
                                }
                            )
                        }
                        entry<Screens.MyClubsScreen> {
                            MyClubs(
                                onBackClicked = { backstack.removeLastOrNull() }
                            )
                        }

                        entry<Screens.EventRegistrationScreen> {
                            EventRegistrationScreen(
                                onBackClicked = {
                                    backstack.removeLastOrNull()
                                }
                            )
                        }
                    }
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        latestIntent.value = intent
    }
}


object EmailVerificationCredentials {
    var mode: String = ""
    var oobCode: String = ""
}