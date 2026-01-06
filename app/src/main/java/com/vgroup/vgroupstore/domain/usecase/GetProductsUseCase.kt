package com.vgroup.vgroupstore.domain.usecase

import com.vgroup.vgroupstore.data.repository.ProductRepositoryImpl
import com.vgroup.vgroupstore.domain.model.Product
import com.vgroup.vgroupstore.domain.model.ProductList
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repo: ProductRepositoryImpl
) {
    suspend operator fun invoke(): List<ProductList> = repo.getProducts()
}
