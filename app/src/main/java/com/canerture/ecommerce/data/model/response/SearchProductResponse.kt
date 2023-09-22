package com.canerture.ecommerce.data.model.response

data class SearchProductResponse(
    val products: List<Product>?
) : BaseResponse()