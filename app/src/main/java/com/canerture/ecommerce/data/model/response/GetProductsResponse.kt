package com.canerture.ecommerce.data.model.response

data class GetProductsResponse(
    val products: List<Product>?
) : BaseResponse()