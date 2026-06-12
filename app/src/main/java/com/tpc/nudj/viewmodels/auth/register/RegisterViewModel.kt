package com.tpc.nudj.viewmodels.auth.register

import androidx.lifecycle.ViewModel
import com.tpc.nudj.ui.screen.auth.register.RegisterUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor() : ViewModel() {

    private val _registerUiState = MutableStateFlow(RegisterUiState())
    val registerUiState: StateFlow<RegisterUiState> = _registerUiState.asStateFlow()

    fun onEmailChange(newEmail: String) {
        _registerUiState.update { it.copy(email = newEmail) }
    }

    fun onPasswordChange(newPassword: String) {
        _registerUiState.update { it.copy(password = newPassword) }
    }

    fun onConfirmPasswordChange(newPassword: String) {
        _registerUiState.update { it.copy(confirmPassword = newPassword) }
    }
}