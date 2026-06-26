package com.tpc.nudj.viewmodels.auth.login

import androidx.lifecycle.ViewModel
import com.tpc.nudj.model.enums.Role
import com.tpc.nudj.ui.screen.auth.login.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState: StateFlow<LoginUiState> = _loginUiState.asStateFlow()

    fun onEmailChange(email: String) {
        _loginUiState.update {
            it.copy(email = email)
        }
    }

    fun onPasswordChange(password: String) {
        _loginUiState.update {
            it.copy(password = password)
        }


    }
    fun togglePasswordVisibility() {
        _loginUiState.update {
            it.copy(
                passwordVisible = !it.passwordVisible
            )
        }
    }
    fun onLoginClick() {
        //        viewModelScope.launch {
//            _loginUiState.update { it.copy(isLoading = true) }
//            delay(2000)
//            _loginUiState.update { it.copy(isLoading = false) }
//        }
    }

    fun onForgotPasswordClick() {}

    fun onGoogleClick() {}

    fun onRoleSelected(role: Role) {
        _loginUiState.update {
            it.copy(role = role)
        }
    }

    fun onCreateAccount()  {}

}