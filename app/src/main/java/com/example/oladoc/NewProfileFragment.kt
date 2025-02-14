package com.example.oladoc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.oladoc.database.UserProfile
import com.google.firebase.database.FirebaseDatabase

class NewProfileFragment : Fragment() {

    private lateinit var editTextName: EditText
    private lateinit var editTextRelationship: EditText
    private lateinit var editTextNumber: EditText
    private lateinit var editTextAge: EditText
    private lateinit var radioGroupGender: RadioGroup
    private lateinit var buttonAddProfile: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextName = view.findViewById(R.id.editTextName)
        editTextRelationship = view.findViewById(R.id.editTextRelationship)
        editTextNumber = view.findViewById(R.id.editTextNumber)
        editTextAge = view.findViewById(R.id.editTextAge)
        radioGroupGender = view.findViewById(R.id.radioGroupGender)
        buttonAddProfile = view.findViewById(R.id.buttonAddProfile)

        buttonAddProfile.setOnClickListener {
            val name = editTextName.text.toString()
            val relationship = editTextRelationship.text.toString()
            val number = editTextNumber.text.toString()
            val age = editTextAge.text.toString()
            val genderId = radioGroupGender.checkedRadioButtonId
            val genderButton = view.findViewById<RadioButton>(genderId)
            val gender = genderButton?.text.toString()

            if (name.isNotEmpty() && relationship.isNotEmpty() && number.isNotEmpty() && age.isNotEmpty() && gender.isNotEmpty()) {
                val database = FirebaseDatabase.getInstance()
                val myRef = database.getReference("profiles")

                val userId = myRef.push().key ?: return@setOnClickListener  // Exit if null
                val userProfile = UserProfile(name = name, relationship = relationship, number = number, age = age.toInt(), gender = gender)
                myRef.child(userId).setValue(userProfile).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(requireContext(), "Profile added successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "Failed to add profile", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
    companion object {
        fun newInstance(): NewProfileFragment {
            return NewProfileFragment()
        }
    }
}
