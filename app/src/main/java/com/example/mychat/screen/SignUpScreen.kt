package com.example.mychat.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.mychat.R
import com.example.mychat.databinding.ActivitySignUpScreenBinding
import com.example.mychat.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class SignUpScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpScreenBinding
    private var isPasswordVisible: Boolean = false
    private lateinit var auth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()



        binding.loginButton.setOnClickListener {
            if (isValidate()) {
                singUp(binding.editEmail.text.toString(), binding.editPassword.text.toString())
            }
        }


        binding.loginText.setOnClickListener {
            finish()
        }


    }


    fun setVisibility(view: View) {
        isPasswordVisible = !isPasswordVisible
        if (isPasswordVisible) {
            // Hide Password
            binding.editPassword.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.confirmPassword.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.visibilityButton.setImageResource(R.drawable.eye_on)
        } else {
            // Show Password
            binding.editPassword.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            binding.confirmPassword.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            binding.visibilityButton.setImageResource(R.drawable.eye_off)
        }
    }

    fun isValidate(): Boolean {
        if (binding.editEmail.text.toString() == "") {
            Toast.makeText(this, "Please Insert Your Email", Toast.LENGTH_SHORT).show()
            return false

        } else if (binding.editPassword.text.toString() == "") {
            Toast.makeText(this, "Please Insert Your Password", Toast.LENGTH_SHORT).show()
            return false

        } else if (binding.confirmPassword.text.toString() == "") {
            Toast.makeText(this, "Please Confirm Password", Toast.LENGTH_SHORT).show()
            return false

        } else if (binding.editPassword.text.toString() != binding.confirmPassword.text.toString()) {
            Toast.makeText(this, "Password does\'t match", Toast.LENGTH_SHORT).show()
            return false
        } else {
            return true
        }
    }


    fun singUp(email: String, password: String) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("login", "createUserWithEmail:success")
                    val user = auth.currentUser
                    addUserToDatabase(
                        binding.editEmail.text.toString(),
                        binding.editEmail.text.toString(),
                        auth.currentUser?.uid!!
                    )
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("login", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    private fun addUserToDatabase(name: String, email: String, uid: String) {
        mDbRef = FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user").child(uid).setValue(User(name, email, uid))
    }

}