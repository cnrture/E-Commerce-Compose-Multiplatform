package com.canerture.ecommerce.data.model.response

data class GetProductDetailResponse(
    val product: Product?
) : BaseResponse()