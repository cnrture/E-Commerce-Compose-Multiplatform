package com.canerture.ecommerce.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.canerture.ecommerce.R
import com.canerture.ecommerce.common.Resource
import com.canerture.ecommerce.data.model.response.ProductUI
import com.canerture.ecommerce.data.repository.ProductRepository
import com.canerture.ecommerce.infrastructure.StringResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val stringRes: StringResourceProvider
) : ViewModel() {

    private var _state = MutableLiveData<FavoritesState>(FavoritesState.Loading)
    val state: LiveData<FavoritesState>
        get() = _state

    fun getFavorites() = viewModelScope.launch {
        _state.value = when (val result = productRepository.getFavorites()) {
            is Resource.Success -> FavoritesState.Success(result.data)
            is Resource.Error -> FavoritesState.Error(result.throwable)
            is Resource.Fail -> FavoritesState.EmptyData(stringRes(R.string.something_went_wrong))
        }
    }

    fun deleteFromFavorites(product: ProductUI) = viewModelScope.launch {
        productRepository.deleteFromFavorites(product)
        getFavorites()
    }
}

sealed interface FavoritesState {
    object Loading : FavoritesState
    data class Success(val favoriteProducts: List<ProductUI>) : FavoritesState
    data class Error(val throwable: Throwable) : FavoritesState
    data class EmptyData(val message: String) : FavoritesState
}