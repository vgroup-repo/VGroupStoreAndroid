package com.vgroup.vgroupstore.presentation.signup

import com.vgroup.vgroupstore.domain.model.SignupResponse
import com.vgroup.vgroupstore.domain.model.UserProfile
import com.vgroup.vgroupstore.presentation.login.LoginUiState


sealed class SignupUiState {
    object Idle : SignupUiState()
    object Loading : SignupUiState()
    object Success : SignupUiState()
    data class Error(val message: String) : SignupUiState()
    data class CustomerCreateSuccess(val user: SignupResponse) : SignupUiState()
    data class LoginSuccess(val user: UserProfile) : SignupUiState()
}
