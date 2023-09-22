package com.canerture.ecommerce.ui.signin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.canerture.ecommerce.R
import com.canerture.ecommerce.common.gone
import com.canerture.ecommerce.common.viewBinding
import com.canerture.ecommerce.common.visible
import com.canerture.ecommerce.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val binding by viewBinding(FragmentSignInBinding::bind)

    private val viewModel: SignInViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            btnSignIn.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()

                viewModel.checkInfo(email, password)
            }

            ivGoToSignUp.setOnClickListener {
                findNavController().navigate(R.id.signInToSignUp)
            }
        }

        initObservers()
    }

    private fun initObservers() = with(binding) {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                SignInState.Loading -> progressBar.visible()

                SignInState.GoToHome -> findNavController().navigate(R.id.signInToHome)

                is SignInState.Error -> {
                    progressBar.gone()
                }
            }
        }
    }
}