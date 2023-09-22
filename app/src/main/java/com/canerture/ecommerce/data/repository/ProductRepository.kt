package com.canerture.ecommerce.data.repository

import com.canerture.ecommerce.common.Resource
import com.canerture.ecommerce.data.mapper.mapToProductEntity
import com.canerture.ecommerce.data.mapper.mapToProductUI
import com.canerture.ecommerce.data.model.request.AddToCartRequest
import com.canerture.ecommerce.data.model.request.ClearCartRequest
import com.canerture.ecommerce.data.model.request.DeleteFromCartRequest
import com.canerture.ecommerce.data.model.response.BaseResponse
import com.canerture.ecommerce.data.model.response.ProductUI
import com.canerture.ecommerce.data.source.local.ProductDao
import com.canerture.ecommerce.data.source.remote.ProductService

class ProductRepository(
    private val productService: ProductService,
    private val productDao: ProductDao
) {

    suspend fun getAllProducts(): Resource<List<ProductUI>> =
        try {
            val result = productService.getProducts()
            val favoriteTitles = productDao.getFavoriteTitles()
            productService.getProducts().call(
                onSuccess = {
                    result.products.orEmpty().map {
                        it.mapToProductUI(favoriteTitles.contains(it.title))
                    }
                },
                onFail = { result.message }
            )
        } catch (e: Exception) {
            Resource.Error(throwable = e)
        }

    suspend fun getSaleProducts(): Resource<List<ProductUI>> =
        try {
            val result = productService.getSaleProducts()
            val favoriteTitles = productDao.getFavoriteTitles()
            result.call(
                onSuccess = {
                    result.products.orEmpty().map {
                        it.mapToProductUI(favoriteTitles.contains(it.title))
                    }
                },
                onFail = { result.message }
            )
        } catch (e: Exception) {
            Resource.Error(e)
        }

    suspend fun getProductDetail(id: Int): Resource<ProductUI> =
        try {
            val result = productService.getProductDetail(id)
            val favoriteTitles = productDao.getFavoriteTitles()
            result.call(
                onSuccess = {
                    result.product?.mapToProductUI(favoriteTitles.contains(result.product.title))!!
                },
                onFail = { result.message }
            )
        } catch (e: Exception) {
            Resource.Error(e)
        }

    suspend fun getCartProducts(userId: String): Resource<List<ProductUI>> =
        try {
            val result = productService.getCartProducts(userId)
            val favoriteTitles = productDao.getFavoriteTitles()
            result.call(
                onSuccess = {
                    result.products.orEmpty().map {
                        it.mapToProductUI(favoriteTitles.contains(it.title))
                    }
                },
                onFail = { result.message }
            )
        } catch (e: Exception) {
            Resource.Error(e)
        }

    suspend fun addToCart(userId: String, id: Int): Resource<BaseResponse> {
        return try {
            Resource.Success(productService.addToCart(AddToCartRequest(userId, id)))
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun deleteFromCart(id: Int): Resource<BaseResponse> =
        try {
            Resource.Success(productService.deleteFromCart(DeleteFromCartRequest(id)))
        } catch (e: Exception) {
            Resource.Error(e)
        }

    suspend fun clearCart(userId: String): Resource<BaseResponse> =
        try {
            Resource.Success(productService.clearCart(ClearCartRequest(userId)))
        } catch (e: Exception) {
            Resource.Error(e)
        }

    suspend fun getProductsByCategory(category: String): Resource<List<ProductUI>> =
        try {
            val result = productService.getProductsByCategory(category)
            val favoriteTitles = productDao.getFavoriteTitles()
            result.call(
                onSuccess = {
                    result.products.orEmpty().map {
                        it.mapToProductUI(favoriteTitles.contains(it.title))
                    }
                },
                onFail = { result.message }
            )
        } catch (e: Exception) {
            Resource.Error(e)
        }

    suspend fun searchProduct(query: String): Resource<List<ProductUI>> =
        try {
            val result = productService.searchProduct(query)
            val favoriteTitles = productDao.getFavoriteTitles()
            result.call(
                onSuccess = {
                    result.products.orEmpty().map {
                        it.mapToProductUI(favoriteTitles.contains(it.title))
                    }
                },
                onFail = { result.message }
            )
        } catch (e: Exception) {
            Resource.Error(e)
        }

    suspend fun getCategories(): Resource<List<String>> =
        try {
            Resource.Success(productService.getCategories().categories.orEmpty())
        } catch (e: Exception) {
            Resource.Error(e)
        }

    suspend fun addToFavorites(product: ProductUI) {
        productDao.addToFavorites(product.mapToProductEntity())
    }

    suspend fun getFavorites(): Resource<List<ProductUI>> =
        try {
            val products = productDao.getProducts().map {
                it.mapToProductUI()
            }
            Resource.Success(products)
        } catch (e: Exception) {
            Resource.Error(e)
        }

    suspend fun deleteFromFavorites(product: ProductUI) {
        productDao.deleteFromFavorites(product.mapToProductEntity())
    }

    suspend fun clearFavorites() {
        productDao.clearFavorites()
    }

    private fun <T : Any> BaseResponse.call(
        onSuccess: () -> T,
        onFail: () -> String?
    ): Resource<T> {
        return if (status == 200) {
            Resource.Success(onSuccess())
        } else {
            Resource.Fail(onFail().orEmpty())
        }
    }
}