package com.canerture.ecommercecm.domain.usecase

import com.canerture.ecommercecm.common.Resource
import com.canerture.ecommercecm.domain.mapper.mapToProductUI
import com.canerture.ecommercecm.domain.repository.ProductRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class GetProductDetailUseCase(private val productRepository: ProductRepository) {

    operator fun invoke(id: Int) = callbackFlow {

        when (val result = productRepository.getProductDetail(id)) {
            is Resource.Success -> {
                val product = result.data.product.mapToProductUI()
                trySend(Resource.Success(product))
            }

            is Resource.Error -> trySend(result)
            is Resource.Fail -> trySend(result)
        }

        awaitClose { close() }
    }
}