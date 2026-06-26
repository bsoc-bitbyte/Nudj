package com.tpc.nudj.repository.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.tpc.nudj.model.AuthResult
import com.tpc.nudj.model.User
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class FirebaseAuthRepository(
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
) : AuthRepository {

    override fun getCurrentUser(): Flow<User?> = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            val firebaseUser = auth.currentUser
            if (firebaseUser != null) {
                trySend(
                    User(
                        uid = firebaseUser.uid,
                        email = firebaseUser.email ?: "",
                        displayName = firebaseUser.displayName ?: "",
                        isEmailVerified = firebaseUser.isEmailVerified,
                        photoUrl = firebaseUser.photoUrl?.toString() ?: ""
                    )
                )
            } else {
                trySend(null)
            }
        }

        firebaseAuth.addAuthStateListener(authStateListener)
        awaitClose { firebaseAuth.removeAuthStateListener(authStateListener) }
    }

    override fun isUserAuthenticated(): Boolean {
        return firebaseAuth.currentUser != null
    }

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<AuthResult> = flow {
        try {
            emit(AuthResult.Loading)
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user

            if (firebaseUser != null) {
                if (firebaseUser.isEmailVerified) {
                    emit(
                        AuthResult.Success(
                            User(
                                uid = firebaseUser.uid,
                                email = firebaseUser.email ?: "",
                                displayName = firebaseUser.displayName ?: "",
                                isEmailVerified = firebaseUser.isEmailVerified,
                                photoUrl = firebaseUser.photoUrl?.toString() ?: ""
                            )
                        )
                    )
                } else {
                    emit(AuthResult.VerificationNeeded(email))
                }
            } else {
                emit(AuthResult.Error("Authentication failed"))
            }
        } catch (e: Exception) {
            emit(AuthResult.Error(e.message ?: "Authentication failed"))
        }
    }

    override suspend fun signInWithGoogle(idToken: String): Flow<AuthResult> = flow {
        try {
            emit(AuthResult.Loading)
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val result = firebaseAuth.signInWithCredential(credential).await()
            val firebaseUser = result.user

            if (firebaseUser != null) {
                if(firebaseUser.email?.contains("iiitdmj.ac.in") == false) {
                    firebaseAuth.currentUser?.delete()?.await()
                    firebaseAuth.signOut()
                    emit(AuthResult.Error("Use IIITDMJ email addresses only."))
                    return@flow
                }
                emit(
                    AuthResult.Success(
                        User(
                            uid = firebaseUser.uid,
                            email = firebaseUser.email ?: "",
                            displayName = firebaseUser.displayName ?: "",
                            isEmailVerified = firebaseUser.isEmailVerified,
                            photoUrl = firebaseUser.photoUrl?.toString() ?: ""
                        )
                    )
                )
            } else {
                emit(AuthResult.Error("Google authentication failed"))
            }
        } catch (e: Exception) {
            emit(AuthResult.Error(e.message ?: "Google authentication failed"))
        }
    }

    override suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        displayName: String
    ): Flow<AuthResult> = flow {
        try {
            emit(AuthResult.Loading)
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user

            if (firebaseUser != null) {
                // Update display name
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName)
                    .build()

                firebaseUser.updateProfile(profileUpdates).await()

                // Send email verification
                firebaseUser.sendEmailVerification().await()

                emit(AuthResult.VerificationNeeded(email))
            } else {
                emit(AuthResult.Error("User creation failed"))
            }
        } catch (e: Exception) {
            emit(AuthResult.Error(e.message ?: "User creation failed"))
        }
    }

    override suspend fun sendPasswordResetEmail(email: String): Flow<AuthResult> = flow {
        try {
            emit(AuthResult.Loading)
            firebaseAuth.sendPasswordResetEmail(email).await()
            emit(AuthResult.Success(User(email = email)))
        } catch (e: Exception) {
            emit(AuthResult.Error(e.message ?: "Failed to send password reset email"))
        }
    }

    override suspend fun sendEmailVerification(): Flow<AuthResult> = flow {
        try {
            emit(AuthResult.Loading)
            val firebaseUser = firebaseAuth.currentUser

            if (firebaseUser != null) {
                firebaseUser.sendEmailVerification().await()
                emit(AuthResult.VerificationNeeded(firebaseUser.email ?: ""))
            } else {
                emit(AuthResult.Error("No user is signed in"))
            }
        } catch (e: Exception) {
            emit(AuthResult.Error(e.message ?: "Failed to send email verification"))
        }
    }

    override suspend fun signOut() {
        firebaseAuth.signOut()
    }
}