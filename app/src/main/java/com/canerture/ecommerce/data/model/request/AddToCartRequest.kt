package com.canerture.ecommerce.data.model.request

data class AddToCartRequest(
    val userId: String,
    val productId: Int
)