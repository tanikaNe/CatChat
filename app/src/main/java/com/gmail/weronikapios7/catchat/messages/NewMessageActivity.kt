package com.gmail.weronikapios7.catchat.messages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import catchat.R
import com.gmail.weronikapios7.catchat.adapters.UserAdapter
import com.gmail.weronikapios7.catchat.models.User
import com.gmail.weronikapios7.catchat.utils.LoadingDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.gmail.weronikapios7.catchat.utils.Firebase as FirebaseUtil

class NewMessageActivity : AppCompatActivity() {

    private lateinit var rvAdapter: UserAdapter
    private lateinit var usersList: MutableList<User>
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var firebase: FirebaseUtil



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)
        firebase = FirebaseUtil()
        loadingDialog = LoadingDialog(this)
        supportActionBar?.title = "Select User"
        usersList = mutableListOf()
        fetchUsers()

    }

    private fun fetchUsers(){
        loadingDialog.startLoading()

        firebase.fetchUsers()
            .addOnSuccessListener { docs ->
                for (doc in docs) {
                    if (firebase.getInstance().currentUser?.uid != doc.data["uid"]) {

                        val user = createUser(
                            doc.data["uid"].toString(),
                            doc.data["image"].toString(),
                            doc.data["username"].toString()
                        )

                        createList(user)
                        Log.d("NewMessageActivity", "${doc.id} => ${doc.data}")
                    }
                }
                createAdapter()
                loadingDialog.stopLoading()
            }
            .addOnFailureListener{ e ->
                Log.d("NewMessageActivity", "Error getting documents: ", e)
            }

    }

    private fun createList(user: User){
        usersList.add(user)
    }

    private fun createAdapter(){
        val rvNewMessage: RecyclerView = findViewById(R.id.rvNewMessage)
        rvAdapter = UserAdapter(usersList)
        rvNewMessage.adapter = rvAdapter
    }

    private fun createUser(uid: String, image: String, username: String): User {
        return User(uid, username, image)
    }

}