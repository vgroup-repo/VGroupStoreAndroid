package com.vgroup.vgroupstore.presentation.search



import com.vgroup.vgroupstore.domain.model.Product

sealed class SearchUiState {
    object Idle : SearchUiState()
    object Loading : SearchUiState()
    data class Success(val results: List<Product>) : SearchUiState()
    data class Error(val message: String) : SearchUiState()
}
