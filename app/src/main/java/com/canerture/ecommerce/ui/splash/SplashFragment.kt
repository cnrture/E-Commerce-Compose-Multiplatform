package com.canerture.ecommerce.ui.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.canerture.ecommerce.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val viewModel by viewModels<SplashViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            delay(2000)
            viewModel.checkUserLogin()
        }

        initObservers()
    }

    private fun initObservers() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is SplashState.GoToHome -> {
                    findNavController().navigate(R.id.splashToHome)
                }

                SplashState.GoToSignIn -> {
                    findNavController().navigate(R.id.splashToSignIn)
                }
            }
        }
    }
}