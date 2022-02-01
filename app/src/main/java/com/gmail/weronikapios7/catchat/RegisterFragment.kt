package com.gmail.weronikapios7.catchat

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat.startActivityForResult
import catchat.R
import catchat.databinding.ActivityMainBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {

    //views
    private lateinit var addName: EditText
    private lateinit var addEmail: EditText
    private lateinit var addPassword: EditText
    private lateinit var addImage: ImageView
    private lateinit var signBtn: Button

    //firebase
    private lateinit var mAuth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()

        addName = requireView().findViewById(R.id.etAddUsername)
        addEmail = requireView().findViewById(R.id.etAddEmail)
        addPassword = requireView().findViewById(R.id.etAddPassword)
        addImage = requireView().findViewById(R.id.ivAddImage)
        signBtn = requireView().findViewById(R.id.btnRegister)


        signBtn.setOnClickListener {
            val email = addEmail.text.toString()
            val password = addPassword.toString()

            signUp(email, password)
        }
    }

    private fun signUp(email: String, password: String) {
        //logic for creating a new user

        //TODO check if user does not exist
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful){
                    //Sign in success, update UI with the signed-in user's information
                    Log.d("RegisterFragment", "createUserWithEmail:success")

                    activity?.let{
                        val intent = Intent(it, MainActivity::class.java)
                        it.startActivity(intent)
                    }
                }else{
                    //If sign in fails, display a message to the user
                    Log.w("RegisterFragment", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    //TODO add updateUI
                }

            }
    }



}