package com.tpc.nudj.repository.auth

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.tpc.nudj.R

class GoogleSignInClient(
    private val context: Context
) {
    private val credentialManager = CredentialManager.create(context)

    suspend fun signIn(): String? {
        return try {
            val response = getCredentialResponse()
            return handleSignIn(result = response)
        } catch (e: Exception) {
            Log.i("GoogleSignInClient", "Error during Google sign-in: ${e.message}")
            null
        }
        return null
    }

    private suspend fun getCredentialResponse(): GetCredentialResponse {
        val request = GetCredentialRequest.Builder()
            .addCredentialOption(
                GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.WEB_CLIENT_ID))
                    .setAutoSelectEnabled(false)
                    .build()
            )
            .build()
        return credentialManager.getCredential(request = request, context = context)
    }

    private fun handleSignIn(result: GetCredentialResponse): String? {
        val credential = result.credential
        if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
            val idToken = googleIdTokenCredential.idToken
            return idToken
        }
        return null
    }
}