package com.tpc.nudj.viewmodels.auth.clubVerification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.viewModelScope
import com.tpc.nudj.ui.screen.auth.clubVerification.ClubVerificationUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ClubVerificationViewModel @Inject constructor() : ViewModel() {
    private val _clubVerificationUiState = MutableStateFlow(ClubVerificationUiState())
    val clubVerificationUiState:StateFlow<ClubVerificationUiState> =
        _clubVerificationUiState.asStateFlow()

    fun onCheckStatusClick() {
        viewModelScope.launch {
            _clubVerificationUiState.update {
                it.copy(isLoading = true)
            }

            delay(1000)

            _clubVerificationUiState.update {
                it.copy(isLoading = false)
            }
        }
    }
}