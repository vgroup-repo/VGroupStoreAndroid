package com.vgroup.vgroupstore.data.api

import com.vgroup.vgroupstore.domain.model.ProductsResponse
import retrofit2.http.GET
import retrofit2.http.Url

interface ShopifyAdminApi {

    @GET("products.json")
    suspend fun getProducts(): ProductsResponse
}
