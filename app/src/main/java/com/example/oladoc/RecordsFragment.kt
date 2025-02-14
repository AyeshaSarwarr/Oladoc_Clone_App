package com.example.oladoc

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class RecordsFragment : Fragment() {

    private val mediaREQUEST = 1  // Constant for request code

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_records, container, false)

        val addMedicalRecordButton: Button = view.findViewById(R.id.addMedicalRecordButton)
        addMedicalRecordButton.setOnClickListener {
            // Intent to open documents and images
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "*/*"
                val mimeTypes = arrayOf("image/*", "application/pdf")
                putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            }
            startActivityForResult(intent, mediaREQUEST)
        }

        return view
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == mediaREQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val selectedMediaUri: Uri? = data.data
            // Handle the selected media URI
        }
    }
}
