package com.tpc.nudj.utils

enum class FirestoreCollections(val path:String) {
    USERS("users"),
    CLUBS("clubs"),
    EVENTS("events"),
    GENERAL_EVENTS("general_events"),
    RSVP("rsvp"),
    FOLLOWS("follows"),
    REVIEWS("reviews"),
    RATINGS("ratings"),
    QUESTIONNAIRES("questionnaires")
}