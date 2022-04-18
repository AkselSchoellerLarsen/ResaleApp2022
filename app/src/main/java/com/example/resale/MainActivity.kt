package com.example.resale

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.resale.databinding.ActivityMainBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)


        if(Firebase.auth.currentUser == null) {
            binding.fabCreateItem.visibility = View.GONE
        } else {
            binding.fabCreateItem.visibility = View.VISIBLE
        }
        binding.fabCreateItem.setOnClickListener {
            val newFragment = CreateItemDialogFragment()
            newFragment.show(supportFragmentManager, "create_item")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val login = menu.findItem(R.id.action_login)
        val logout = menu.findItem(R.id.action_logout)

        if(login.isVisible && logout.isVisible) {
            invalidateOptionsMenu() // We have to invalidate the options menu to get it to recalculate.
        }

        if(Firebase.auth.currentUser != null) {
            login.isVisible = false
        } else {
            logout.isVisible = false
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_login) {
            val navController = findNavController(R.id.nav_host_fragment_content_main)
            navController.navigate(R.id.action_Main_to_LoginFragment)
        }

        if(item.itemId == R.id.action_logout) {
            if (Firebase.auth.currentUser != null) {
                Firebase.auth.signOut()
                val navController = findNavController(R.id.nav_host_fragment_content_main)
                navController.navigate(R.id.action_Main_to_LoginFragment)
            } else {
                Snackbar.make(binding.root, "Cannot sign out", Snackbar.LENGTH_LONG).show()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}