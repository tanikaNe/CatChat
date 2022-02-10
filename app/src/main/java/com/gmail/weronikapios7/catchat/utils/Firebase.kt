package com.gmail.weronikapios7.catchat.utils

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Firebase {

    fun getInstance(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    fun fetchUsers(): Task<QuerySnapshot> {
        return Firebase.firestore.collection("users").get()
    }






}