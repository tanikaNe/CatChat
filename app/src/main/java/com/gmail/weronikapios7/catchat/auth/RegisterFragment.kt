package com.gmail.weronikapios7.catchat.auth

import android.content.Context
import android.content.Intent
import android.graphics.ImageDecoder
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
import com.gmail.weronikapios7.catchat.messages.LatestMessagesActivity
import com.gmail.weronikapios7.catchat.utils.LoadingDialog
import de.hdodenhof.circleimageview.CircleImageView
import java.io.IOException
import java.util.*
import com.gmail.weronikapios7.catchat.utils.FirebaseUtil as FirebaseUtil

//TODO move profile image uploading to separate class as image uploader to reuse for later feature of image change

class RegisterFragment(private val loadingDialog: LoadingDialog) : Fragment() {

    private var selectedPhotoUri: Uri? = null

    //views
    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var profileImageBtn: Button
    private lateinit var signBtn: Button
    private lateinit var profileImage: CircleImageView

    private lateinit var firebase: FirebaseUtil

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
        firebase = FirebaseUtil()

        name = requireView().findViewById(R.id.etAddUsername)
        email = requireView().findViewById(R.id.etAddEmail)
        password = requireView().findViewById(R.id.etAddPassword)
        profileImageBtn = requireView().findViewById(R.id.btnProfileImage)
        signBtn = requireView().findViewById(R.id.btnRegister)
        profileImage = requireView().findViewById(R.id.ivItemImage)

        signBtn.setOnClickListener {
            val email = email.text.toString()
            val password = password.text.toString()
            val username = name.text.toString()

            signUp(username, email, password)
        }

        profileImageBtn.setOnClickListener {
            pickImageGallery()
        }
    }

    private fun signUp(username: String, email: String, password: String) {
        val isFilled = checkIfFilled(username, email, password)

        if (isFilled) {
            //show loading dialog while the data is being uploaded to firebase
            showLoading()

            //TODO check if user does not exist
            //TODO check if username is not taken
            firebase.getAuthInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        //Sign in success, update UI with the signed-in user's information
                        Log.d("RegisterFragment", "createUserWithEmail:success")
                        uploadImageToStorage(username)

                    } else {
                        //If sign in fails, display a message to the user
                        Log.w("RegisterFragment", "createUserWithEmail:failure", task.exception)
                        hideLoading()
                        createToast("Authentication failed. Try again later")
                    }
                }
        }
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

    private fun uploadImageToStorage(username: String) {
        if (selectedPhotoUri == null) return

        //create random filename to save in firebase storage
        val filename = UUID.randomUUID().toString()
        val ref = firebase.getStorageInstance("/images/$filename")

        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                Log.d("RegisterFragment", "Image uploaded successfully: ${it.metadata?.path}")

                ref.downloadUrl.addOnSuccessListener {
                    saveUserToDB(it.toString(), username)
                }
                    .addOnFailureListener { e ->
                        Log.w("RegisterFragment", "Error while downloading the image $e")
                        createToast("Something went wrong, try again")

                    }
            }
            .addOnFailureListener { e ->
                Log.d("RegisterFragment", "Cannot upload image $e")
                createToast("Something went wrong, try again")
            }


    }

    private fun saveUserToDB(filepath: String, username: String) {

        val uid = firebase.getAuthInstance().uid ?: ""
        val user = firebase.createUser(uid, username, filepath)

        firebase.getCollection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d("RegisterFragment", "DocumentSnapshot added with ID: ${documentReference.id}")

                //open latest messages activity
                activity?.let {
                    launchActivity(it)
                }
            }
            .addOnFailureListener { e ->
                Log.w("RegisterFragment", "Error adding document $e")
                createToast("Something went wrong, try again")
            }
            .addOnCompleteListener {
                Log.d("RegisterActivity", "Creating account completed")
            }
        hideLoading()
    }

    private val loadImage = registerForActivityResult(
        ActivityResultContracts.GetContent(),
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

                profileImage.setImageBitmap(bitmap)
                profileImageBtn.alpha = 0f

            } catch (e: IOException) {
                e.printStackTrace()
            }
        })


    private fun pickImageGallery() {
        loadImage.launch("image/*")
    }

    private fun showLoading(){
        loadingDialog.startLoading();
    }

    private fun hideLoading(){
        loadingDialog.stopLoading()
    }

    private fun launchActivity(context: Context){
        val intent = Intent(context, LatestMessagesActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    private fun createToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }




}

