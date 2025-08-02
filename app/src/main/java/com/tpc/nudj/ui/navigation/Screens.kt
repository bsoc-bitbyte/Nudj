package com.tpc.nudj.ui.navigation

import androidx.navigation3.runtime.NavKey
import com.tpc.nudj.model.enums.Branch
import kotlinx.serialization.Serializable

sealed interface Screens: NavKey {
    sealed interface Auth : Screens {

        @Serializable
        data object PreHomeScreen : Auth

        @Serializable
        data object LandingScreen : Auth

        @Serializable
        data object LoginScreen : Auth

        @Serializable
        data object SignUpScreen : Auth

        @Serializable
        data object EmailVerificationScreen : Auth

        @Serializable
        data object ForgotPasswordScreen : Auth


    }

    @Serializable
    data class EventDetailsScreen(
        val eventId: String
    ) : Screens

    @Serializable
    data object UserDetailsScreen : Screens

    @Serializable
    data class UserDetailsConfirmationScreen(
        val firstName: String,
        val lastName: String,
        val branch: Branch,
        val batch: Int,
        val gender: String
    ) : Screens

    @Serializable
    data object DashboardScreen : Screens

    @Serializable
    data object UserDetailsFetchLoadingScreen : Screens

    //Clubs
    @Serializable
    data object ClubRegistrationScreen : Screens

    @Serializable
    data object ClubDashboardScreen : Screens

    @Serializable
    data object MyClubsScreen : Screens

    @Serializable
    data object EventRegistrationScreen :Screens

}