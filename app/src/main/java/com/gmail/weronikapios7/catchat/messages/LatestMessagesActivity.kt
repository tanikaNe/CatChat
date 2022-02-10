package com.gmail.weronikapios7.catchat.messages

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import catchat.R
import com.gmail.weronikapios7.catchat.auth.JoinActivity
import com.gmail.weronikapios7.catchat.utils.FirebaseUtil
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class LatestMessagesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latest_messages)
        verifyUserIsLoggedIn()

    }

    private fun verifyUserIsLoggedIn(){

        if(FirebaseUtil().getAuthInstance().uid == null){
            launchJoinActivity()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_new_message -> {
                val intent = Intent(this, NewMessageActivity::class.java)
                startActivity(intent)
            }

            R.id.menu_sign_out -> {
                FirebaseAuth.getInstance().signOut()
                launchJoinActivity()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun launchJoinActivity(){
        val intent = Intent(this, JoinActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}