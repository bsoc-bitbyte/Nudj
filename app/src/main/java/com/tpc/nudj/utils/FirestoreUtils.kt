package com.tpc.nudj.utils

import com.google.firebase.Timestamp
import com.tpc.nudj.model.ClubUser
import com.tpc.nudj.model.Event
import com.tpc.nudj.model.EventDate
import com.tpc.nudj.model.EventFAQ
import com.tpc.nudj.model.Follow
import com.tpc.nudj.model.NormalUser
import com.tpc.nudj.model.RSVP
import com.tpc.nudj.model.Review
import com.tpc.nudj.model.enums.Branch
import com.tpc.nudj.model.enums.ClubCategory
import com.tpc.nudj.model.enums.Gender


object FirestoreUtils {

    inline fun <reified T : Enum<T>> enumValueOrDefault(value: String?, defaultValue: T): T {
        return try {
            if (value == null) defaultValue else enumValueOf<T>(value)
        } catch (e: Exception) {
            defaultValue
        }
    }


    fun getEnumDisplayName(enum: Any?): String {
        return when(enum) {
            is Gender -> enum.genderName
            is Branch -> enum.branchName
            is ClubCategory -> enum.categoryName
            else -> enum?.toString() ?: ""
        }
    }

    fun toMap(clubUser: ClubUser): Map<String, Any?> {
        return mapOf(
            "clubId" to clubUser.clubId,
            "clubName" to clubUser.clubName,
            "description" to clubUser.description,
            "achievements" to clubUser.achievementsList,
            "clubEmail" to clubUser.clubEmail,
            "clubLogo" to clubUser.clubLogo,
            "clubCategory" to clubUser.clubCategory.name, // Store enum as string
            "clubCategoryName" to getEnumDisplayName(clubUser.clubCategory), // Store human-readable name
            "additionalDetails" to clubUser.additionalDetails
        )
    }

    fun toMap(normalUser: NormalUser): Map<String, Any?> {
        return mapOf(
            "userid" to normalUser.userid,
            "firstName" to normalUser.firstName,
            "lastname" to normalUser.lastname,
            "email" to normalUser.email,
            "gender" to normalUser.gender.name, // Store enum as string
            "genderName" to getEnumDisplayName(normalUser.gender), // Store human-readable name
            "branch" to normalUser.branch.name, // Store enum as string
            "branchName" to getEnumDisplayName(normalUser.branch), // Store human-readable name
            "batch" to normalUser.batch,
            "profilePictureUrl" to normalUser.profilePictureUrl,
            "bio" to normalUser.bio
        )
    }


    fun toMap(event: Event): Map<String, Any?> {
        return mapOf(
            "eventId" to event.eventId,
            "clubId" to event.clubId,
            "eventName" to event.eventName,
            "eventDescription" to event.eventDescription,
            "eventBannerUrl" to event.eventBannerUrl,
            "organizerName" to event.organizerName,
            "organizerContactNumber" to event.organizerContactNumber,
            "eventDates" to event.eventDates.map { toMap(it) },
            "faqs" to event.faqs.map { toMap(it) },
            "filterBatch" to event.filterBatch,
            "isDeleted" to event.isDeleted,
            "creationTimestamp" to event.creationTimestamp,
            "lastUpdatedTimestamp" to event.lastUpdatedTimestamp
        )
    }

    private fun toMap(eventDate: EventDate): Map<String, Any> {
        return mapOf(
            "startDateTime" to eventDate.startDateTime,
            "estimatedDuration" to eventDate.estimatedDuration
        )
    }

    private fun toMap(faq: EventFAQ): Map<String, Any> {
        return mapOf(
            "question" to faq.question,
            "answer" to faq.answer
        )
    }

    fun toEvent(data: Map<String, Any?>): Event {
        val eventDates = (data["eventDates"] as? List<*>)?.mapNotNull { dateData ->
            if (dateData is Map<*, *>) {
                @Suppress("UNCHECKED_CAST")
                toEventDate(dateData as Map<String, Any?>)
            } else null
        } ?: emptyList()

        val faqs = (data["faqs"] as? List<*>)?.mapNotNull { faqData ->
            if (faqData is Map<*, *>) {
                @Suppress("UNCHECKED_CAST")
                toEventFAQ(faqData as Map<String, Any?>)
            } else null
        } ?: emptyList()

        val filterBatch = (data["filterBatch"] as? List<*>)?.mapNotNull { it as? Int } ?: emptyList()

        return Event(
            eventId = (data["eventId"] as? String) ?: "",
            clubId = (data["clubId"] as? String) ?: "",
            eventName = (data["eventName"] as? String) ?: "",
            eventDescription = (data["eventDescription"] as? String) ?: "",
            eventBannerUrl = data["eventBannerUrl"] as? String,
            organizerName = (data["organizerName"] as? String) ?: "",
            organizerContactNumber = (data["organizerContactNumber"] as? String) ?: "",
            eventDates = eventDates,
            eventVenue = data["eventVenue"] as? String ?: "",
            faqs = faqs,
            filterBatch = filterBatch,
            isDeleted = (data["isDeleted"] as? Boolean) ?: false,
            creationTimestamp = (data["creationTimestamp"] as? Timestamp) ?: Timestamp.now(),
            lastUpdatedTimestamp = (data["lastUpdatedTimestamp"] as? Timestamp) ?: Timestamp.now()
        )
    }

