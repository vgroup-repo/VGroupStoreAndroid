package com.vgroup.vgroupstore.presentation.profile



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vgroup.vgroupstore.core.AuthPrefs
import com.vgroup.vgroupstore.data.dao.CartDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val prefs: AuthPrefs,
    private val cartDao: CartDao
) : ViewModel() {

    private val _state = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val state = _state

    init {
        loadUser()
    }

    private fun loadUser() {
        val user = prefs.getUserObject()

        if (user == null) {
            _state.value = ProfileUiState.Error("User not logged in")
        } else {
            _state.value = ProfileUiState.Loaded(
                name = "${user.firstName} ${user.lastName}",
                email = user.email
            )
        }
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            prefs.logout()
            cartDao.logout()        // Must run on IO thread
            _state.value = ProfileUiState.LoggedOut
        }

    }
}
