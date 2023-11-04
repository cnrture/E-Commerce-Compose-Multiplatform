package com.canerture.ecommercecm.data.source.remote

import com.canerture.ecommercecm.common.Constants
import com.canerture.ecommercecm.data.model.response.GetProductDetailResponse
import com.canerture.ecommercecm.data.model.response.GetProductsResponse
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url

class ProductService : KtorApi() {

    suspend fun getProducts() =
        client.get {
            url(Constants.EndPoints.GET_PRODUCTS)
        }.body<GetProductsResponse>()

    suspend fun getProductDetail(id: Int) =
        client.get {
            url(Constants.EndPoints.GET_PRODUCT_DETAIL)
            parameter("id", id)
        }.body<GetProductDetailResponse>()
}