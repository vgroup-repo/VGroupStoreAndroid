package com.vgroup.vgroupstore


import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController

import com.vgroup.vgroupstore.databinding.ActivityMainBinding
import com.vgroup.vgroupstore.presentation.cart.CartViewModel

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val cartViewModel: CartViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup NavHost
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val navController = navHostFragment.navController

        // Connect bottom navigation with nav controller
        binding.bottomNav.setupWithNavController(navController)

        // Manage visibility of bottom navigation
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                // Screens that should show bottom navigation
                R.id.dashboardFragment,
                R.id.cartFragment,
                R.id.profileFragment -> binding.bottomNav.visibility = android.view.View.VISIBLE

//                // Screens that must HIDE bottom nav

                R.id.searchFragment -> binding.bottomNav.visibility = android.view.View.VISIBLE

                else -> binding.bottomNav.visibility = android.view.View.GONE
            }
        }

        setupCartBadge()


    }

    private fun setupCartBadge() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                cartViewModel.cartUpdate.collect { cartItems ->

                    val count = cartItems.sumOf { it.quantity }
                    val badge = binding.bottomNav.getOrCreateBadge(R.id.cartFragment)

                    if (count > 0) {
                        badge.isVisible = true
                        badge.number = count
                    } else {
                        badge.clearNumber()
                        badge.isVisible = false
                    }
                }
            }
        }
    }

}

