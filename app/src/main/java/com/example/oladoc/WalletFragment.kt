package com.example.oladoc

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.oladoc.MainActivity

class WalletFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_wallet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backArrow: ImageView = view.findViewById(R.id.backArrow)
        backArrow.setOnClickListener {
            // Correctly handling back navigation or starting MainActivity
            activity?.let {
                val intent = Intent(it, MainActivity::class.java)
                intent.putExtra("openFragment", "HomeFragment")
                it.startActivity(intent)
                it.finish()
            }
        }
    }
}
