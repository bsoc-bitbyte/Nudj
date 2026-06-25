package com.tpc.nudj.ui.screen.auth.login

import com.tpc.nudj.model.enums.Role

data class LoginUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? =null,
    val toastMessage:String?=null,
    val email:String = "",
    val password:String = "",
    val passwordVisible: Boolean = false,
    val role: Role = Role.USER
)
