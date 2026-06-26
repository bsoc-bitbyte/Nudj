package com.tpc.nudj.ui.screen.detailsFetch

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.tpc.nudj.ui.components.LoadingScreenLayout
import com.tpc.nudj.ui.navigation.ScreenRoute

@Composable
fun UserDetailFetchScreen(
    text: String,
    onNormalUser: ()->Unit,
    onClubUser: ()->Unit,
    onUserNotFound:()->Unit,
    onEmailNotVerified:()->Unit,
    viewModel: UserDetailsFetchViewModel = hiltViewModel()
){
    val userType =  viewModel.userTypeState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.checkUserType(
            onEmailNotVerified = {
                onEmailNotVerified()
            }
        )
    }

    when(userType.value){
        is UserDetailsFetchViewModel.UserType.NormalUser -> onNormalUser()
        is UserDetailsFetchViewModel.UserType.ClubUser -> onClubUser()
        is UserDetailsFetchViewModel.UserType.NotFound -> onUserNotFound()
        is UserDetailsFetchViewModel.UserType.Loading -> {
            LoadingScreenLayout(
                text
            )
        }
    }
}