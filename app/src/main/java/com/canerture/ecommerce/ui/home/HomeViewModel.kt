package com.canerture.ecommerce.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.canerture.ecommerce.common.Resource
import com.canerture.ecommerce.data.model.response.ProductUI
import com.canerture.ecommerce.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private var _mainState = MutableLiveData<HomeState>()
    val mainState: LiveData<HomeState>
        get() = _mainState

    init {
        getProducts()
    }

    private fun getProducts() = viewModelScope.launch {
        _mainState.value = HomeState.Loading
        val allProducts = async { productRepository.getAllProducts() }.await()
        val saleProducts = async { productRepository.getSaleProducts() }.await()

        _mainState.value = when {
            allProducts is Resource.Error -> HomeState.Error(allProducts.throwable)
            saleProducts is Resource.Error -> HomeState.Error(saleProducts.throwable)
            allProducts is Resource.Fail -> HomeState.EmptyScreen(allProducts.message)
            saleProducts is Resource.Fail -> HomeState.EmptyScreen(saleProducts.message)

            else -> {
                val all = (allProducts as Resource.Success)
                val sale = (saleProducts as Resource.Success)
                HomeState.Success(all.data, sale.data)
            }
        }
    }

    fun setFavoriteState(product: ProductUI) = viewModelScope.launch {
        if (product.isFavorite) productRepository.deleteFromFavorites(product)
        else productRepository.addToFavorites(product)
        getProducts()
    }
}

sealed interface HomeState {
    object Loading : HomeState
    data class EmptyScreen(val message: String) : HomeState
    data class Success(val saleProducts: List<ProductUI>, val allProducts: List<ProductUI>) : HomeState
    data class Error(val throwable: Throwable) : HomeState
}