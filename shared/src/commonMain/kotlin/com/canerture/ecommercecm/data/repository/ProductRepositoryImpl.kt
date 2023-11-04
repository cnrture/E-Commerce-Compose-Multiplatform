package com.canerture.ecommercecm.data.repository

import com.canerture.ecommercecm.common.Resource
import com.canerture.ecommercecm.data.model.response.BaseResponse
import com.canerture.ecommercecm.data.model.response.GetProductDetailResponse
import com.canerture.ecommercecm.data.model.response.GetProductsResponse
import com.canerture.ecommercecm.data.source.remote.ProductService
import com.canerture.ecommercecm.domain.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent

class ProductRepositoryImpl(
    private val service: ProductService
) : KoinComponent, ProductRepository {

    override suspend fun getAllProducts(): Resource<GetProductsResponse> =
        safeApiCall { service.getProducts() }

    override suspend fun getProductDetail(id: Int): Resource<GetProductDetailResponse> =
        safeApiCall { service.getProductDetail(id) }

    private suspend inline fun <reified T : BaseResponse> safeApiCall(
        crossinline apiToBeCalled: suspend () -> T
    ): Resource<T> {

        return withContext(Dispatchers.IO) {
            try {
                val response: BaseResponse = apiToBeCalled()

                if (response.status in 200..300) {
                    Resource.Success(response as T)
                } else {
                    Resource.Fail(response.message.orEmpty())
                }
            } catch (e: Exception) {
                Resource.Error(Throwable("Something went wrong"))
            }
        }
    }
}