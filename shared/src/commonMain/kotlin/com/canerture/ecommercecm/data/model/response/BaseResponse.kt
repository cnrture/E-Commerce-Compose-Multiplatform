package com.canerture.ecommercecm.data.model.response

import kotlinx.serialization.Serializable

@Serializable
open class BaseResponse {
    val status: Int? = null
    val message: String? = null
}