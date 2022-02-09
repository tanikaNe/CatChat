package com.gmail.weronikapios7.catchat.messages

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import catchat.R
import com.gmail.weronikapios7.catchat.adapters.UserAdapter
import com.gmail.weronikapios7.catchat.models.UserItem
import com.gmail.weronikapios7.catchat.utils.LoadingDialog
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class NewMessageActivity : AppCompatActivity() {

    private lateinit var rvAdapter: UserAdapter
    private lateinit var usersList: MutableList<UserItem>
    private lateinit var loadingDialog: LoadingDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)
        loadingDialog = LoadingDialog(this)
        supportActionBar?.title = "Select User"
        usersList = mutableListOf()
        fetchUsers()
    }

    private fun fetchUsers(){
        loadingDialog.startLoading()
        val db = Firebase.firestore
        db.collection("users")
            .get()
            .addOnSuccessListener { docs ->
                for (doc in docs){
                    val uid = doc.data["uid"].toString()
                    val image = doc.data["image"].toString()
                    val username = doc.data["username"].toString()
                    val user = UserItem(uid, username, image)
                    createList(user)
                    Log.d("NewMessageActivity", "${doc.id} => ${doc.data}")
                }
                createAdapter()
                loadingDialog.stopLoading()
            }
            .addOnFailureListener{ e ->
                Log.d("NewMessageActivity", "Error getting documents: ", e)
            }


    }

    private fun createList(user: UserItem){
        usersList.add(user)
    }

    private fun createAdapter(){
        val rvNewMessage: RecyclerView = findViewById(R.id.rvNewMessage)
        rvAdapter = UserAdapter(usersList)
        rvNewMessage.adapter = rvAdapter

    }

}