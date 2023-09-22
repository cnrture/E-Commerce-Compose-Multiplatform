package com.canerture.ecommerce.di

import com.canerture.ecommerce.data.repository.ProductRepository
import com.canerture.ecommerce.data.repository.UserRepository
import com.canerture.ecommerce.data.source.local.ProductDao
import com.canerture.ecommerce.data.source.remote.ProductService
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideProductRepository(productService: ProductService, productDao: ProductDao): ProductRepository =
        ProductRepository(productService, productDao)

    @Provides
    @Singleton
    fun provideUserRepository(firebaseAuth: FirebaseAuth): UserRepository =
        UserRepository(firebaseAuth)
}