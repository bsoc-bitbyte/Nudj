package com.tpc.nudj.viewmodels.auth.userDetailsInput

import androidx.lifecycle.ViewModel
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

    fun onStudentNameChange(studentName: String) {
        _userDetailsInputUiState.update { it.copy(studentName = studentName) }
    }

    fun onrollNoChange(rollNo: String) {
        _userDetailsInputUiState.update { it.copy(rollNo = rollNo) }
    }

    fun onBatchYearChange(Batch: String) {
        _userDetailsInputUiState.update { it.copy(batchYear = Batch) }
    }

    fun onExpandedStateChange(value: Boolean) {
        _userDetailsInputUiState.update { it.copy(expanded = value) }
    }


    fun onSubmitButtonClick() {

    }

    fun onSkipButtonClick() {}
}
