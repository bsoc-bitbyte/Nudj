package com.tpc.nudj.model


data class NormalUser(
    val userid :String = "",
    val firstName :String = "",
    val lastname :String = "",
    val email :String = "",
    val rollNo: String = "",
    val batch : Int = 2024,
    val profilePictureUrl :String? = null,
    val bio :String = "",
)