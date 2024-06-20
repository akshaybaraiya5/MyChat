package com.example.mychat.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Toast
import com.example.mychat.R
import com.example.mychat.databinding.ActivityLoginScreenBinding
import com.example.mychat.databinding.ActivitySignUpScreenBinding
import com.google.firebase.auth.FirebaseAuth

class LoginScreen : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginScreenBinding
    private var isPasswordVisible: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()



        binding.loginButton.setOnClickListener {
            if(isValidate()){
                loginWithEmail(binding.editEmail.text.toString(), binding.editPassword.text.toString())
            }
        }

        binding.signUpText.setOnClickListener {
            val intent = Intent(this,SignUpScreen::class.java)
            startActivity(intent)
        }

    }


    private fun loginWithEmail(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Login success
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                    // Navigate to another activity if needed
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // If login fails, display a message to the user.
                    Toast.makeText(
                        this,
                        "Login Failed: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    fun isValidate(): Boolean {
        if (binding.editEmail.text.toString() == "") {
            Toast.makeText(this, "Please Insert Your Email", Toast.LENGTH_SHORT).show()
            return false

        } else if (binding.editPassword.text.toString() == "") {
            Toast.makeText(this, "Please Insert Your Password", Toast.LENGTH_SHORT).show()
            return false
        } else {
            return true
        }
    }

    fun setVisibility(view: View){
        isPasswordVisible = !isPasswordVisible
        if (isPasswordVisible) {
            // Hide Password
            binding.editPassword.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.visibilityButton.setImageResource(R.drawable.eye_on)
        } else {
            // Show Password
            binding.editPassword.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            binding.visibilityButton.setImageResource(R.drawable.eye_off)
        }
    }
}