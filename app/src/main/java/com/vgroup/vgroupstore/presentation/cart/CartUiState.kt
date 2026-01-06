package com.vgroup.vgroupstore.presentation.cart

import com.vgroup.vgroupstore.domain.model.CartItem



sealed class CartUiState {
    object Loading : CartUiState()
    data class Success(
        val items: List<CartItem>,
        val subtotal: Double,
        val shipping: Double,
        val total: Double
    ) : CartUiState()

    data class Error(val message: String) : CartUiState()
}
