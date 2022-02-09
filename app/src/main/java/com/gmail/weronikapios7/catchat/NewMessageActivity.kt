package com.gmail.weronikapios7.catchat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import catchat.R
import catchat.databinding.ActivityJoinBinding.inflate
import com.gmail.weronikapios7.catchat.utils.LoadingDialog
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class NewMessageActivity : AppCompatActivity() {

    private lateinit var rvAdapter: UserAdapter
    private lateinit var usersList: MutableList<UserItem>
    private lateinit var loadingDialog: LoadingDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)
        loadingDialog = LoadingDialog(this)

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

    fun createList(user: UserItem){
        usersList.add(user)
    }

    private fun createAdapter(){
        val rvNewMessages: RecyclerView = findViewById(R.id.rvNewMessage)
        supportActionBar?.title = "Select User"
        rvAdapter = UserAdapter(usersList)
        rvNewMessages.adapter = rvAdapter
    }


}