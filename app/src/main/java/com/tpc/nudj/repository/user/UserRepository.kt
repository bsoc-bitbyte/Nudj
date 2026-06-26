package com.tpc.nudj.repository.user

import com.tpc.nudj.model.ClubUser
import com.tpc.nudj.model.NormalUser

interface UserRepository {
    suspend fun checkUserTypeAndNavigate(
        onNormalUser: () -> Unit,
        onClubUser: () -> Unit,
        onUserNotFound: () -> Unit
    )

    suspend fun saveUser(user: NormalUser): Boolean

    suspend fun saveClub(club: ClubUser): Boolean

    suspend fun fetchUserById(userId: String): NormalUser?

    suspend fun fetchCurrentUser(): NormalUser?

    suspend fun fetchClubById(clubId: String): ClubUser?

    suspend fun fetchAllClubs(): List<ClubUser>

    suspend fun fetchCurrentClub(): ClubUser?

    suspend fun userExists(userId: String): Boolean

    suspend fun clubExists(clubId: String): Boolean
}