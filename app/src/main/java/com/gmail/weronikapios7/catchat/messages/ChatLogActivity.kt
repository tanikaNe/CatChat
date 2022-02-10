package com.gmail.weronikapios7.catchat.messages

import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import catchat.R
import com.gmail.weronikapios7.catchat.adapters.ChatAdapter
import com.gmail.weronikapios7.catchat.models.Message
import com.gmail.weronikapios7.catchat.utils.FirebaseUtil

class ChatLogActivity : AppCompatActivity() {

    //private lateinit var msgAdapter: ChatAdapter
    private lateinit var messages: MutableList<Message>
    private lateinit var firebase: FirebaseUtil
    private lateinit var messageBox: EditText
    private lateinit var sendButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)
        firebase = FirebaseUtil()
        messages = mutableListOf()
        messageBox = findViewById(R.id.etEnterMsg)
        sendButton = findViewById(R.id.btnSend)

        //set the navBar title to selected user's username
        val userToChat = intent.getParcelableExtra<com.gmail.weronikapios7.catchat.models.User>("USER")
        supportActionBar?.title = userToChat?.username
        createAdapter()

        //dummy data
        val message_from = userToChat?.let { Message("Hihugevfhvgsavsgvcghdhfkgsdahfdhgvgdvhcdgvdshachvbhabvmvdf", it.uid) }
        message_from?.let { messagesList(it) }

        sendButton.setOnClickListener{
            sendMessage()
        }
    }

    private fun createAdapter(){
        val rvNewMessage: RecyclerView = findViewById(R.id.rvChatLog)
        val msgAdapter = ChatAdapter(messages)
        rvNewMessage.adapter = msgAdapter
    }

    private fun messagesList(message: Message){
        messages.add(message)
    }

    private fun sendMessage(){
        val messageText = messageBox.text.toString()
        val uid = firebase.getAuthInstance().uid.toString()
        val message = Message(messageText, uid)
        val ref = firebase.getCollection("messages")
            .add(message)
            .addOnSuccessListener {
                Log.d("ChatLogActivity", "Saved the message chat")
            }

        messageBox.text = null
    }
}