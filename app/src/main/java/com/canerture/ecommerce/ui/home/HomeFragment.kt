package com.canerture.ecommerce.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.canerture.ecommerce.R
import com.canerture.ecommerce.common.gone
import com.canerture.ecommerce.common.showPopup
import com.canerture.ecommerce.common.viewBinding
import com.canerture.ecommerce.common.visible
import com.canerture.ecommerce.data.model.response.ProductUI
import com.canerture.ecommerce.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)

    private val viewModel by viewModels<HomeViewModel>()

    private val saleProductsAdapter by lazy { SaleProductsAdapter(::onProductClick, ::onFavoriteClick) }

    private val allProductsAdapter by lazy { AllProductsAdapter(::onProductClick, ::onFavoriteClick) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvSaleProducts.adapter = saleProductsAdapter
        binding.rvAllProducts.adapter = allProductsAdapter

        observeData()
    }

    private fun observeData() = with(binding) {
        viewModel.mainState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is HomeState.Loading -> progressBar.visible()

                is HomeState.Success -> {
                    saleProductsAdapter.submitList(state.saleProducts)
                    allProductsAdapter.submitList(state.allProducts)
                    progressBar.gone()
                }

                is HomeState.Error -> {
                    showPopup(state.throwable.message)
                    progressBar.gone()
                }

                is HomeState.EmptyScreen -> {
                    progressBar.gone()
                }
            }
        }
    }

    private fun onProductClick(id: Int) {
        val action = HomeFragmentDirections.homeToDetail(id)
        findNavController().navigate(action)
    }

    private fun onFavoriteClick(product: ProductUI) {
        viewModel.setFavoriteState(product)
    }
}