package com.vgroup.vgroupstore.data.repository

import com.vgroup.vgroupstore.data.api.ShopifyAdminApi
import com.vgroup.vgroupstore.domain.model.ProductList
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val adminApi: ShopifyAdminApi
) {

    suspend fun getProducts(): List<ProductList> {
        val result = adminApi.getProducts()
        return result.products
    }
}
