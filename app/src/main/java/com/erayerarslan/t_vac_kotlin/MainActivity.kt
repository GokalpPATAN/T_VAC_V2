package com.farukayata.t_vac_kotlin

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.farukayata.t_vac_kotlin.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint
import com.farukayata.t_vac_kotlin.ui.components.CustomToolbar

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        val customToolbar = binding.customToolbar
        customToolbar.onBackClick = { onBackPressedDispatcher.onBackPressed() }
        //customToolbar.onHomeClick = { navController.popBackStack(navController.graph.startDestinationId, false) }
        customToolbar.onHomeClick = {
            navController.popBackStack(navController.graph.startDestinationId, false)
            binding.bottomNavigation.selectedItemId = R.id.homeFragment
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.treeDetailFragment -> {
                    showToolbar()
                    customToolbar.setTitle("T-VAC")
                    customToolbar.showBack(true)
                    customToolbar.showHome(true)
                }
                // Diğer fragmentlar için toolbarı gizle
                else -> {
                    hideToolbar()
                }
            }
        }

        // BottomNavigationView ile NavController'ı bağla
        val bottomNavigationView = binding.bottomNavigation
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    navController.navigate(R.id.homeFragment)
                    true
                }
                R.id.searchFragment -> {
                    navController.navigate(R.id.searchFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun showToolbar() {
        binding.customToolbar.visibility = View.VISIBLE
    }
    private fun hideToolbar() {
        binding.customToolbar.visibility = View.GONE
    }

    private fun createBottomNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        NavigationUI.setupWithNavController(binding.bottomNavigation,navHostFragment.navController)


    }

    override fun onSupportNavigateUp(): Boolean {

        return  navController.navigateUp() ||   super.onSupportNavigateUp()
    }
}