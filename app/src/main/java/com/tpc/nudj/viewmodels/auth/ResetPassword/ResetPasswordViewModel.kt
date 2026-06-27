package com.tpc.nudj.viewmodels.auth.ResetPassword

import androidx.lifecycle.ViewModel
import com.tpc.nudj.ui.screen.auth.Reset.ResetPasswordUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor() : ViewModel() {

    private val _resetPasswordUiState = MutableStateFlow(ResetPasswordUiState())
    val resetPasswordUiState: StateFlow<ResetPasswordUiState> = _resetPasswordUiState.asStateFlow()

    fun onPasswordChange(password: String) {
        _resetPasswordUiState.update {
            it.copy(password = password)
        }
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        _resetPasswordUiState.update {
            it.copy(confirmPassword = confirmPassword)
        }
    }

    fun togglePasswordVisibility() {
        _resetPasswordUiState.update {
            it.copy(passwordVisible = !it.passwordVisible)
        }
    }

    fun toggleConfirmPasswordVisibility() {
        _resetPasswordUiState.update {
            it.copy(
                confirmPasswordVisible = !it.confirmPasswordVisible
            )
        }
    }

    fun onSubmitClick() {

    }

}