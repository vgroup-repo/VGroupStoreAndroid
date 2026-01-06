package com.vgroup.vgroupstore.presentation.cart


import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vgroup.vgroupstore.domain.model.CartItem
import com.vgroup.vgroupstore.domain.model.Product
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class CartManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val gson: Gson
) {

    private val prefs = context.getSharedPreferences("cart_prefs", Context.MODE_PRIVATE)
    private val editor get() = prefs.edit()

    private val cartKey = "cart_items"

    fun getCartItems(): List<CartItem> {
        val json = prefs.getString(cartKey, null) ?: return emptyList()
        val type = object : TypeToken<List<CartItem>>() {}.type
        return gson.fromJson(json, type)
    }

    fun saveCart(items: List<CartItem>) {
        val json = gson.toJson(items)
        editor.putString(cartKey, json).apply()
    }

    fun addToCart(product: Product) {
        val items = getCartItems().toMutableList()

        val existing = items.find { it.product.id == product.id }

        if (existing == null) {
            items.add(CartItem(product, 1))
        } else {
            existing.quantity++
        }

        saveCart(items)
    }

    fun removeFromCart(productId: Long) {
        val items = getCartItems().toMutableList()
        items.removeAll { it.product.id == productId }
        saveCart(items)
    }

    fun increaseQuantity(productId: Long) {
        val items = getCartItems().toMutableList()
        items.find { it.product.id == productId }?.quantity?.let {
            items.find { it.product.id == productId }!!.quantity = it + 1
        }
        saveCart(items)
    }

    fun decreaseQuantity(productId: Long) {
        val items = getCartItems().toMutableList()
        val item = items.find { it.product.id == productId }

        if (item != null) {
            if (item.quantity > 1) item.quantity--
            else items.remove(item)
        }

        saveCart(items)
    }

    fun getSubtotal(): Double {
        return getCartItems().sumOf { it.product.price * it.quantity }
    }
}
