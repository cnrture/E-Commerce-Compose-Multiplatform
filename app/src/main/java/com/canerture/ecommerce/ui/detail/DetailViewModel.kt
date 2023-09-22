package com.canerture.ecommerce.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.canerture.ecommerce.common.Resource
import com.canerture.ecommerce.data.model.response.ProductUI
import com.canerture.ecommerce.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private var _detailState = MutableLiveData<DetailState>(DetailState.Loading)
    val detailState: LiveData<DetailState>
        get() = _detailState

    fun getProductDetail(id: Int) {
        viewModelScope.launch {
            _detailState.value = when (val result = productRepository.getProductDetail(id)) {
                is Resource.Success -> DetailState.Success(result.data)
                is Resource.Error -> DetailState.Error(result.throwable)
                is Resource.Fail -> DetailState.EmptyScreen(result.message)
            }
        }
    }
}

sealed interface DetailState {
    object Loading : DetailState
    data class EmptyScreen(val message: String) : DetailState
    data class Success(val product: ProductUI) : DetailState
    data class Error(val throwable: Throwable) : DetailState
}