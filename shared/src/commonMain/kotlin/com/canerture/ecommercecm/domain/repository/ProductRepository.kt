package com.canerture.ecommercecm.domain.repository

import com.canerture.ecommercecm.common.Resource
import com.canerture.ecommercecm.data.model.response.GetProductDetailResponse
import com.canerture.ecommercecm.data.model.response.GetProductsResponse

interface ProductRepository {

    suspend fun getAllProducts(): Resource<GetProductsResponse>

    suspend fun getProductDetail(id: Int): Resource<GetProductDetailResponse>
}