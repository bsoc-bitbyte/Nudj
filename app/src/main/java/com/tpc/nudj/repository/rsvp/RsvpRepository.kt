package com.tpc.nudj.repository.rsvp

import com.tpc.nudj.model.Event
import com.tpc.nudj.model.RSVP

interface RsvpRepository {

    suspend fun rsvpEvent(eventId: String, userId: String): Boolean

    suspend fun cancelRsvp(eventId: String, userId: String): Boolean

    suspend fun checkRsvpStatus(eventId: String, userId: String?): Boolean

    suspend fun fetchUpcomingRsvpEvents(userId: String): List<Event>

    suspend fun fetchUpcomingRsvpEventsOfFollowedClubs(userId: String): List<Event>
}