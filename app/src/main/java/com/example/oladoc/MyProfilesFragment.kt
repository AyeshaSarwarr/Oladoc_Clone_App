package com.example.oladoc

import ProfilesAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.oladoc.database.UserProfile
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase

class MyProfilesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProfilesAdapter
    private lateinit var imageViewNoProfile: ImageView
    private lateinit var textViewNoProfile: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_profiles, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backArrow: ImageView = view.findViewById(R.id.backArrow)
        backArrow.setOnClickListener {
            requireActivity().onBackPressed()  // Use requireActivity() to handle back press
        }

        imageViewNoProfile = view.findViewById(R.id.imageViewNoProfile)
        textViewNoProfile = view.findViewById(R.id.textViewNoProfile)

        val addButton: Button = view.findViewById(R.id.buttonAddProfile)
        addButton.setOnClickListener {
            navigateToAddProfileFragment()
        }

        setupRecyclerView(view)
        fetchProfiles()
    }

    private fun navigateToAddProfileFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, NewProfileFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    private fun setupRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.profilesRecyclerView)
        adapter = ProfilesAdapter(emptyList())
        recyclerView.adapter = adapter
    }

    private fun fetchProfiles() {
        FirebaseDatabase.getInstance().getReference("profiles")
            .get().addOnSuccessListener { dataSnapshot ->
                if (dataSnapshot.exists()) {
                    updateProfileList(dataSnapshot)
                    imageViewNoProfile.visibility = View.GONE
                    textViewNoProfile.visibility = View.GONE
                } else {
                    imageViewNoProfile.visibility = View.VISIBLE
                    textViewNoProfile.visibility = View.VISIBLE
                }
            }.addOnFailureListener {
                Toast.makeText(context, "Failed to fetch profiles", Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateProfileList(dataSnapshot: DataSnapshot) {
        val profiles = dataSnapshot.children.mapNotNull { it.getValue(UserProfile::class.java) }
        adapter.updateProfiles(profiles)
    }
}
