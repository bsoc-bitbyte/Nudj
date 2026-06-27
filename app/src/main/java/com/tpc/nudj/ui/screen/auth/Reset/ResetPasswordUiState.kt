package com.tpc.nudj.ui.screen.auth.Reset

data class ResetPasswordUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val toastMessage: String? = null,
    val password: String = "",
    val confirmPassword: String = "",
    val passwordVisible: Boolean = false,
    val confirmPasswordVisible: Boolean = false
)