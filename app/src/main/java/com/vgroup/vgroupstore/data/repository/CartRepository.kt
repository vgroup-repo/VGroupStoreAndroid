package com.vgroup.vgroupstore.data.repository

import com.vgroup.vgroupstore.data.dao.CartDao
import com.vgroup.vgroupstore.data.entities.CartEntity
import com.vgroup.vgroupstore.domain.model.Product
import javax.inject.Inject

class CartRepository @Inject constructor(
    private val dao: CartDao
) {

    val cartFlow = dao.getAllFlow()

    suspend fun insert(item: CartEntity) = dao.insert(item)

    suspend fun delete(productId: Long) = dao.delete(productId)

    suspend fun getAll(): List<CartEntity> = dao.getAll()

    suspend fun getById(productId: Long): CartEntity? = dao.getById(productId)

    suspend fun addToCart(product: Product) {
        val existing = dao.getById(product.id)
        if (existing == null) {
            dao.insert(
                CartEntity(
                    productId = product.id,
                    title = product.title,
                    price = product.price,
                    image = product.image,
                    quantity = 1
                )
            )
        } else {
            dao.insert(existing.copy(quantity = existing.quantity + 1))
        }
    }



    suspend fun getCartItems(): List<CartEntity> = dao.getAll()

    suspend fun remove(productId: Long) = dao.remove(productId)

    suspend fun increase(productId: Long) = dao.increaseQty(productId)

    suspend fun decrease(productId: Long) = dao.decreaseQty(productId)


}
