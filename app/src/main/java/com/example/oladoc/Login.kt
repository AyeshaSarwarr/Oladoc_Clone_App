
package com.example.oladoc

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.hbb20.CountryCodePicker

class Login : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Apply edge-to-edge configuration if needed
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val myPhoneNumber = findViewById<EditText>(R.id.editTextPhone)
        val otpButton = findViewById<Button>(R.id.enterPhoneButton)
        val countryCodePicker = findViewById<CountryCodePicker>(R.id.countryCodeHolder) // Assuming you have a CountryCodePicker view

        countryCodePicker.registerCarrierNumberEditText(myPhoneNumber)
        otpButton.setOnClickListener {
            // Assume countryCodePicker.isValidFullNumber() and countryCodePicker.getFullNumberWithPlus are valid methods
            // Usually, these methods are part of some library like `ccp` (CountryCodePicker), ensure it's correctly implemented
            if (!countryCodePicker.isValidFullNumber) {
                myPhoneNumber.error = "Phone number is not valid"
            } else {
                val intent = Intent(this, Authentication::class.java) // Corrected syntax for class reference
                intent.putExtra("phone", countryCodePicker.fullNumberWithPlus)
                startActivity(intent)
            }
        }
    }
}
