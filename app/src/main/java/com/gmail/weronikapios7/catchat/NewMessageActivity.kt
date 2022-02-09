package com.gmail.weronikapios7.catchat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import catchat.R
import catchat.databinding.ActivityJoinBinding.inflate

class NewMessageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)

        val rvNewMessages: RecyclerView = findViewById(R.id.rvNewMessage)
        supportActionBar?.title = "Select User"

//        var usersList = mutableListOf(User())
//
//        val adapter = UserAdapter(usersList)
//
//        rvNewMessages.adapter = adapter

    }

}