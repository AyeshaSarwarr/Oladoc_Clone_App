package com.example.oladoc

import com.example.oladoc.database.UserProfile
import com.google.firebase.firestore.FirebaseFirestore

class DataRepository {
    private val db = FirebaseFirestore.getInstance()

    fun addProfile(profile: UserProfile) {
        db.collection("profiles").add(profile)
            .addOnSuccessListener { documentReference ->
                // Handle successful addition of the profile
            }
            .addOnFailureListener { e ->
                // Handle possible errors
            }
    }

    fun getProfiles() {
        db.collection("profiles").get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    // Handle each document, e.g., convert to UserProfile object
                }
            }
            .addOnFailureListener {
                // Handle possible errors
            }
    }
}
