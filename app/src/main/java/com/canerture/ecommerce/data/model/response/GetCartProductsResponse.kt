package com.canerture.ecommerce.data.model.response

data class GetCartProductsResponse(
    val products: List<Product>?
) : BaseResponse()