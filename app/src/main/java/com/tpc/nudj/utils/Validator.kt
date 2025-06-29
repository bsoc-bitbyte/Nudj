package com.tpc.nudj.utils

object Validator {
    fun isValidEmail(email: String, onSuccess:()->Unit, onFailure:(String)->Unit) {
        val emailSplit = email.split("@")
        if (emailSplit.size < 2 || emailSplit[1].isBlank()) {
            onFailure("Invalid Email")
            return
        }

        else if(emailSplit[1]!="iiitdmj.ac.in") {
            onFailure("Enter institute Email Only.")
            return
        }

        else {
            onSuccess()
            return
        }
    }
}