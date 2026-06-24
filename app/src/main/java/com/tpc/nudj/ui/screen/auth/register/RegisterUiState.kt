package com.tpc.nudj.ui.screen.auth.register

enum class Role {
    USER, ADMIN
}

data class RegisterUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isPasswordVisible: Boolean= false,
    val isConfirmPasswordVisible: Boolean = false,
    val isLoading: Boolean = false
    val role: Role = Role.USER
)
