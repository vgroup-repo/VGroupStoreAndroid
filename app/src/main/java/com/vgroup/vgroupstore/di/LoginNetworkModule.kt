package com.vgroup.vgroupstore.di


import com.vgroup.vgroupstore.core.Config
import com.vgroup.vgroupstore.data.api.ShopifyStorefrontApi
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
object LoginNetworkModule {

    @Provides
    @Singleton
    @StorefrontClient
    fun provideStorefrontOkHttp(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("X-Shopify-Storefront-Access-Token", Config.STORE_FRONT_TOKEN)
                    .build()
                chain.proceed(request)
            }
            .build()

    @Provides
    @Singleton
    @StorefrontRetrofit
    fun provideStorefrontRetrofit(
        @StorefrontClient client: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(Config.STORE_FRONT_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideStorefrontApi(
        @StorefrontRetrofit retrofit: Retrofit
    ): ShopifyStorefrontApi =
        retrofit.create(ShopifyStorefrontApi::class.java)
}

