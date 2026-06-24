package com.tpc.nudj.ui.screen.auth.emailVerification

data class EmailVerificationUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val timerInSeconds: Int = 30,
    val isResendEnabled: Boolean = true
) {
    val formattedTime: String
        get() {
            val minutes = (timerInSeconds / 60).toString().padStart(2, '0')
            val seconds = (timerInSeconds % 60).toString().padStart(2, '0')
            return "$minutes:$seconds"
        }
}
