package com.gmail.weronikapios7.catchat.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import catchat.R
import com.gmail.weronikapios7.catchat.models.UserItem
import com.squareup.picasso.Picasso

class UserAdapter(
    private var users: List<UserItem>
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false )
        return UserViewHolder(view)
    }


    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.itemView.apply {
            val image: ImageView = findViewById(R.id.ivItemImage)
            val username: TextView = findViewById(R.id.btnItemUser)
            username.text = users[position].username

            Picasso.get().load(users[position].profileImage).into(image)

        }
    }

    //count the number of items in the list
    override fun getItemCount(): Int {
        return users.size
    }




}