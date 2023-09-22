package com.canerture.ecommerce.ui.favorites

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
import com.canerture.ecommerce.databinding.FragmentFavoritesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private val binding by viewBinding(FragmentFavoritesBinding::bind)

    private val viewModel: FavoritesViewModel by viewModels()

    private val favoritesAdapter by lazy { FavoritesAdapter(::onProductClick, ::onDeleteClick) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getFavorites()

        with(binding) {
            rvFavorites.adapter = favoritesAdapter
        }

        initObservers()
    }

    private fun initObservers() = with(binding) {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is FavoritesState.Loading -> progressBar.visible()

                is FavoritesState.Success -> {
                    favoritesAdapter.submitList(state.favoriteProducts)
                    progressBar.gone()
                }

                is FavoritesState.Error -> {
                    showPopup(state.throwable.message)
                    progressBar.gone()
                }

                is FavoritesState.EmptyData -> {
                    progressBar.gone()
                }
            }
        }
    }

    private fun onProductClick(id: Int) {
        val action = FavoritesFragmentDirections.favoritesToDetail(id)
        findNavController().navigate(action)
    }

    private fun onDeleteClick(product: ProductUI) {
        viewModel.deleteFromFavorites(product)
    }
}