package com.tpc.nudj.repository.user

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.tpc.nudj.model.ClubUser
import com.tpc.nudj.model.NormalUser
import com.tpc.nudj.utils.FirestoreCollections
import com.tpc.nudj.utils.FirestoreUtils
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor() : UserRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override suspend fun checkUserTypeAndNavigate(
        onNormalUser: () -> Unit,
        onClubUser: () -> Unit,
        onUserNotFound: () -> Unit
    ) {
        val currentUser: FirebaseUser? = auth.currentUser
        if (currentUser == null) {
            onUserNotFound()
            return
        }

        try {
            if (userExists(currentUser.uid)) {
                onNormalUser()
                return
            }

            if (clubExists(currentUser.uid)) {
                onClubUser()
                return
            }

            onUserNotFound()
        } catch (e: Exception) {
            onUserNotFound()
        }
    }

    override suspend fun saveUser(user: NormalUser): Boolean {
        return try {
            val data = FirestoreUtils.toMap(user)
            firestore.collection(FirestoreCollections.USERS.path).document(user.userid).set(data).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun saveClub(club: ClubUser): Boolean {
        return try {
            val data = FirestoreUtils.toMap(club)
            firestore.collection(FirestoreCollections.CLUBS.path).document(club.clubId).set(data).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun fetchUserById(userId: String): NormalUser? {
        return try {
            val document = firestore.collection(FirestoreCollections.USERS.path).document(userId).get().await()
            if (document.exists()) {
                val data = document.data
                if (data != null) {
                    FirestoreUtils.toNormalUser(data)
                } else null
            } else null
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun fetchCurrentUser(): NormalUser? {
        val currentUser = auth.currentUser ?: return null
        return fetchUserById(currentUser.uid)
    }

    override suspend fun fetchClubById(clubId: String): ClubUser? {
        return try {
            val document = firestore.collection(FirestoreCollections.CLUBS.path).document(clubId).get().await()
            if (document.exists()) {
                val data = document.data
                if (data != null) {
                    FirestoreUtils.toClubUser(data)
                } else null
            } else null
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun fetchCurrentClub(): ClubUser? {
        val currentUser = auth.currentUser ?: return null
        return fetchClubById(currentUser.uid)
    }

    override suspend fun fetchAllClubs(): List<ClubUser> {
        try {
            val querySnapshot = firestore.collection(FirestoreCollections.CLUBS.path)
                .get()
                .await()

            val clubList = querySnapshot.documents.mapNotNull { document->
                document.data?.let { data->
                    FirestoreUtils.toClubUser(data)
                }

            }
            return clubList
        } catch (e: Exception) {
            return emptyList()
        }
    }

    override suspend fun userExists(userId: String): Boolean {
        return try {
            val document = firestore.collection(FirestoreCollections.USERS.path).document(userId).get().await()
            document.exists()
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun clubExists(clubId: String): Boolean {
        return try {
            val document = firestore.collection(FirestoreCollections.CLUBS.path).document(clubId).get().await()
            document.exists()
        } catch (e: Exception) {
            false
        }
    }
}