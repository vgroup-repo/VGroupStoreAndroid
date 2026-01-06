package com.vgroup.vgroupstore.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vgroup.vgroupstore.core.AuthPrefs
import com.vgroup.vgroupstore.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val prefs: AuthPrefs

) : ViewModel() {

    private val _state = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val state: StateFlow<LoginUiState> = _state

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                _state.value = LoginUiState.Loading
                 val user = loginUseCase(email, password)

                _state.value = LoginUiState.LoginSuccess(user)

            } catch (e: Exception) {
                _state.value = LoginUiState.Error(e.message ?: "Login failed")
            }
        }
    }
}
