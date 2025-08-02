package com.tpc.nudj.repository.rsvp

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.tpc.nudj.model.Event
import com.tpc.nudj.model.RSVP
import com.tpc.nudj.utils.FirestoreCollections
import com.tpc.nudj.utils.FirestoreUtils
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RsvpRepositoryImpl @Inject constructor() : RsvpRepository {

    val firestore = FirebaseFirestore.getInstance()

    override suspend fun rsvpEvent(eventId: String, userId: String): Boolean {
        return try {
            val rsvpId = "${userId}:${eventId}"
            val rsvpWithId = RSVP(
                rsvpId = rsvpId,
                userId = userId,
                eventId = eventId,
                rsvpTimestamp = Timestamp.now()
            )

            firestore.collection(FirestoreCollections.RSVP.path)
                .document(rsvpId)
                .set(FirestoreUtils.toMap(rsvpWithId))
                .await()
            true
        } catch (e: Exception) {
            Log.e("RsvpRepository", "Failed to rsvp an event: ${e.message}")
            false
        }
    }

    override suspend fun checkRsvpStatus(eventId: String, userId: String?): Boolean {
        return try {
            val rsvpId = "${userId}:${eventId}"
            val documentSnapshot = firestore.collection(FirestoreCollections.RSVP.path)
                .document(rsvpId)
                .get()
                .await()
            documentSnapshot.exists()
        } catch (e: Exception) {
            Log.e("RsvpRepository", "Failed to check rsvp status: ${e.message}")
            false
        }
    }

    override suspend fun cancelRsvp(eventId: String, userId: String): Boolean {
        return try {
            val rsvpId = "${userId}:${eventId}"
            firestore.collection(FirestoreCollections.RSVP.path)
                .document(rsvpId)
                .delete()
                .await()
            true
        } catch (e: Exception) {
            Log.e("RsvpRepository", "Failed to cancel rsvp of the event: ${e.message}")
            false
        }
    }

    override suspend fun fetchUpcomingRsvpEvents(userId: String): List<Event> {
        return try {
            val rsvpCollection = firestore.collection(FirestoreCollections.RSVP.path)
                .whereEqualTo("userId", userId)
                .get()
                .await()

            val eventIds = rsvpCollection.documents.mapNotNull {
                it.getString("eventId")
            }

            val rsvpEvents = eventIds.mapNotNull { eventId ->
                val eventSnapshot = firestore.collection(FirestoreCollections.EVENTS.path)
                    .document(eventId)
                    .get()
                    .await()

                val data = eventSnapshot.data
                data?.let { FirestoreUtils.toEvent(it) }
            }.filter { event ->
                event.eventDates.any { it.startDateTime.toDate().after(Timestamp.now().toDate()) }
            }

            rsvpEvents
        } catch (e: Exception) {
            Log.e("RsvpRepository", "Failed to fetch all upcoming rsvp events : ${e.message}")
            emptyList()
        }
    }

    override suspend fun fetchUpcomingRsvpEventsOfFollowedClubs(userId: String): List<Event> {
        return try {

            val followCollection = firestore.collection(FirestoreCollections.FOLLOWS.path)
                .whereEqualTo("userId", userId)
                .get()
                .await()

            val followedClubs = followCollection.documents.mapNotNull {
                it.getString("clubId")
            }

            val rsvpCollection = firestore.collection(FirestoreCollections.RSVP.path)
                .whereEqualTo("userId", userId)
                .get()
                .await()

            val eventIds = rsvpCollection.documents.mapNotNull {
                it.getString("eventId")
            }

            val result = eventIds.mapNotNull { eventId ->
                val eventSnapshot = firestore.collection(FirestoreCollections.EVENTS.path)
                    .document(eventId)
                    .get()
                    .await()

                val data = eventSnapshot.data
                data?.let { FirestoreUtils.toEvent(it) }
            }.filter { event ->
                followedClubs.contains(event.clubId) && event.eventDates.any {
                    it.startDateTime.toDate().after(Timestamp.now().toDate())
                }
            }

            result
        } catch (e: Exception) {
            Log.e(
                "RsvpRepository",
                "Failed to fetch all upcoming events that user follows : ${e.message}"
            )
            emptyList()
        }
    }
}