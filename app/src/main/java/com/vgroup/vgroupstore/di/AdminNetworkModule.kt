package com.vgroup.vgroupstore.di

import com.vgroup.vgroupstore.core.Config
import com.vgroup.vgroupstore.data.api.ShopifyAdminApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AdminNetworkModule {

    @Provides
    @Singleton
    @AdminClient
    fun provideAdminOkHttp(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("X-Shopify-Access-Token", Config.ADMIN_API_TOKEN)
                    .build()
                chain.proceed(request)
            }
            .build()

    @Provides
    @Singleton
    @AdminRetrofit
    fun provideAdminRetrofit(
        @AdminClient client: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(Config.ADMIN_PRODUCTS_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideAdminApi(
        @AdminRetrofit retrofit: Retrofit
    ): ShopifyAdminApi =
        retrofit.create(ShopifyAdminApi::class.java)
}
