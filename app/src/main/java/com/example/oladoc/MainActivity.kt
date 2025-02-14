
package com.example.oladoc

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.oladoc.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var fragmentManager: FragmentManager
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        val toggle = ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar, R.string.nav_open, R.string.nav_close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navigationDrawer.setNavigationItemSelectedListener(this)

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_home -> openFragment(HomeFragment())
                R.id.bottom_appointments -> openFragment(AppointmentsFragment())
                R.id.bottom_records -> openFragment(RecordsFragment())
                R.id.bottom_healthzone -> openFragment(HealthzoneFragment())
            }
            true
        }

        fragmentManager = supportFragmentManager

        // Check if the intent has the extra to navigate to HomeFragment
        if (intent.getBooleanExtra("navigateToHome", false)) {
            openFragment(HomeFragment())
        } else {
            openFragment(HomeFragment()) // Open by default if extra is not present
        }

        // Set up window insets for edge-to-edge experience
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawer_layout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set the NavigationView listener
        binding.navigationDrawer.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_myprofiles -> openFragment(MyProfilesFragment())
            R.id.nav_doctors -> openFragment(RecentDoctorsFragment())
            R.id.nav_orders -> openFragment(MyOrdersFragment())
            R.id.nav_wallet -> openFragment(WalletFragment())
            R.id.nav_friend -> openFragment(ReferFriendFragment())
            R.id.nav_feedback -> openFragment(GiveFeedbackFragment())
            R.id.nav_logout -> {
                logoutUser()
                return true
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed() // Calls the default back press behavior
        }
    }

    private fun openFragment(fragment: Fragment) {
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }
    private fun logoutUser() {
        FirebaseAuth.getInstance().signOut() // Firebase sign out
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish() // Finish MainActivity to prevent returning on back press
    }
}
