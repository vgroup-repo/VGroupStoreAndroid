package com.vgroup.vgroupstore.presentation.dashboard


import com.vgroup.vgroupstore.domain.model.ProductList

sealed class DashboardUiState {
    object Loading : DashboardUiState()
    data class Success(val products: List<ProductList>) : DashboardUiState()
    data class Error(val message: String) : DashboardUiState()
}
