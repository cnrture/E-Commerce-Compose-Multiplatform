package com.canerture.ecommerce.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.canerture.ecommerce.common.Resource
import com.canerture.ecommerce.data.model.response.ProductUI
import com.canerture.ecommerce.data.repository.ProductRepository
import com.canerture.ecommerce.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private var _cartState = MutableLiveData<CartState>()
    val cartState: LiveData<CartState>
        get() = _cartState

    fun getCartProducts() {
        viewModelScope.launch {
            _cartState.value = CartState.Loading
            _cartState.value = when (val result = productRepository.getCartProducts(userRepository.getUserUid())) {
                is Resource.Success -> CartState.Success(result.data)
                is Resource.Error -> CartState.Error(result.throwable)
                is Resource.Fail -> CartState.EmptyScreen(result.message)
            }
        }
    }

    fun deleteProductFromCart(id: Int) {
        viewModelScope.launch {
            productRepository.deleteFromCart(id)
            getCartProducts()
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            productRepository.clearCart(userRepository.getUserUid())
            getCartProducts()
        }
    }
}

sealed interface CartState {
    object Loading : CartState
    data class EmptyScreen(val message: String) : CartState
    data class Success(val products: List<ProductUI>) : CartState
    data class Error(val throwable: Throwable) : CartState
}