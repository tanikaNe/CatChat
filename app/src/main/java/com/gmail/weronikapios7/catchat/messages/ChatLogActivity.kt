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
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.format.DateTimeFormatter

class ChatLogActivity : AppCompatActivity() {

    private lateinit var messages: MutableList<Message>
    private lateinit var firebase: FirebaseUtil
    private lateinit var messageBox: EditText
    private lateinit var sendButton: ImageButton
    private lateinit var receiverID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)
        firebase = FirebaseUtil()
        messages = mutableListOf()
        messageBox = findViewById(R.id.etEnterMsg)
        sendButton = findViewById(R.id.btnSend)

        val userToChat = intent.getParcelableExtra<com.gmail.weronikapios7.catchat.models.User>("USER")

        //set the navBar title to selected user's username
        supportActionBar?.title = userToChat?.username

        //get selected user's id
        receiverID = userToChat?.uid.toString()

        createAdapter()
        listenForMessages()

//        //dummy data
//        val message_from = userToChat?.let { Message("Hihugevfhvgsavsgvcghdhfkgsdahfdhgvgdvhcdgvdshachvbhabvmvdf", it.uid) }
//        message_from?.let { messagesList(it) }

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
        val timestamp = DateTimeFormatter.ISO_INSTANT.format(Instant.now())
        val message = firebase.createMessage(messageText, uid, receiverID, timestamp)
        firebase.getCollection("messages")
            .add(message)
            .addOnSuccessListener {
                Log.d("ChatLogActivity", "Saved the message chat")
            }

        messageBox.text = null
    }


}