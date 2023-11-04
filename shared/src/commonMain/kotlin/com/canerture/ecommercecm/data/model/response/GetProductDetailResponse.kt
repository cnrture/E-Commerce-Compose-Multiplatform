package com.canerture.ecommercecm.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class GetProductDetailResponse(
    val product: Product?
) : BaseResponse()