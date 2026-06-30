package com.tpc.nudj.viewmodels.auth.userDetailsInput

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.tpc.nudj.ui.components.BatchYearDropDownMenu
import com.tpc.nudj.ui.screen.auth.userDetailsInput.UserDetailsInputScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class UserDetailsInputViewModel @Inject constructor() : ViewModel() {
    private val _userDetailsInputUiState = MutableStateFlow(UserDetailsInputScreenUiState())
    val userDetailsInputUiState: StateFlow<UserDetailsInputScreenUiState> =
        _userDetailsInputUiState.asStateFlow()

    fun onStudentNameChage(studentName: String) {
        _userDetailsInputUiState.update { it.copy(studentName = studentName) }
    }

    fun onrollNoChange(rollNo: String) {
        _userDetailsInputUiState.update { it.copy(rollNo = rollNo) }
    }

    fun onBatchYearChange(Batch: String) {
        _userDetailsInputUiState.update { it.copy(batchYear = Batch) }
    }


    fun onSubmitButtonClick() {

    }

    fun onSkipButtonClick() {}
}
