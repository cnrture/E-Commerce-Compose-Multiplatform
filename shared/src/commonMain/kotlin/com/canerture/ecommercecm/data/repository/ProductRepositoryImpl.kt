package com.canerture.ecommercecm.data.repository

import com.canerture.ecommercecm.common.Resource
import com.canerture.ecommercecm.data.model.response.GetProductsResponse
import com.canerture.ecommercecm.data.model.response.GetSaleProductsResponse
import com.canerture.ecommercecm.data.model.response.SearchProductResponse
import com.canerture.ecommercecm.data.source.remote.ProductService
import com.canerture.ecommercecm.domain.mapper.mapToProductUI
import com.canerture.ecommercecm.domain.repository.ProductRepository
import io.ktor.client.call.body
import org.koin.core.component.KoinComponent

class ProductRepositoryImpl(
    private val service: ProductService
) : KoinComponent, ProductRepository, BaseRepository() {

    override suspend fun getAllProducts(): Resource<GetProductsResponse> {
        return try {
            val response = service.getProducts()
            Resource.Success(GetProductsResponse(response))
        } catch (e: Exception) {
            Resource.Error(Throwable("Something went wrong"))
        }
    }

    override suspend fun getSaleProducts(): Resource<GetSaleProductsResponse> =
        safeApiCall { service.getSaleProducts() }

    override suspend fun searchProduct(query: String): Resource<SearchProductResponse> {
        TODO("Not yet implemented")
    }
}