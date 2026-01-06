package com.vgroup.vgroupstore.di

import android.content.SharedPreferences
import com.vgroup.vgroupstore.core.AuthPrefs
import com.vgroup.vgroupstore.data.api.ShopifyAdminApi
import com.vgroup.vgroupstore.data.api.ShopifyStorefrontApi
import com.vgroup.vgroupstore.data.repository.LoginRepositoryImpl
import com.vgroup.vgroupstore.data.repository.ProductRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    // LOGIN -------------------------
    @Provides
    @Singleton
    fun provideAuthRepository(
        api: ShopifyStorefrontApi,
        prefs: AuthPrefs
    ): LoginRepositoryImpl =
        LoginRepositoryImpl(api, prefs)


    // PRODUCTS ----------------------
    @Provides
    @Singleton
    fun provideProductRepository(
        api: ShopifyAdminApi
    ): ProductRepositoryImpl =
        ProductRepositoryImpl(api)

}
