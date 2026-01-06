package com.vgroup.vgroupstore.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vgroup.vgroupstore.domain.usecase.SearchProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchProductsUseCase: SearchProductsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    val state: StateFlow<SearchUiState> = _state

    fun search(query: String) {
        viewModelScope.launch {
            try {
                _state.value = SearchUiState.Loading
            } catch (e: Exception) {
                _state.value = SearchUiState.Error(e.message ?: "Search failed")
            }
        }
    }
}



