package com.canerture.ecommerce.data.model.response

data class GetSaleProductsResponse(
    val products: List<Product>?
) : BaseResponse()