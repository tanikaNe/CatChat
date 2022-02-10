package com.gmail.weronikapios7.catchat.utils

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class FirebaseUtil {

    fun getAuthInstance(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    fun getStorageInstance(path: String): StorageReference {
        return FirebaseStorage.getInstance().getReference(path)
    }

    fun getCollection(collectionPath: String): CollectionReference {
        return getFirestore().collection(collectionPath)
    }

    fun createUser(uid: String, username: String, filepath: String): HashMap<String, String> {
        return hashMapOf(
            "uid" to uid,
            "username" to username,
            "image" to filepath
        )
    }

    fun createMessage(message: String, uid: String, receiverId: String, timestamp: String): HashMap<String, String> {
        return hashMapOf(
            "message" to message,
            "uid" to uid,
            "receiverId" to receiverId,
            "timestamp" to timestamp
        )
    }

    private fun getFirestore(): FirebaseFirestore {
        return Firebase.firestore
    }







}