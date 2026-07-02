package com.tpc.nudj.ui.screen.auth.userDetailsInput

data class UserDetailsInputScreenUiState(
    val studentName: String = "",
    val rollNo: String = "",
    val batchYear: String = "",
    val expanded: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val toastMessage: String? = null,
    val batchYearList: List<String> = listOf("2023", "2024", "2025", "2026")
)




