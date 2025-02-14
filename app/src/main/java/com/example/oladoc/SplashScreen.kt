package com.example.oladoc

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.splashscreen)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Check if user is signed in (non-null) and update UI accordingly.
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            // User is signed in, redirect to MainActivity
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            // No user is signed in, redirect to LoginActivity
            startActivity(Intent(this, Login::class.java))
        }
        finish() // Close the SplashScreen activity
    }
}