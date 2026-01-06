package com.vgroup.vgroupstore.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vgroup.vgroupstore.data.entities.CartEntity
import com.vgroup.vgroupstore.data.repository.CartRepository
import com.vgroup.vgroupstore.domain.model.Product
import com.vgroup.vgroupstore.domain.model.ProductList
import com.vgroup.vgroupstore.domain.usecase.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SharedProductsViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val cartRepo: CartRepository   // <-- Add this
) : ViewModel() {


    // PRODUCTS STATE
    private val _state = MutableStateFlow<DashboardUiState>(DashboardUiState.Loading)
    val state: StateFlow<DashboardUiState> = _state
    val searchResults = MutableStateFlow<List<ProductList>>(emptyList())
    private var isLoaded = false
    var allProducts: List<ProductList> = emptyList()



    // CART STATE
    private val _cart = MutableStateFlow<List<CartEntity>>(emptyList())


    val cart: StateFlow<List<CartEntity>> = _cart



    // LOAD PRODUCTS
    fun loadProducts() {
        if (isLoaded) {
            _state.value = DashboardUiState.Success(allProducts)
            return
        }
        isLoaded = true

        viewModelScope.launch {
            try {
                allProducts = getProductsUseCase()
                _state.value = DashboardUiState.Success(allProducts)
            } catch (e: Exception) {
                _state.value = DashboardUiState.Error(e.message ?: "Something went wrong")
            }
        }
    }



    // SEARCH
    fun performSearch(query: String) {
        val filtered = if (query.isBlank()) allProducts
        else allProducts.filter { it.title.contains(query, ignoreCase = true) }

        _state.value = DashboardUiState.Success(filtered)
    }


    // CART OPERATIONS

    fun addToCart(product: Product) {
        viewModelScope.launch {
            cartRepo.addToCart(product)
            loadCart()
        }
    }

    fun removeFromCart(productId: Long) {
        viewModelScope.launch {
            cartRepo.remove(productId)
            loadCart()
        }
    }

    fun increaseQty(productId: Long) {
        viewModelScope.launch {
            cartRepo.increase(productId)
            loadCart()
        }
    }

    fun decreaseQty(productId: Long) {
        viewModelScope.launch {
            cartRepo.decrease(productId)
            loadCart()
        }
    }

    fun loadCart() {
        viewModelScope.launch {
            _cart.value = cartRepo.getCartItems()
        }
    }

    fun performItemSearch(query: String) {
        val filtered = allProducts.filter { it.title.contains(query, true) }
        searchResults.value = filtered
    }


    // --------------------------
    // ProductList → Product converter
    // --------------------------
    fun ProductList.toProduct(): Product {
        return Product(
            id = this.id ?: 0L,
            title = this.title ?: "",
            price = this.variants?.firstOrNull()?.price?.toDoubleOrNull() ?: 0.0,
            image = this.images?.firstOrNull()?.src ?: ""
        )
    }

}

