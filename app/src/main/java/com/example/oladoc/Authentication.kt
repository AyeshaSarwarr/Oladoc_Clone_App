package com.example.oladoc

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class Authentication : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var myPhoneNumber: String
    private lateinit var otpCode: EditText
    private lateinit var nextButton: Button
    private lateinit var resendButton: Button // Add a resend button
    private val timeOutSeconds: Long = 60L
    private lateinit var verificationId: String
    private var forceResendingToken: PhoneAuthProvider.ForceResendingToken? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

        mAuth = FirebaseAuth.getInstance()
        otpCode = findViewById(R.id.editTextNumberSigned)
        nextButton = findViewById(R.id.button2)
        resendButton = findViewById(R.id.resendButton) // Initialize the resend button

        myPhoneNumber = intent.extras?.getString("phone") ?: ""

        if (myPhoneNumber.isNotEmpty()) {
            Toast.makeText(applicationContext, myPhoneNumber, Toast.LENGTH_LONG).show()
            sendOtp(myPhoneNumber, false)
        } else {
            Toast.makeText(applicationContext, "No phone number passed", Toast.LENGTH_LONG).show()
        }

        nextButton.setOnClickListener {
            // Remove OTP validation checks
            // Directly navigate to MainActivity, assuming the navigation should happen no matter the OTP
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("openFragment", "HomeFragment") // Pass extra to indicate which fragment to load
            startActivity(intent)
            finish() // Finish the Authentication activity
        }

        resendButton.setOnClickListener { // Add a click listener for the resend button
            sendOtp(myPhoneNumber, true) // Resend OTP with isResend set to true
        }

        val backArrow: ImageView = findViewById(R.id.backArrow)
        backArrow.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }

    private fun sendOtp(phoneNumber: String, isResend: Boolean) {
        val optionsBuilder = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(timeOutSeconds, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                    // Auto sign-in when verification is automatically completed
                    signIn(phoneAuthCredential)
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    // Provide more specific error messages based on the exception
                    Toast.makeText(
                        applicationContext,
                        "Verification Failed: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(id, token)
                    verificationId = id
                    forceResendingToken = token
                    Toast.makeText(applicationContext, "OTP sent successfully", Toast.LENGTH_LONG)
                        .show()
                }
            })

        if (isResend && forceResendingToken != null) {
            PhoneAuthProvider.verifyPhoneNumber(
                optionsBuilder.setForceResendingToken(
                    forceResendingToken!!
                ).build()
            )
        } else {
            PhoneAuthProvider.verifyPhoneNumber(optionsBuilder.build())
        }
    }

    private fun signIn(phoneAuthCredential: PhoneAuthCredential) {
        mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(applicationContext, "Sign in successful", Toast.LENGTH_LONG).show()
                // Navigate to MainActivity after successful sign-in
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("openFragment", "HomeFragment") // Pass extra to indicate which fragment to load
                startActivity(intent)
                finish() // Finish the Authentication activity
            } else {
                // Provide more specific error messages based on the exception
                Toast.makeText(
                    applicationContext,
                    "Sign in failed: ${task.exception?.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}

