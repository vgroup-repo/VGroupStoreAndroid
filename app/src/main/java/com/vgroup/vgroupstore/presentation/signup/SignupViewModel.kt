package com.vgroup.vgroupstore.presentation.signup


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vgroup.toystore.domain.usecase.SignupUseCase
import com.vgroup.vgroupstore.core.AuthPrefs
import com.vgroup.vgroupstore.data.repository.LoginRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class SignupViewModel @Inject constructor(
    private val signupUseCase: SignupUseCase,
    private val loginRepo: LoginRepositoryImpl,
    private val prefs: AuthPrefs
) : ViewModel() {

    private val _state = MutableStateFlow<SignupUiState>(SignupUiState.Idle)
    val state: StateFlow<SignupUiState> = _state

    fun signup(firstName: String,lastName: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                _state.value = SignupUiState.Loading
                val signupResponse = signupUseCase(firstName, lastName, email, password)

                val errors = signupResponse.data?.customerCreate?.customerUserErrors
                if (!errors.isNullOrEmpty()) {
                    _state.value = SignupUiState.Error(errors.first().message ?: "Signup failed")
                    return@launch
                }

                val userProfile = loginRepo.login(email, password)

                prefs.saveUserObject(userProfile)

                _state.value = SignupUiState.LoginSuccess(userProfile)
            } catch (e: Exception) {
                _state.value = SignupUiState.Error(e.message ?: "Signup failed")
            }
        }
    }
}
