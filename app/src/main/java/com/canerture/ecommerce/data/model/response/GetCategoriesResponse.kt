package com.canerture.ecommerce.data.model.response

data class GetCategoriesResponse(
    val categories: List<String>?
) : BaseResponse()