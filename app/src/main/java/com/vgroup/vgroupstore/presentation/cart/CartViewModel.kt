package com.vgroup.vgroupstore.presentation.cart



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vgroup.vgroupstore.data.entities.CartEntity
import com.vgroup.vgroupstore.data.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repo: CartRepository
) : ViewModel() {

    private val _cart = MutableStateFlow<List<CartEntity>>(emptyList())
    val cart: StateFlow<List<CartEntity>> = _cart
    val cartUpdate = repo.cartFlow
    // Load all cart items
    fun loadCart() {
        viewModelScope.launch {
            _cart.value = repo.getAll()
        }
    }

    // Add 1 quantity
    fun increase(item: CartEntity) {
        viewModelScope.launch {
            val updated = item.copy(quantity = item.quantity + 1)
            repo.insert(updated)
            loadCart()
        }
    }

    // Subtract quantity
    fun decrease(item: CartEntity) {
        viewModelScope.launch {
            if (item.quantity <= 1) {
                repo.delete(item.productId)
            } else {
                val updated = item.copy(quantity = item.quantity - 1)
                repo.insert(updated)
            }
            loadCart()
        }
    }

    // Remove item completely
    fun remove(item: CartEntity) {
        viewModelScope.launch {
            repo.delete(item.productId)
            loadCart()
        }
    }

    // Add product from Dashboard
    fun addFromDashboard(productId: Long, title: String, price: Double, image: String) {
        viewModelScope.launch {
            val existing = repo.getById(productId)
            if (existing == null) {
                repo.insert(CartEntity(productId, title, price, image, 1))
            } else {
                repo.insert(existing.copy(quantity = existing.quantity + 1))
            }
            loadCart()
        }
    }
}

