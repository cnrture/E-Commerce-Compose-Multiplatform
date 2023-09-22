package com.canerture.ecommerce.ui.cart

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
import com.canerture.ecommerce.databinding.FragmentCartBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created on 10.08.2023
 * @author Caner Türe
 */

@AndroidEntryPoint
class CartFragment : Fragment(R.layout.fragment_cart) {

    private val binding by viewBinding(FragmentCartBinding::bind)

    private val viewModel by viewModels<CartViewModel>()

    private val cartProductsAdapter by lazy { CartProductsAdapter(::onProductClick, ::onDeleteClick) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCartProducts()

        with(binding) {
            rvCartProducts.adapter = cartProductsAdapter

            tvClear.setOnClickListener {
                viewModel.clearCart()
            }
        }

        observeData()
    }

    private fun observeData() = with(binding) {
        viewModel.cartState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is CartState.Loading -> {
                    progressBar.visible()
                }

                is CartState.Success -> {
                    cartProductsAdapter.submitList(state.products)
                    progressBar.gone()
                }

                is CartState.Error -> {
                    showPopup(state.throwable.message)
                    progressBar.gone()
                }

                is CartState.EmptyScreen -> {
                    progressBar.gone()
                }
            }
        }
    }

    private fun onProductClick(id: Int) {
        val action = CartFragmentDirections.cartToDetail(id)
        findNavController().navigate(action)
    }

    private fun onDeleteClick(id: Int) {
        viewModel.deleteProductFromCart(id)
    }
}