package com.tpc.nudj.ui.screens.auth.emailVerification


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.tpc.nudj.model.AuthResult
import com.tpc.nudj.model.User
import com.tpc.nudj.repository.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@HiltViewModel
class EmailVerificationViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(EmailVerificationUiState())
    val uiState: StateFlow<EmailVerificationUiState> = _uiState.asStateFlow()



    fun toastMessageShown(){
        _uiState.update { it.copy(message = null) }
    }

    fun checkCurrentUserVerificationStatus(goToLoginScreen: () -> Unit) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        currentUser?.reload()
        if (currentUser?.isEmailVerified == true) {
            goToLoginScreen
        }
    }

    fun sendVerificationEmail() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            authRepository.sendEmailVerification().collect { authResult->
                _uiState.update {
                    it.copy(isLoading = false, message = it.message, canResend = true)
                }
            }
        }
    }
}