package com.vgroup.vgroupstore.presentation.login

import com.vgroup.vgroupstore.domain.model.UserProfile

sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    object Success : LoginUiState()
    data class Error(val message: String) : LoginUiState()
    data class LoginSuccess(val user: UserProfile) : LoginUiState()

}
