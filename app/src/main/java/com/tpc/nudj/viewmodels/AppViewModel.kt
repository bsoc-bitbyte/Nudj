package com.tpc.nudj.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.tpc.nudj.model.User
import com.tpc.nudj.repository.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Initial)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    val currentUser = authRepository.getCurrentUser()
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            null
        )

    init {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            _authState.value = AuthState.Authenticated(User(user.uid, user.email ?: ""))
        } else {
            _authState.value = AuthState.Unauthenticated
        }
        observeAuthState()
    }

    private fun observeAuthState() {
        viewModelScope.launch {
            authRepository.getCurrentUser().collect { user ->
                _authState.value = if (user != null) {
                    AuthState.Authenticated(user)
                } else {
                    AuthState.Unauthenticated
                }
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            authRepository.signOut()
        }
    }

    sealed class AuthState {
        data object Initial : AuthState()
        data class Authenticated(val user: User) : AuthState()
        data object Unauthenticated : AuthState()
    }
}