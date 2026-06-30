package com.tpc.nudj.viewmodels.club.clubLanding

import androidx.lifecycle.ViewModel
import com.tpc.nudj.ui.screen.club.clubLanding.ClubLandingUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ClubLandingViewModel @Inject constructor(): ViewModel(){
    private val _clubLandingUiState = MutableStateFlow(ClubLandingUiState())
    val clubLandingUiState: StateFlow<ClubLandingUiState> = _clubLandingUiState.asStateFlow()
    fun createClubEvents(){}
    fun onClickSkip(){}
}
