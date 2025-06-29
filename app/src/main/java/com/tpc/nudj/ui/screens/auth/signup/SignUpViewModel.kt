package com.tpc.nudj.ui.screens.auth.signup

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tpc.nudj.model.AuthResult
import com.tpc.nudj.repository.auth.AuthRepository
import com.tpc.nudj.repository.auth.GoogleSignInClient
import com.tpc.nudj.utils.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.sign

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _signUpUiState = MutableStateFlow(SignUpUiState())
    val signUpUiState: StateFlow<SignUpUiState> = _signUpUiState.asStateFlow()

    fun onEmailChange(email: String) {
        _signUpUiState.value = _signUpUiState.value.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        _signUpUiState.value = _signUpUiState.value.copy(password = password)
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        _signUpUiState.value = _signUpUiState.value.copy(confirmPassword = confirmPassword)
    }

    fun toastMessageShown() {
        _signUpUiState.update {
            it.copy(
                toastMessage = null
            )
        }
    }

    fun onClickRegister(
        goToEmailVerificationScreen: () -> Unit
    ) {
        val email = _signUpUiState.value.email
        val password = _signUpUiState.value.password
        // val displayName = signUpUiState.value.displayName-> Display name seems to be missing while required for AuthRepository.

        val passwordValid: Boolean = password.length >= 6

        if (!passwordValid) {
            _signUpUiState.update {
                it.copy(
                    toastMessage = "Password is too short. It should be at least 6 characters or more."
                )
            }
        }

        Validator.isValidEmail(email, onSuccess = {
            if (passwordValid) {
                _signUpUiState.update {
                    it.copy(
                        isLoading = true
                    )
                }
                viewModelScope.launch {
                    authRepository.createUserWithEmailAndPassword(
                        email = email,
                        password = password,
                        displayName = "New User"
                    ).collect { authResult->
                        _signUpUiState.update {
                            it.copy(
                                isLoading = false,
                                toastMessage = when (authResult) {
                                    is AuthResult.Success -> "Successfully created an account!"
                                    is AuthResult.Error -> authResult.message
                                    else -> null
                                }
                            )
                        }

                        when (authResult) {
                            is AuthResult.VerificationNeeded-> {
                                goToEmailVerificationScreen()
                            }
                            else -> {}
                        }
                    }
                }
            }
        },
            onFailure = { error ->
            _signUpUiState.update {
                    it.copy(
                        toastMessage = error
                    )
                }
            }
        )
    }

    fun onGoogleClicked(context: Context, onSuccessfulSignIn: () -> Unit) {
        _signUpUiState.update { it.copy(isLoading = true) }

        val googleSignInClient = GoogleSignInClient(context)

        viewModelScope.launch {
            val idToken = googleSignInClient.signIn()
            Log.i("LoginViewModel", "Google ID Token: $idToken")
            if (idToken == null) {
                _signUpUiState.update {
                    it.copy(isLoading = false, toastMessage = "Google Sign-In failed")
                }
            } else {
                authRepository.signInWithGoogle(idToken).collect { result ->
                    when (result) {
                        is AuthResult.Success -> {
                            _signUpUiState.update {
                                it.copy(isLoading = false, toastMessage = "Sign in successful")
                            }
                            onSuccessfulSignIn()
                        }

                        is AuthResult.Error -> {
                            _signUpUiState.update {
                                it.copy(isLoading = false, toastMessage = result.message)
                            }
                        }

                        else -> {}
                    }
                }
            }
        }
    }
}