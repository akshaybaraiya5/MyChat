package com.example.mychat.screen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.mychat.R
import com.google.firebase.auth.FirebaseAuth

class SplashScreen : AppCompatActivity() {

    private val splashTimeOut: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen2)
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        Handler().postDelayed({
            if(currentUser!=null){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                startActivity(Intent(this, LoginScreen::class.java))
                finish()
            }
            // Start your app's main activity

        }, splashTimeOut)
    }
}