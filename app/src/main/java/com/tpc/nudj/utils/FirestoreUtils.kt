package com.tpc.nudj.utils

import com.tpc.nudj.model.ClubUser
import com.tpc.nudj.model.NormalUser
import com.tpc.nudj.model.enums.ClubCategory

object FirestoreUtils{

    inline fun <reified T : Enum<T>> enumValueOrDefault(value: String?, defaultValue: T): T {
        return try {
            if (value == null) defaultValue else enumValueOf<T>(value)
        } catch (e: Exception) {
            defaultValue
        }
    }

    fun getEnumDisplayName(enum: Any?): String {
        return when(enum) {
            is ClubCategory -> enum.categoryName
            else -> enum?.toString() ?: ""
        }
    }


    fun toMap(clubUser: ClubUser): Map<String, Any?> {
        return mapOf(
            "clubId" to clubUser.clubId,
            "clubName" to clubUser.clubName,
            "description" to clubUser.description,
            "achievementsList" to clubUser.achievementsList,
            "clubEmail" to clubUser.clubEmail,
            "clubLogo" to clubUser.clubLogo,
            "clubCategory" to clubUser.clubCategory.name,
            "clubCategoryName" to getEnumDisplayName(clubUser.clubCategory),
            "additionalDetails" to clubUser.additionalDetails
        )
    }

    fun toClubUser(data: Map<String,Any?>) : ClubUser{
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

    fun toMap(normalUser: NormalUser): Map<String, Any?> {
        return mapOf(
            "userid" to normalUser.userid,
            "firstName" to normalUser.firstName,
            "lastname" to normalUser.lastname,
            "email" to normalUser.email,
            "rollNo" to normalUser.rollNo,
            "batch" to normalUser.batch,
            "profilePictureUrl" to normalUser.profilePictureUrl,
            "bio" to normalUser.bio
        )
    }

    fun toNormalUser(data: Map<String, Any?>): NormalUser {
        return NormalUser(
            userid = (data["userid"] as? String) ?: "",
            firstName = (data["firstName"] as? String) ?: "",
            lastname = (data["lastname"] as? String) ?: "",
            email = (data["email"] as? String) ?: "",
            rollNo = (data["rollNo"] as? String) ?: "",
            batch = (data["batch"] as? Long)?.toInt() ?: 2024,
            profilePictureUrl = data["profilePictureUrl"] as? String,
            bio = (data["bio"] as? String) ?: ""
        )
    }
}