package com.gmail.weronikapios7.catchat.messages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import catchat.R
import com.gmail.weronikapios7.catchat.adapters.ChatAdapter
import com.gmail.weronikapios7.catchat.adapters.UserAdapter
import com.gmail.weronikapios7.catchat.models.Message
import com.gmail.weronikapios7.catchat.models.User

class ChatLogActivity : AppCompatActivity() {

    private lateinit var msgAdapter: ChatAdapter
    private lateinit var messages: MutableList<Message>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)

        //set the navBar title to selected user's username
        val user = intent.getParcelableExtra<com.gmail.weronikapios7.catchat.models.User>("USER")
        supportActionBar?.title = user?.username
    }

    private fun createAdapter(){
        val rvNewMessage: RecyclerView = findViewById(R.id.rvChatLog)
        msgAdapter = ChatAdapter(messages)
        rvNewMessage.adapter = msgAdapter
    }
}