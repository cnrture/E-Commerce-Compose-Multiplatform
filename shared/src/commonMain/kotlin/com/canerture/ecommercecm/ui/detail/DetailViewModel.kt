package com.canerture.ecommercecm.ui.detail

import com.canerture.ecommercecm.common.Resource
import com.canerture.ecommercecm.data.model.response.ProductUI
import com.canerture.ecommercecm.domain.usecase.GetProductDetailUseCase
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class DetailViewModel(
    val id: Int
) : ViewModel(), KoinComponent {

    private val getProductDetailUseCase: GetProductDetailUseCase = get()

    private val _state = MutableStateFlow<DetailState>(DetailState.Loading)
    val state: StateFlow<DetailState> = _state.asStateFlow()

    init {
        getProductDetail(id)
    }

    fun getProductDetail(id: Int) =
        getProductDetailUseCase
            .invoke(id)
            .onStart { _state.value = DetailState.Loading }
            .onEach {
                _state.value = when (it) {
                    is Resource.Success -> DetailState.Product(it.data)
                    is Resource.Error -> DetailState.Error(it.throwable)
                    is Resource.Fail -> DetailState.EmptyScreen(it.message)
                }
            }
            .launchIn(viewModelScope)
}

sealed interface DetailState {
    data object Loading : DetailState
    data class EmptyScreen(val message: String) : DetailState
    data class Product(val product: ProductUI) : DetailState
    data class Error(val throwable: Throwable) : DetailState
}