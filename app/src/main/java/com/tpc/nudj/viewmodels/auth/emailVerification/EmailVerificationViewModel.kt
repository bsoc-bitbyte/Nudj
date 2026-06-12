package com.tpc.nudj.viewmodels.auth.emailVerification

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import com.tpc.nudj.ui.screen.auth.emailVerification.EmailVerificationUiState

@HiltViewModel
class EmailVerificationViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(EmailVerificationUiState())
    val uiState: StateFlow<EmailVerificationUiState> = _uiState.asStateFlow()

    fun onCheckInboxClick() {}
    fun onResendEmailClick() {}

}