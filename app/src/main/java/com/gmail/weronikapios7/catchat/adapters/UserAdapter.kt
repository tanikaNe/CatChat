package com.gmail.weronikapios7.catchat.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import catchat.R
import com.gmail.weronikapios7.catchat.messages.ChatLogActivity
import com.gmail.weronikapios7.catchat.models.UserItem
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter(
    private val users: List<UserItem>,
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false )
        return UserViewHolder(view)
    }


    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.itemView.apply {
            val image: CircleImageView = findViewById(R.id.ivItemImage)
            val username: TextView = findViewById(R.id.tvItemUsername)
            username.text = users[position].username

            Picasso.get().load(users[position].profileImage).into(image)
            this.setOnClickListener{
                Log.d("UserAdapter", "on click listener")
                val intent = Intent(context, ChatLogActivity::class.java)
                context.startActivity(intent)

            }

        }

    }


    //count the number of items in the list
    override fun getItemCount(): Int {
        return users.size
    }


    inner class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)


}