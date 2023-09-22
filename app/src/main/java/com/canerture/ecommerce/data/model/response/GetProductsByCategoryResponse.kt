package com.canerture.ecommerce.data.model.response

data class GetProductsByCategoryResponse(
    val products: List<Product>?
) : BaseResponse()