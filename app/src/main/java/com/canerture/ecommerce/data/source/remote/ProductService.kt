package com.canerture.ecommerce.data.source.remote

import com.canerture.ecommerce.common.Constants.EndPoints.ADD_TO_CART
import com.canerture.ecommerce.common.Constants.EndPoints.CLEAR_CAT
import com.canerture.ecommerce.common.Constants.EndPoints.DELETE_FROM_CART
import com.canerture.ecommerce.common.Constants.EndPoints.GET_CART_PRODUCTS
import com.canerture.ecommerce.common.Constants.EndPoints.GET_CATEGORIES
import com.canerture.ecommerce.common.Constants.EndPoints.GET_PRODUCTS
import com.canerture.ecommerce.common.Constants.EndPoints.GET_PRODUCTS_BY_CATEGORY
import com.canerture.ecommerce.common.Constants.EndPoints.GET_PRODUCT_DETAIL
import com.canerture.ecommerce.common.Constants.EndPoints.GET_SALE_PRODUCTS
import com.canerture.ecommerce.common.Constants.EndPoints.SEARCH_PRODUCT
import com.canerture.ecommerce.data.model.request.AddToCartRequest
import com.canerture.ecommerce.data.model.request.ClearCartRequest
import com.canerture.ecommerce.data.model.request.DeleteFromCartRequest
import com.canerture.ecommerce.data.model.response.BaseResponse
import com.canerture.ecommerce.data.model.response.GetCartProductsResponse
import com.canerture.ecommerce.data.model.response.GetCategoriesResponse
import com.canerture.ecommerce.data.model.response.GetProductDetailResponse
import com.canerture.ecommerce.data.model.response.GetProductsByCategoryResponse
import com.canerture.ecommerce.data.model.response.GetProductsResponse
import com.canerture.ecommerce.data.model.response.GetSaleProductsResponse
import com.canerture.ecommerce.data.model.response.SearchProductResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ProductService {

    @POST(ADD_TO_CART)
    suspend fun addToCart(
        @Body addToCartRequest: AddToCartRequest
    ): BaseResponse

    @POST(DELETE_FROM_CART)
    suspend fun deleteFromCart(
        @Body deleteFromCartRequest: DeleteFromCartRequest
    ): BaseResponse

    @GET(GET_CART_PRODUCTS)
    suspend fun getCartProducts(
        @Query("userId") userId: String
    ): GetCartProductsResponse

    @POST(CLEAR_CAT)
    suspend fun clearCart(
        @Body clearCartRequest: ClearCartRequest
    ): BaseResponse

    @GET(GET_PRODUCTS)
    suspend fun getProducts(): GetProductsResponse

    @GET(GET_PRODUCTS_BY_CATEGORY)
    suspend fun getProductsByCategory(
        @Query("category") category: String
    ): GetProductsByCategoryResponse

    @GET(GET_SALE_PRODUCTS)
    suspend fun getSaleProducts(): GetSaleProductsResponse

    @GET(SEARCH_PRODUCT)
    suspend fun searchProduct(
        @Query("query") query: String
    ): SearchProductResponse

    @GET(GET_CATEGORIES)
    suspend fun getCategories(): GetCategoriesResponse

    @POST(GET_PRODUCT_DETAIL)
    suspend fun getProductDetail(
        @Query("id") id: Int
    ): GetProductDetailResponse
}