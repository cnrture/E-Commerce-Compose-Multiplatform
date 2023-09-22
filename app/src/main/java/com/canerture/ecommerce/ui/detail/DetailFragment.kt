package com.canerture.ecommerce.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.canerture.ecommerce.R
import com.canerture.ecommerce.common.gone
import com.canerture.ecommerce.common.viewBinding
import com.canerture.ecommerce.common.visible
import com.canerture.ecommerce.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created on 10.08.2023
 * @author Caner Türe
 */

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val binding by viewBinding(FragmentDetailBinding::bind)

    private val viewModel by viewModels<DetailViewModel>()

    private val args by navArgs<DetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getProductDetail(args.id)

        observeData()
    }

    private fun observeData() = with(binding) {
        viewModel.detailState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is DetailState.Loading -> {
                    progressBar.visible()
                }

                is DetailState.Success -> {
                    progressBar.gone()
                }

                is DetailState.Error -> {
                    ivError.visible()
                    tvError.visible()
                    tvError.text = state.throwable.message.orEmpty()
                    progressBar.gone()
                }

                is DetailState.EmptyScreen -> {

                }
            }
        }
    }
}