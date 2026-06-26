package com.tpc.nudj.viewmodels.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tpc.nudj.model.enums.Role
import com.tpc.nudj.ui.screen.auth.register.RegisterUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
    
    fun onRoleChange(newRole: Role) {
        _registerUiState.update { it.copy(role = newRole) }
    }

    fun onRegisterClick() {

        viewModelScope.launch {
//            _registerUiState.update { it.copy(isLoading = true) }
//            delay(2000)
//            _registerUiState.update { it.copy(isLoading = false) }
        }
    }

    fun onGoogleClick() {}

    fun onPasswordVisibilityToggle(){
        _registerUiState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    fun onConfirmPasswordVisibilityToggle(){
        _registerUiState.update { it.copy(isConfirmPasswordVisible = !it.isConfirmPasswordVisible) }
    }



}
