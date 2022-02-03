package com.gmail.weronikapios7.catchat

import android.app.AlertDialog
import android.content.Intent
import android.graphics.ImageDecoder
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import catchat.R
import com.gmail.weronikapios7.catchat.utils.LoadingDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.IOException
import java.util.*

//TODO move profile image uploading to separate class as image uploader
//TODO move firebase stuff to separate class

class RegisterFragment(val loadingDialog: LoadingDialog) : Fragment() {

    private var selectedPhotoUri: Uri? = null

    //views
    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var profileImage: Button
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

        name = requireView().findViewById(R.id.etAddUsername)
        email = requireView().findViewById(R.id.etAddEmail)
        password = requireView().findViewById(R.id.etAddPassword)
        profileImage = requireView().findViewById(R.id.btnProfileImage)
        signBtn = requireView().findViewById(R.id.btnRegister)

        signBtn.setOnClickListener {
            val email = email.text.toString()
            val password = password.toString()
            val username = name.text.toString()

            signUp(username, email, password)
        }

        profileImage.setOnClickListener {
            pickImageGallery()
        }
    }

    private fun signUp(username: String, email: String, password: String) {
        val isFilled = checkIfFilled(username, email, password)

        if (isFilled) {
            //show loading dialog while the data is being uploaded to firebase
            showLoading()
            //TODO check if user does not exist
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        //Sign in success, update UI with the signed-in user's information
                        Log.d("RegisterFragment", "createUserWithEmail:success")

                        uploadImageToStorage(username)

                    } else {
                        //If sign in fails, display a message to the user
                        Log.w("RegisterFragment", "createUserWithEmail:failure", task.exception)
                        createToast("Authentication failed. The email address is already in use by another account")
                    }
                }
        }
    }

    private val loadImage = registerForActivityResult(ActivityResultContracts.GetContent(),
        ActivityResultCallback {
            Log.d("RegisterFragment", "Photo was selected")

            try {
                selectedPhotoUri = it
                val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    val src: ImageDecoder.Source =
                        ImageDecoder.createSource(
                            requireContext().contentResolver,
                            it
                        )
                    ImageDecoder.decodeBitmap(src)
                } else {
                    selectedPhotoUri = it
                    @Suppress("DEPRECATION")
                    MediaStore.Images.Media.getBitmap(
                        requireContext().contentResolver,
                        it
                    )
                }

                val bitmapBg = BitmapDrawable(requireContext().resources, bitmap)
                profileImage.background = bitmapBg
                profileImage.text = ""

            } catch (e: IOException) {
                e.printStackTrace()
            }
        })


    private fun pickImageGallery() {
        loadImage.launch("image/*")
    }

    /**
     * Check if all the fields have been filled, return error message if there
     * is an empty field left
     */
    private fun checkIfFilled(username: String, email: String, password: String): Boolean {
        when {
            email.isEmpty() -> {
                createToast("Email cannot be empty")
                return false
            }
            password.isEmpty() -> {
                createToast("Password cannot be empty")
                return false
            }
            username.isEmpty() -> {
                createToast("Username cannot be empty")
                return false
            }
            else -> {
                return true
            }
        }
    }

    private fun createToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun uploadImageToStorage(username: String) {
        if (selectedPhotoUri == null) return



        //create random filename to save in firebase storage
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                Log.d("RegisterFragment", "Image uploaded successfully: ${it.metadata?.path}")

                ref.downloadUrl.addOnSuccessListener {
                    saveUserToDB(it.toString(), username)
                }
                    .addOnFailureListener { e ->
                        Log.w("RegisterFragment", "Error while downloading the image $e")

                    }
            }
            .addOnFailureListener { e ->
                Log.d("RegisterFragment", "Cannot upload image $e")
            }


    }

    private fun saveUserToDB(filepath: String, username: String) {

        val uid = FirebaseAuth.getInstance().uid ?: ""
        val db = Firebase.firestore

        val user = hashMapOf(
            "uid" to uid,
            "username" to username,
            "image" to filepath
        )

        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d("RegisterFragment", "DocumentSnapshot added with ID: ${documentReference.id}")

                //open main activity - with chats
                activity?.let {
                    val intent = Intent(it, MainActivity::class.java)
                    it.startActivity(intent)
                }
            }
            .addOnFailureListener { e ->

                Log.w("RegisterFragment", "Error adding document $e")
            }
            .addOnCompleteListener {
                Log.d("RegisterActivity", "Creating account completed")
                hideLoading()
            }
    }

    private fun showLoading(){
        Log.d("RegisterFragment", "Loading data")
        loadingDialog.startLoading();
    }

    private fun hideLoading(){
        loadingDialog.isDismiss()
    }


}

