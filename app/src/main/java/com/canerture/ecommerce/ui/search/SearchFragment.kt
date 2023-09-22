package com.canerture.ecommerce.ui.search

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.canerture.ecommerce.R
import com.canerture.ecommerce.common.gone
import com.canerture.ecommerce.common.showPopup
import com.canerture.ecommerce.common.viewBinding
import com.canerture.ecommerce.common.visible
import com.canerture.ecommerce.data.model.response.ProductUI
import com.canerture.ecommerce.databinding.FragmentSearchBinding
import com.canerture.ecommerce.ui.home.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val binding by viewBinding(FragmentSearchBinding::bind)

    private val viewModel: SearchViewModel by viewModels()

    private val searchProductsAdapter by lazy { SearchProductsAdapter(::onProductClick, ::onFavoriteClick) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            rvSearchProducts.adapter = searchProductsAdapter

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    viewModel.searchProduct(newText)
                    return false
                }
            })
        }

        observeData()
    }

    private fun observeData() = with(binding) {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SearchState.Loading -> {
                    progressBar.visible()
                }

                is SearchState.Success -> {
                    searchProductsAdapter.submitList(state.products)
                    progressBar.gone()
                }

                is SearchState.Error -> {
                    showPopup(state.throwable.message)
                    progressBar.gone()
                }

                is SearchState.EmptyScreen -> {
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