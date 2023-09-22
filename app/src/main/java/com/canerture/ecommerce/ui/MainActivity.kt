package com.canerture.ecommerce.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.canerture.ecommerce.R
import com.canerture.ecommerce.common.gone
import com.canerture.ecommerce.common.viewBinding
import com.canerture.ecommerce.common.visible
import com.canerture.ecommerce.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNav, navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment,
                R.id.searchFragment,
                R.id.cartFragment,
                R.id.favoritesFragment -> binding.bottomNav.visible()

                else -> binding.bottomNav.gone()
            }
        }
    }
}