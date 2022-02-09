package com.gmail.weronikapios7.catchat.messages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import catchat.R
import com.gmail.weronikapios7.catchat.adapters.UserAdapter
import com.gmail.weronikapios7.catchat.models.UserItem
import com.google.firebase.firestore.auth.User

class ChatLogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)
        val user = intent.getParcelableExtra<UserItem>("USER")
        supportActionBar?.title = user?.username
    }
}