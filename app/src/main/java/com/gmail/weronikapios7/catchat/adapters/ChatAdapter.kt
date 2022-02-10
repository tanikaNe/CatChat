package com.gmail.weronikapios7.catchat.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import catchat.R
import com.gmail.weronikapios7.catchat.models.Message
import com.gmail.weronikapios7.catchat.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class ChatAdapter(
    private val messages: List<Message>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_RECEIVED = 1
    val ITEM_SENT = 2

    private lateinit var context: Context



    /**
     * View holder for sent message
     */
    class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sentMessage: TextView = itemView.findViewById(R.id.tvSentMessage)
        val image: CircleImageView = itemView.findViewById(R.id.ivMyProfileImage)
    }

    /**
     * View holder for received message
     */
    class ReceiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val receivedMessage: TextView = itemView.findViewById(R.id.tvReceivedMessage)
        val image: CircleImageView = itemView.findViewById(R.id.ivReceivedProfileImage)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context

        return if( viewType == 1){
            //inflate receive
            val view: View = LayoutInflater.from(context).inflate(R.layout.received_msg_row, parent, false)
            ReceiveViewHolder(view)
        }else{
            //inflate sent
            val view: View = LayoutInflater.from(context).inflate(R.layout.sent_message_row, parent, false)
            SentViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messages[position]

        if (holder.javaClass == SentViewHolder::class.java) {
            //behaviour for sent view holder
            val viewHolder = holder as SentViewHolder
            holder.sentMessage.text = currentMessage.message
          //  Picasso.get().load(messages[position].senderImage).into(holder.image)

        } else {
            //behaviour for receive view holder
            val viewHolder = holder as ReceiveViewHolder
            holder.receivedMessage.text = currentMessage.message
           // Picasso.get().load(messages[position].senderImage).into(holder.image)
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun getItemViewType(position: Int): Int {
       val currentMessage = messages[position]

        return if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderID)){
            ITEM_SENT
        }else{
            ITEM_RECEIVED
        }
    }
}