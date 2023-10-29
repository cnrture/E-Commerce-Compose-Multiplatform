package com.canerture.ecommercecm.data.source.remote

import com.canerture.ecommercecm.common.Constants
import com.canerture.ecommercecm.data.model.request.AddToCartRequest
import com.canerture.ecommercecm.data.model.request.ClearCartRequest
import com.canerture.ecommercecm.data.model.request.DeleteFromCartRequest
import com.canerture.ecommercecm.data.model.response.GetProductsResponse
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType

class ProductService : KtorApi() {

    suspend fun addToCart(addToCartRequest: AddToCartRequest) =
        client.post {
            url(Constants.EndPoints.ADD_TO_CART)
            setBody(addToCartRequest)
        }

    suspend fun deleteFromCart(deleteFromCartRequest: DeleteFromCartRequest) =
        client.post {
            url(Constants.EndPoints.DELETE_FROM_CART)
            setBody(deleteFromCartRequest)
        }

    suspend fun getCartProducts(userId: String) =
        client.get {
            url(Constants.EndPoints.GET_CART_PRODUCTS)
            parameter("userId", userId)
        }

    suspend fun clearCart(clearCartRequest: ClearCartRequest) =
        client.post {
            url(Constants.EndPoints.CLEAR_CART)
            setBody(clearCartRequest)
        }

    suspend fun getProducts() =
        client.get {
            url(Constants.EndPoints.GET_PRODUCTS)
        }.body<GetProductsResponse>().products

    suspend fun getProductsByCategory(category: String) =
        client.get {
            url(Constants.EndPoints.GET_PRODUCTS_BY_CATEGORY)
            parameter("category", category)
        }

    suspend fun getSaleProducts() =
        client.get {
            url(Constants.EndPoints.GET_SALE_PRODUCTS)
        }

    suspend fun searchProduct(query: String) =
        client.get {
            url(Constants.EndPoints.SEARCH_PRODUCT)
            parameter("query", query)
        }

    suspend fun getCategories() =
        client.get {
            url(Constants.EndPoints.GET_CATEGORIES)
        }

    suspend fun getProductDetail(id: Int) =
        client.get {
            url(Constants.EndPoints.GET_PRODUCT_DETAIL)
            parameter("id", id)
        }
}