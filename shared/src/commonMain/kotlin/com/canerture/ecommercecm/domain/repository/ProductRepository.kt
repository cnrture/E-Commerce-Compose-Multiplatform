package com.canerture.ecommercecm.domain.repository

import com.canerture.ecommercecm.common.Resource
import com.canerture.ecommercecm.data.model.response.BaseResponse
import com.canerture.ecommercecm.data.model.response.GetCartProductsResponse
import com.canerture.ecommercecm.data.model.response.GetCategoriesResponse
import com.canerture.ecommercecm.data.model.response.GetProductDetailResponse
import com.canerture.ecommercecm.data.model.response.GetProductsByCategoryResponse
import com.canerture.ecommercecm.data.model.response.GetProductsResponse
import com.canerture.ecommercecm.data.model.response.GetSaleProductsResponse
import com.canerture.ecommercecm.data.model.response.ProductUI
import com.canerture.ecommercecm.data.model.response.SearchProductResponse

interface ProductRepository {

    suspend fun getAllProducts(): Resource<GetProductsResponse>

    suspend fun getSaleProducts(): Resource<GetSaleProductsResponse>

    suspend fun searchProduct(query: String): Resource<SearchProductResponse>
}