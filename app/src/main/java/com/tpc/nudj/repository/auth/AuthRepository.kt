package com.tpc.nudj.repository.auth

import com.tpc.nudj.model.AuthResult
import com.tpc.nudj.model.User
import kotlinx.coroutines.flow.Flow

/**
 * Interface defining authentication operations for the application.
 */
interface AuthRepository {
    /**
     * Gets the current authenticated user as a flow.
     * @return Flow of User object or null if not authenticated
     */
    fun getCurrentUser(): Flow<User?>

    /**
     * Checks if the user is authenticated.
     * @return true if user is authenticated, false otherwise
     */
    fun isUserAuthenticated(): Boolean

    /**
     * Signs in a user with email and password.
     * @param email User's email
     * @param password User's password
     * @return Flow of AuthResult representing the operation result
     */
    suspend fun signInWithEmailAndPassword(email: String, password: String): Flow<AuthResult>

    /**
     * Signs in a user with Google.
     * @param idToken Google ID token
     * @return Flow of AuthResult representing the operation result
     */
    suspend fun signInWithGoogle(idToken: String): Flow<AuthResult>

    /**
     * Creates a new user account with email and password.
     * @param email User's email
     * @param password User's password
     * @param displayName User's display name
     * @return Flow of AuthResult representing the operation result
     */
    suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        displayName: String
    ): Flow<AuthResult>

    /**
     * Sends a password reset email to the specified email address.
     * @param email Email address to send the password reset link to
     * @return Flow of AuthResult representing the operation result
     */
    suspend fun sendPasswordResetEmail(email: String): Flow<AuthResult>

    /**
     * Sends an email verification to the current user.
     * @return Flow of AuthResult representing the operation result
     */
    suspend fun sendEmailVerification(): Flow<AuthResult>

    /**
     * Signs out the current user.
     */
    suspend fun signOut()
}