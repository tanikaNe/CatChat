package com.gmail.weronikapios7.catchat.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import catchat.R
import com.gmail.weronikapios7.catchat.messages.LatestMessagesActivity
import com.gmail.weronikapios7.catchat.utils.FirebaseUtil


class LoginFragment : Fragment() {

    private lateinit var editEmail: EditText
    private lateinit var editPassword: EditText
    private lateinit var loginBtn: Button
    private lateinit var firebase: FirebaseUtil

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebase = FirebaseUtil()

        editEmail = requireView().findViewById(R.id.etAddEmail)
        editPassword = requireView().findViewById(R.id.etAddPassword)
        loginBtn = requireView().findViewById(R.id.btnLogin)

        loginBtn.setOnClickListener {
            val email = editEmail.text.toString()
            val password = editPassword.text.toString()
            login(email, password)
        }
    }


    private fun login(email: String, password: String) {
        // login for existing user
        Log.d("LoginFragment", "Email: $email, Password: $password")

        if (email.isEmpty() || password.isEmpty()) {
            createToast("Enter email and password")
        } else {
            firebase.getAuthInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        //Sign in success, update UI with the signed-in user's information
                        Log.d("LoginFragment", "signInWithEmail:success")

                        activity?.let {
                            launchActivity(it)
                        }
                    } else {
                        //If sign in fails, show a toast
                        Log.d("LoginFragment", "signInWithEmail:failure", task.exception)
                        createToast("Authentication failed. Try again")
                    }
                }
        }
    }

    private fun launchActivity(context: Context) {
        val intent = Intent(context, LatestMessagesActivity::class.java)
        context.startActivity(intent)
    }

    private fun createToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }


}