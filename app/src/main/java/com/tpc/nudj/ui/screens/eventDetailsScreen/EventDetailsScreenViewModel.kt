package com.tpc.nudj.ui.screens.eventDetailsScreen

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.tpc.nudj.model.RSVP
import com.tpc.nudj.repository.events.EventsRepository
import com.tpc.nudj.repository.rsvp.RsvpRepository
import com.tpc.nudj.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.tpc.nudj.model.Event
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class EventDetailsScreenViewModel @Inject constructor(
    private val eventsRepository: EventsRepository,
    private val rsvpRepository: RsvpRepository
): ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    private val firebaseAuth = FirebaseAuth.getInstance()
    val currentUser = firebaseAuth.currentUser

    private val _isRsvped = MutableStateFlow<Boolean>(false)
    val isRsvped: StateFlow<Boolean> = _isRsvped.asStateFlow()
    private val _event = MutableStateFlow<Event?>(null)
    val event: StateFlow<Event?> = _event.asStateFlow()
    fun isRsvped(eventId: String) {
        viewModelScope.launch {
            _isRsvped.value = rsvpRepository.checkRsvpStatus(eventId, currentUser!!.uid)
        }
    }

    fun fetchEvent(eventId: String) {
        _isLoading.value = true
        viewModelScope.launch{
            try{
                _event.value = eventsRepository.fetchEventByID(eventId)!!
            } catch (e: Exception) {
                null
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun onClickRsvp(eventId: String) {
        viewModelScope.launch {
            rsvpRepository.rsvpEvent(eventId, currentUser!!.uid)
        }
        _isRsvped.value = true
    }

    fun onClickUnRsvp(eventId: String) {
        viewModelScope.launch {
            rsvpRepository.cancelRsvp(eventId, currentUser!!.uid)
        }
        _isRsvped.value = false
    }
}