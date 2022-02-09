package com.gmail.weronikapios7.catchat.auth

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
import com.google.firebase.auth.FirebaseAuth



class LoginFragment : Fragment() {

    private lateinit var editEmail: EditText
    private lateinit var editPassword: EditText
    private lateinit var loginBtn: Button
    private lateinit var mAuth: FirebaseAuth

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

        mAuth = FirebaseAuth.getInstance()

        editEmail = requireView().findViewById(R.id.etAddEmail)
        editPassword = requireView().findViewById(R.id.etAddPassword)
        loginBtn = requireView().findViewById(R.id.btnLogin)

        loginBtn.setOnClickListener {
            val email = editEmail.text.toString()
            val password = editPassword.text.toString()
            login(email,password)
        }
    }


    private fun login(email: String, password: String){
        // login for existing user
        Log.d("LoginFragment", "Email: $email, Password: $password")
        if( email.isEmpty() || password.isEmpty()){
            Toast.makeText(context, "Enter email and password", Toast.LENGTH_SHORT).show()
        }else {
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        //Sign in success, update UI with the signed-in user's information
                        Log.d("LoginFragment", "signInWithEmail:success")

                        activity?.let {
                            val intent = Intent(it, LatestMessagesActivity::class.java)
                            it.startActivity(intent)
                        }
                    } else {
                        //If sign in fails, show a toast
                        Log.d("LoginFragment", "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            context,
                            "Authentication failed. Try again",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }
        }

    }





}