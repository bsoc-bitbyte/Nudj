package com.tpc.nudj.viewmodels.auth.forgotPassword

import androidx.lifecycle.ViewModel
import com.tpc.nudj.ui.screen.auth.forgotPassword.ForgotPasswordUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor() : ViewModel() {

    private val _forgotPasswordUiState = MutableStateFlow(ForgotPasswordUiState())

    val forgotPasswordUiState: StateFlow<ForgotPasswordUiState> = _forgotPasswordUiState.asStateFlow()

    fun onEmailChange(email: String) {
        _forgotPasswordUiState.update {
            it.copy(email = email)
        }
    }
    fun onSendEmailClick(){

    }
}