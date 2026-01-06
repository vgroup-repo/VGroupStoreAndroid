package com.vgroup.vgroupstore.presentation.profile

sealed class ProfileUiState {
    object Loading : ProfileUiState()
    data class Loaded(val name: String, val email: String) : ProfileUiState()
    data class Error(val msg: String) : ProfileUiState()
    object LoggedOut : ProfileUiState()
}
