package com.gmail.weronikapios7.catchat

import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import catchat.R
import java.io.IOException

class UserAdapter(
    var users: List<User>
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false )
        return UserViewHolder(view)
    }


    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.itemView.apply {
            val image: ImageView = findViewById(R.id.ivItemImage)
            val username: TextView = findViewById(R.id.tvItemUsername)

            username.text = users[position].username

        }
    }

    //count the number of items in the list
    override fun getItemCount(): Int {
        return users.size
    }


}