package com.tpc.nudj.ui.screen.auth.forgotPassword

data class ForgotPasswordUiState (
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val toastMessage: String? = null,
    val email:String = ""

)