    private fun toEventDate(data: Map<String, Any?>): EventDate {
        return EventDate(
            startDateTime = (data["startDateTime"] as? Timestamp) ?: Timestamp.now(),
            estimatedDuration = (data["estimatedDuration"] as? String) ?: ""
        )
    }

    private fun toEventFAQ(data: Map<String, Any?>): EventFAQ {
        return EventFAQ(
            question = (data["question"] as? String) ?: "",
            answer = (data["answer"] as? String) ?: ""
        )
    }

    fun toClubUser(data: Map<String, Any?>): ClubUser {
        return ClubUser(
            clubId = (data["clubId"] as? String) ?: "",
            clubName = (data["clubName"] as? String) ?: "",
            description = (data["description"] as? String) ?: "",
            achievementsList = (data["achievementsList"] as? List<String>) ?: emptyList(),
            clubEmail = (data["clubEmail"] as? String) ?: "",
            clubLogo = data["clubLogo"] as? String,
            clubCategory = enumValueOrDefault(data["clubCategory"] as? String, ClubCategory.MISCELLANEOUS),
            additionalDetails = (data["additionalDetails"] as? String) ?: ""
        )
    }


    fun toNormalUser(data: Map<String, Any?>): NormalUser {
        return NormalUser(
            userid = (data["userid"] as? String) ?: "",
            firstName = (data["firstName"] as? String) ?: "",
            lastname = (data["lastname"] as? String) ?: "",
            email = (data["email"] as? String) ?: "",
            gender = enumValueOrDefault(data["gender"] as? String, Gender.PREFER_NOT_TO_DISCLOSE),
            branch = enumValueOrDefault(data["branch"] as? String, Branch.CSE),
            batch = (data["batch"] as? Long)?.toInt() ?: 2024,
            profilePictureUrl = data["profilePictureUrl"] as? String,
            bio = (data["bio"] as? String) ?: ""
        )
    }


    // Follow conversion methods
    fun toMap(follow: Follow): Map<String, Any?> {
        return mapOf(
            "followId" to follow.followId,
            "userId" to follow.userId,
            "clubId" to follow.clubId,
            "followTimestamp" to follow.followTimestamp
        )
    }

    fun toFollow(data: Map<String, Any?>): Follow {
        return Follow(
            followId = (data["followId"] as? String) ?: "",
            userId = (data["userId"] as? String) ?: "",
            clubId = (data["clubId"] as? String) ?: "",
            followTimestamp = (data["followTimestamp"] as? Timestamp) ?: Timestamp.now()
        )
    }

    // RSVP conversion methods
    fun toMap(rsvp: RSVP): Map<String, Any?> {
        return mapOf(
            "rsvpId" to rsvp.rsvpId,
            "userId" to rsvp.userId,
            "eventId" to rsvp.eventId,
            "rsvpTimestamp" to rsvp.rsvpTimestamp
        )
    }

    fun toRSVP(data: Map<String, Any?>): RSVP {
        return RSVP(
            rsvpId = (data["rsvpId"] as? String) ?: "",
            userId = (data["userId"] as? String) ?: "",
            eventId = (data["eventId"] as? String) ?: "",
            rsvpTimestamp = (data["rsvpTimestamp"] as? Timestamp) ?: Timestamp.now()
        )
    }


    // Review conversion methods
    fun toMap(review: Review): Map<String, Any?> {
        return mapOf(
            "reviewId" to review.reviewId,
            "userId" to review.userId,
            "clubId" to review.clubId,
            "eventId" to review.eventId,
            "rating" to review.rating,
            "review" to review.review,
            "createdAt" to review.createdAt
        )
    }


    fun toReview(data: Map<String, Any?>): Review {
        return Review(
            reviewId = (data["reviewId"] as? String) ?: "",
            userId = (data["userId"] as? String) ?: "",
            clubId = (data["clubId"] as? String) ?: "",
            eventId = (data["eventId"] as? String) ?: "",
            rating = (data["rating"] as? Long)?.toInt() ?: 0,
            review = data["review"] as? String,
            createdAt = (data["createdAt"] as? Timestamp) ?: Timestamp.now()
        )
    }

}
